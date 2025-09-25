package ch.fhnw.richards.Week_09.annotations.solutions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * This class looks for the ToDo annotation in all classes in the current package.
 * It then collects all ToDo-messages and writes them to the console
 */
public class CreateToDoList {
    public static void main(String[] args) {
        for (String todo : getTodos(Person.class)) System.out.println(todo);
    }

    public static ArrayList<String> getTodos(Class<?> cls) {
        ArrayList<String> todos = new ArrayList<>();

        Todo annotation = cls.getAnnotation(Todo.class);
        if (annotation == null) {
            todos.add("No to-dos for this class");
        } else {
            todos.add("Class: " + annotation.content());

            for (Field f : cls.getDeclaredFields()) {
                annotation = f.getAnnotation(Todo.class);
                if (annotation != null) {
                    todos.add("Field " + f.getName() + ": " + annotation.content());
                }
            }
            for (Constructor<?> c : cls.getDeclaredConstructors()) {
                annotation = c.getAnnotation(Todo.class);
                if (annotation != null) {
                    todos.add("Constructor " + c.getName() + ": " + annotation.content());
                }
            }
            for (Method m : cls.getDeclaredMethods()) {
                annotation = m.getAnnotation(Todo.class);
                if (annotation != null) {
                    todos.add("Method " + m.getName() + ": " + annotation.content());
                }
            }
        }
        return todos;
    }
}
