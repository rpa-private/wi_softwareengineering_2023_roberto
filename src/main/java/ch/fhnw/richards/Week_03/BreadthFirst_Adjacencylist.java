package ch.fhnw.richards.Week_03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BreadthFirst_Adjacencylist {
    private static final Map<Character, char[]> adjList = new HashMap<>();

    public static void main(String[] args) {
        initMapData();
        ArrayList<Character> path = breadthFirst('B', 'G');
        printPath(path);
    }

    private static void initMapData() {
        adjList.put('A', new char[]{'B'});
        adjList.put('B', new char[]{'A', 'C', 'D'});
        adjList.put('C', new char[]{'B'});
        adjList.put('D', new char[]{'B', 'E'});
        adjList.put('E', new char[]{'D', 'F', 'G'});
        adjList.put('F', new char[]{'E', 'H'});
        adjList.put('G', new char[]{'E', 'H'});
        adjList.put('H', new char[]{'F', 'G'});
    }

    private static void printPath(ArrayList<Character> path) {
        System.out.print("Final solution: ");
        for (char node : path) System.out.printf("%c ", node);
        System.out.println();
    }

    private static ArrayList<Character> breadthFirst(char start, char end) {
        ArrayList<ArrayList<Character>> paths = new ArrayList<>();
        ArrayList<Character> startingPath = new ArrayList<>();
        startingPath.add(start);
        paths.add(startingPath); // Add starting node to path

        boolean solutionFound = false;
        while (!solutionFound && paths.size() > 0) {
            // Remove the first element from the paths
            ArrayList<Character> oldPath = paths.remove(0);

            // Extend it in all possible ways, adding each new path to the end of the list
            char current = oldPath.get(oldPath.size() - 1);
            char[] connectedNodes = adjList.get(current);
            for (char c : connectedNodes) {
                ArrayList<Character> newPath = (ArrayList<Character>) oldPath.clone();
                newPath.add(c);
                paths.add(newPath);
                if (c == end) {
                    solutionFound = true;
                    break; // Otherwise will continue searching
                }
            }
        }
        return paths.size() == 0 ? null : paths.get(paths.size() - 1);
    }
}