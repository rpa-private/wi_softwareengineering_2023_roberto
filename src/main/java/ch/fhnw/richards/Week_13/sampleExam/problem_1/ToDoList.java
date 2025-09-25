package ch.fhnw.richards.Week_13.sampleExam.problem_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ToDoList {
    public static void main(String[] args) {
        Map<LocalDate, String> tasks = getTasks("/tmp/tasks.txt");
        for (LocalDate date : tasks.keySet()) {
            System.out.println(date + ": " + tasks.get(date));
        }
    }

    /**
     * Method getTasks takes a String as a parameter (the file path)
     * and returns a Map where the key is a LocalDate and the value
     * is a String.
     * @param filePath The path the file to read
     * @return The Map containing all of the tasks
     */
    private static Map<LocalDate, String> getTasks(String filePath) {
        // Create the data structure to hold the tasks
        Map<LocalDate, String> tasks = new HashMap<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ( (line = in.readLine()) != null) {
                String[] parts = line.split(";");
                LocalDate date = LocalDate.parse(parts[0]);
                tasks.put(date, parts[1]);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return tasks;
    }
}
