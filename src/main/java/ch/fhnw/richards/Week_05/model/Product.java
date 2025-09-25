package ch.fhnw.richards.Week_05.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private final Integer ID;
    private String name;
    private Integer price;

    public Product(Integer productID) {
        this.ID = productID;
    }

    public Product(Integer productID, String name, Integer price) {
        this(productID);
        this.name = name;
        this.price = price;
    }

    /**
     * Create a product from a row in a ResultSet
     */
    public Product(ResultSet rs) throws SQLException {
        this.ID = rs.getInt("ID");
        this.name = rs.getString("name");
        this.price = rs.getInt("price");
    }

    public void save(Database db) throws SQLException {
        String sql = "UPDATE Product SET name = ?, price = ? WHERE ID = ?";

        // Create a PreparedStatement
        PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);

        // Set the parameters for the PreparedStatement
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, price);
        preparedStatement.setInt(3, ID);

        // Execute the update
        preparedStatement.executeUpdate();
    }

    /**
     * Method to get all products from the database
     */
    public static List<Product> getProducts(Database db) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product ORDER BY ID";
        try (
                PreparedStatement stmt = db.getConnection().prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                products.add(new Product(rs));
            }

        } catch (SQLException e) {
            System.out.println("Exception " + e);
        }
        return products;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}