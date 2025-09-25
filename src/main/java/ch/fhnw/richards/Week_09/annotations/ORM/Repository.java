package ch.fhnw.richards.Week_09.annotations.ORM;

import ch.fhnw.richards.Week_09.annotations.ORMexample.model.Database;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class contains the methods that implement ORM functionality
 */
public class Repository<ObjType, IdType> {
    private static Database db = null; // Create and initialize database when class is first loaded

    public Repository() {
        if (db == null) {
            db = new Database();
            db.createDatabase();
        }
    }

    /**
     * Call when program is ending, to release the database connection
     */
    public static void close() {
        db.closeConnection();
    }

    /**
     * Delete the object from the database that has the same ID as the given object
     */
    public void delete(ObjType obj) {
        try {
            Class<?> c = obj.getClass();
            // Verify the object is an entity
            Entity entity = c.getAnnotation(Entity.class);
            // Get the table name
            Table table = c.getAnnotation(Table.class);
            String tableName = table.name();

            // Find the ID field, so we know which object to delete
            String IdFieldName = null;
            IdType IdValue = null;
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getAnnotation(ID.class) != null) { // found the ID field
                    IdFieldName = f.getName();
                    IdValue = (IdType) f.get(obj);
                    break;
                }
            }

            String sql = "DELETE FROM " + tableName + " WHERE " + IdFieldName + " = ?";
            try (
                PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
                setParameter(stmt, IdValue, 1);
                stmt.execute();
            }
        } catch (Exception e) {
            throw new OrmException(e.getMessage());
        }
    }

    /**
     * Return a list containing all objects from the database table. We are only using runtime annotations,
     * not compile-time. Since generic types are not available at runtime, we require an object as a
     * parameter, so that we can determine the data-type, and hence the database table to access.
     */
    public List<ObjType> findAll(ObjType obj) {
        try {
            Class<?> c = obj.getClass();
            String tableName = getTableName(c);

            // Find the ID field, which is needed for the constructor
            String IdFieldName = null;
            Class<?> IdFieldType = null;
            IdType IdValue = null;
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getAnnotation(ID.class) != null) { // found the ID field
                    IdFieldName = f.getName();
                    IdFieldType = f.getType();
                    break;
                }
            }

            List<ObjType> items = new ArrayList<>();
            String sql = "SELECT * FROM " + tableName;
            try (
                    PreparedStatement stmt = db.getConnection().prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
            ) {
                while (rs.next()) { // Move to next record
                    // Construct new object
                    Constructor<ObjType> constructor = (Constructor<ObjType>) c.getConstructor(IdFieldType);
                    IdValue = (IdType) getFieldValue(rs, IdFieldType, IdFieldName);
                    ObjType newObj = (ObjType) constructor.newInstance(IdValue);

                    for (Field f : c.getDeclaredFields()) {
                        f.setAccessible(true);
                        if (f.getAnnotation(ID.class) == null && f.getAnnotation(Column.class) != null) { // skip the ID field
                            String fieldName = f.getName();
                            Class<?> fieldType = f.getType();
                            Object fieldValue = getFieldValue(rs, fieldType, fieldName);
                            f.set(newObj, fieldValue);
                        } else if (f.getAnnotation(ElementCollection.class) != null) {
                            // Embedded collection, need to fill with objects
                            CollectionTable collectionInfo = f.getAnnotation(CollectionTable.class);
                            String subTableName = collectionInfo.name();
                            String joinColumn = collectionInfo.joinColumn();
                            Collection collection = (Collection) f.get(newObj); // Must be a collection!
                            String packageName = obj.getClass().getPackageName();
                            loadElementCollection(collection, packageName, subTableName, joinColumn, IdValue);
                            System.out.println("here");
                        }
                    }
                    items.add(newObj);
                }
            }
            return items;
        } catch (Exception e) {
            throw new OrmException(e.getMessage());
        }
    }

    /**
     * Load elements in an embedded collection. For example, "positions" in an "invoice",
     * or "items" in a "shoppingCart". We have no easy way to identify the class of the
     * objects we want to load, so we _require_ that the class-name be identical to tableName.
     * The class of the elements we load must provide a constructor without parameters.
     * We do not allow recursion, i.e., embedded-collections within embedded-collections
     * @param collection The collection where we will store the objects loaded
     * @param packageName The name of the package where the class will be found
     * @param tableName The name of the database table _and_ the name of the class
     * @param joinColumn The column used to select the objects from the database table
     * @param IdValue The id value that the objects must have on the joinColumn
     */
    private void loadElementCollection(Collection collection, String packageName, String tableName, String joinColumn, IdType IdValue) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + joinColumn + " = " + IdValue;
        try (
                PreparedStatement stmt = db.getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) { // Move to next record
                // Construct new object
                String className = packageName + "." + tableName;
                Class<?> c = Class.forName(className);
                Constructor constructor = c.getConstructor();
                ObjType newObj = (ObjType) constructor.newInstance();

                for (Field f : c.getDeclaredFields()) {
                    f.setAccessible(true);
                    if (f.getAnnotation(Column.class) != null) { // for all annotated fields
                        String fieldName = f.getName();
                        Class<?> fieldType = f.getType();
                        Object fieldValue = getFieldValue(rs, fieldType, fieldName);
                        f.set(newObj, fieldValue);
                    }
                }
                collection.add(newObj);
            }
        } catch (Exception e) {
            throw new OrmException(e.getMessage());
        }
    }

    // Find an object in the database with the same ID as this object
    public ObjType findOne(ObjType obj) {
        try {
            Class<?> c = obj.getClass();
            String tableName = getTableName(c);

            // Find the ID field, so we know which object to fetch
            String IdFieldName = null;
            IdType IdValue = null;
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getAnnotation(ID.class) != null) { // found the ID field
                    IdFieldName = f.getName();
                    IdValue = (IdType) f.get(obj);
                    break;
                }
            }

            String sql = "SELECT * FROM " + tableName + " WHERE " + IdFieldName + " = ?";
            try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
                setParameter(stmt, IdValue, 1);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) { // Move to first record
                        // Construct new object
                        Constructor<ObjType> constructor = (Constructor<ObjType>) obj.getClass().getConstructor(IdValue.getClass());
                        ObjType newObj = (ObjType) constructor.newInstance(IdValue);

                        for (Field f : c.getDeclaredFields()) {
                            f.setAccessible(true);
                            if (f.getAnnotation(ID.class) == null && f.getAnnotation(Column.class) != null) { // skip the ID field
                                String fieldName = f.getName();
                                Class<?> fieldType = f.getType();
                                Object fieldValue = getFieldValue(rs, fieldType, fieldName);
                                f.set(newObj, fieldValue);
                            }
                        }
                        return newObj;
                    } else {
                        return null; // No such object
                    }
                }
            }
        } catch (Exception e) {
            throw new OrmException(e.getMessage());
        }
    }

    /**
     * This method saves new objects and updates existing ones. This means we have two possible SQL statements.
     * The reason for combining these two operations is to mirror JPA functionality.
     */
    public void save(ObjType obj) {
        try {
            Class<?> c = obj.getClass();
            String tableName = getTableName(c);

            // Find the ID field, so we know which object to create or update
            // Simultaneously create lists of all field names and values to use in our queries
            String IdFieldName = null;
            IdType IdValue = null;
            List<String> columns = new ArrayList<>();
            List<Object> values = new ArrayList<>();
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getAnnotation(ID.class) != null) { // found the ID field
                    IdFieldName = f.getName();
                    IdValue = (IdType) f.get(obj);
                }
                if (f.getAnnotation(Column.class) != null) {
                    columns.add(f.getName());
                    values.add(f.get(obj));
                }
            }

            String sql;
            boolean creatingNew = findOne(obj) == null;
            if (creatingNew) { // Creating new object
                boolean first = true;
                String columnNames = "";
                String columnValues = "";
                for (String column : columns) {
                    if (first) {
                        first = false;
                    } else {
                        columnNames += ", ";
                        columnValues += ", ";
                    }
                    columnNames += column;
                    columnValues += "?";
                }
                sql = "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + columnValues + ")";
            } else { // updating existing object
                boolean first = true;
                String fieldUpdates = "";
                for (String column : columns) {
                    if (first) {
                        first = false;
                    } else {
                        fieldUpdates += ", ";
                    }
                    fieldUpdates += column + " = ?";
                }
                sql = "UPDATE " + tableName + " SET " + fieldUpdates + " WHERE " + IdFieldName + " = ?";
            }

            try(PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
                for (int i = 1; i <= values.size(); i++) { // 1-based, because SQL
                    setParameter(stmt, values.get(i - 1), i);
                }
                if (!creatingNew) {
                    // Need to set ID for SQL update statement
                    setParameter(stmt, IdValue, values.size() + 1);
                }
                stmt.execute();
            }
        } catch (Exception e) {
            throw new OrmException(e.getMessage());
        }
    }

    /**
     * Utility method to get the database table name. By using the annotation-classes,
     * we also verify that the object belongs to an annotated class. If it's not, we
     * throw an exception.
     */
    private String getTableName(Class<?> c) throws OrmException {
        try {
            // Verify the object is an entity
            c.getAnnotation(Entity.class);
            // Get the table name
            Table table = c.getAnnotation(Table.class);
            return table.name();
        } catch (Exception e) {
            throw new OrmException("Object does not belong to an annotated class");
        }
    }

    /**
     * Utility method to get the value of a field from the given ResultSet.
     */
    private Object getFieldValue(ResultSet rs, Class<?> fieldType, String fieldName) throws SQLException {
        if (fieldType == Integer.class) {
            return rs.getInt(fieldName);
        } else if (fieldType == String.class) {
            return rs.getString(fieldName);
        } else if (fieldType == LocalDate.class) {
            return rs.getDate(fieldName).toLocalDate();
        } else {
            throw new SQLException("Invalid type for Repository.getFieldValue");
        }
    }

    /**
     * Utility method to call the correct set-method of prepared statement, based on IdType.
     * This is difficult in Java, because generic types only exist at the source-code level.
     * (See "type erasure"). This means that we have to check the type of the value itself.
     */
    private void setParameter(PreparedStatement stmt, Object value, int position) throws SQLException {
        if (value.getClass() == Integer.class) {
            stmt.setInt(position, (Integer) value);
        } else if (value.getClass() == String.class) {
            stmt.setString(position, (String) value);
        } else {
            throw new SQLException("Invalid type for Repository.setParameter");
        }
    }
}
