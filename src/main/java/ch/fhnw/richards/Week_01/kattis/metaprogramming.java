package ch.fhnw.richards.Week_01.kattis;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The "define" keyword is used to associate names with values.
 * The key insight is to use a HashMap to store this information.
 * When we get an "eval", we retrieve values from the HashMap
 * and compare them.
 */
public class metaprogramming {
    private static final HashMap<String, Integer> variables = new HashMap<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] parts = line.split(" ");
            if (parts[0].equals("define")) {
                variables.put(parts[2], Integer.parseInt(parts[1]));
            } else { // eval
                Integer value1 = variables.get(parts[1]);
                Integer value2 = variables.get(parts[3]);
                if (value1 == null || value2 == null) {
                    System.out.println("undefined");
                } else { // both exist
                    if ((parts[2].equals("=") && value1.equals(value2))
                            || (parts[2].equals("<") && value1 < value2)
                            || (parts[2].equals(">") && value1 > value2)
                    ) {
                        System.out.println(true);
                    } else {
                        System.out.println(false);
                    }
                }
            }
        }
    }
}
