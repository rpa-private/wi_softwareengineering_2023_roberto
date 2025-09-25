package ch.fhnw.richards.Week_02.Streams.FileExample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * To use the program, enter the full path to a text file in the console.
 * The program will then count the number of each symbol that appears in
 * the file.
 */
public class ReadFileCountChars {
    public static void main(String[] args) {
        System.out.println("Enter the full path to a text file");
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        in.close();

        Path path = Paths.get(filePath);
        try {
            Files.lines(path) // Read lines from files
                    .collect(Collectors.joining()) // Join them all together into one huge string
                    .chars() // Convert into a stream of chars (actually ints)
                    .mapToObj(c -> (char) c) // Turn into the corresponding objects (cast needed, because we want Characters, not Integers)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) // Group by identity (same value) and count each group
                    .forEach((x, y) -> System.out.println(x + " - " + y)); // Print each pair of values (char and the number of occurrences)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
