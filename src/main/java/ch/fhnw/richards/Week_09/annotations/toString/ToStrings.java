package ch.fhnw.richards.Week_09.annotations.toString;

import java.lang.reflect.Field;

public class ToStrings {
    /**
     * This method constructs a string representation of an object for classes that use
     * the @ToString annotation. If the object's fields are themselves objects, it calls
     * itself recursively. For objects of classes that do not use the ToString annotation,
     * the normal toString method will be used.
     */
    public static String toString(Object obj) {
        if (obj == null) return "null";
        Class<?> cl = obj.getClass();

        // If the ToString annotation is present, we get an object that
        // lets us access the annotation methods
        ToString ts = cl.getAnnotation(ToString.class);
        if (ts == null) return obj.toString(); // No annotation, just use normal toString

        // We have an object of an annotated class, so start building a String
        StringBuilder result = new StringBuilder();

        // Class-level output
        if (ts.includeName()) result.append(cl.getName());
        result.append("[");

        // Access all attributes (fields) in the class by reflection
        boolean first = true;
        for (Field f : cl.getDeclaredFields()) {
            ts = f.getAnnotation(ToString.class); // Is this field annotated?
            if (ts != null) { // Annotation found, so include this field
                if (first) first = false; else result.append(",");

                // Reflection cannot normally access private members.
                // We can override this, because we are in the same module
                f.setAccessible(true);

                if (ts.includeName()) result.append(f.getName()).append("=");

                try {
                    result.append(ToStrings.toString(f.get(obj))); // Recursively call ToStrings on attributes
                } catch (ReflectiveOperationException e) {
                    // May happen, if we try to use our annotation on classes in other modules
                    // and those classes are not opened to our module. See documentation of "setAccessible"
                    e.printStackTrace();
                }
            }
        }
        result.append("]");
        return result.toString();
    }
}
