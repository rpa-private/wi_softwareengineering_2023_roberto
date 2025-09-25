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
public class ReadFilePrintLineLengths {
    public static void main(String[] args) {
        System.out.println("Enter the full path to a text file");
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        in.close();

        Path path = Paths.get(filePath);
        try {
            Files.lines(path) // Read lines from files
                    .map(String::length)
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
