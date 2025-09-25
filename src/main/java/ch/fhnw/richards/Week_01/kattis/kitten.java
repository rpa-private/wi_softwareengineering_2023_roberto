package ch.fhnw.richards.Week_01.kattis;

import java.util.HashMap;
import java.util.Scanner;

/**
 * A good representation is important. In this case, we have
 * a map from each node-number to it's parent. To store this,
 * we use a HashMap<Integer, Integer>.
 */
public class kitten {
    private static HashMap<Integer, Integer> nodeMap = new HashMap<>();
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Kitten location
        Integer kitten = Integer.parseInt(in.nextLine().trim());

        // Tree representation: from each child-node to its parent-node
        while (in.hasNext()) { // Actual exit condition is a single value on a line
            String[] values = in.nextLine().split(" ");
            if (values.length == 1)
                break;
            else {
                int parent = Integer.parseInt(values[0]);
                for (int i = 1; i < values.length; i++) {
                    int child = Integer.parseInt(values[i]);
                    nodeMap.put(child, parent);
                }
            }
        }

        // Now find the path
        while(kitten != null) {
            System.out.print(kitten + " ");
            kitten = nodeMap.get(kitten);
        }
    }
}
