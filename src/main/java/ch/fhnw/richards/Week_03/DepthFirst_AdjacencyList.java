package ch.fhnw.richards.Week_03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DepthFirst_AdjacencyList {
    private static final Map<Character, char[]> adjList = new HashMap<>();

    public static void main(String[] args) {
        initMapData();
        ArrayList<Character> path = depthFirst('B', 'G');
        printPath(path);
    }

    private static void initMapData() {
        adjList.put('A', new char[]{'B'});
        adjList.put('B', new char[]{'A','C','D'});
        adjList.put('C', new char[]{'B'});
        adjList.put('D', new char[]{'B','E'});
        adjList.put('E', new char[]{'D','F','G'});
        adjList.put('F', new char[]{'E','H'});
        adjList.put('G', new char[]{'E','H'});
        adjList.put('H', new char[]{'F','G'});
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
            char[] connectedNodes = adjList.get(current);
            for (char c : connectedNodes) {
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
