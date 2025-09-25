package ch.fhnw.richards.Week_03;

import java.util.ArrayList;

public class DepthFirst_AdjacencyMatrix {
    private static final int[][] adjMatrix = {
            { 0, 1, 0, 0, 0, 0, 0, 0},
            { 1, 0, 1, 1, 0, 0, 0, 0},
            { 0, 1, 0, 0, 0, 0, 0, 0},
            { 0, 1, 0, 0, 1, 0, 0, 0},
            { 0, 0, 0, 1, 0, 1, 1, 0},
            { 0, 0, 0, 0, 1, 0, 0, 1},
            { 0, 0, 0, 0, 1, 0, 0, 1},
            { 0, 0, 0, 0, 0, 1, 1, 0}
    };

    public static void main(String[] args) {
        ArrayList<Character> path = depthFirst('B', 'G');
        printPath(path);
    }

    private static void printPath(ArrayList<Character> path) {
        System.out.print("Final solution: ");
        for (char node : path) System.out.printf("%c ", node);
        System.out.println();
    }

    private static ArrayList<Character> depthFirst(char start, char end) {
        ArrayList<Character> path = new ArrayList<>();
        path.add(start); // Add starting node to path

        // Search using a recursive method
        return depthFirstRecursive(path, start, end);
    }

    private static ArrayList<Character> depthFirstRecursive(ArrayList<Character> path, char current, char end) {
        if (current == end) { // Base case - we are finished!
            // Nothing to do...
        } else { // Recursive case - add a node to the path
            int[] row = adjMatrix[current - 'A']; // get row from matrix
            for (int i = 0; i < row.length; i++) {
                if (row[i] != 0) {
                    char c = (char) (i + 'A');
                    if (!haveBeenThere(path, c)) {
                        path.add(c);
                        depthFirstRecursive(path, c, end);
                        // If we have a solution, stop the loop!
                        if (path.get(path.size() - 1) == end) break;

                        // If we are here, remove the last node from the path, so we can add a different one
                        path.remove(path.size() - 1);
                    }
                }
            }
        }
        return path;
    }

    private static boolean haveBeenThere(ArrayList<Character> path, char where) {
        boolean found = false;
        for (char node : path) {
            found = found || node == where;
        }
        return found;
    }
}
