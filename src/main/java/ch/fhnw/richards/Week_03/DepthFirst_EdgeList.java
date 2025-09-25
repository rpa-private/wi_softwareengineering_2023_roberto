package ch.fhnw.richards.Week_03;

import java.util.ArrayList;

public class DepthFirst_EdgeList {
    private static final char[][] edgeList = {{'A', 'B'}, {'B', 'A'}, {'B', 'C'}, {'C', 'B'}, {'B', 'D'}, {'D', 'B'}, {'D', 'E'}, {'E', 'D'},
            {'E', 'F'}, {'F', 'E'}, {'E', 'G'}, {'G', 'E'}, {'F', 'H'}, {'H', 'F'}, {'G', 'H'}, {'H', 'G'}};

    public static void main(String[] args) {
        ArrayList<char[]> path = depthFirst('B', 'G');
        printPath(path);
    }

    private static void printPath(ArrayList<char[]> path) {
        System.out.print("Final solution: ");
        for (char[] edge : path) System.out.printf("[%c, %c] ", edge[0], edge[1]);
        System.out.println();
    }

    private static ArrayList<char[]> depthFirst(char start, char end) {
        ArrayList<char[]> path = new ArrayList<>();

        // Search using a recursive method
        return depthFirstRecursive(path, start, end);
    }

    private static ArrayList<char[]> depthFirstRecursive(ArrayList<char[]> path, char current, char end) {
        if (current == end) { // Base case - we are finished!
            // Nothing to do...
        } else { // Recursive case - add an edge to the path
            for (char[] edge : edgeList) {
                if (edge[0] == current && !haveBeenThere(path, edge[1])) {
                    path.add(edge);
                    depthFirstRecursive(path, edge[1], end);
                    // If we have a solution, stop the loop!
                    if (path.get(path.size() - 1)[1] == end) break;

                    // If we are here, remove the last node from the path, so we can add a different one
                    path.remove(path.size() - 1);
                }
            }
        }
        return path;
    }

    private static boolean haveBeenThere(ArrayList<char[]> path, char where) {
        boolean found = false;
        for (char[] edge : path) {
            found = found || edge[0] == where || edge[1] == where;
        }
        return found;
    }
}
