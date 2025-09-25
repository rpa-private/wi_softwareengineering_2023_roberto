package ch.fhnw.richards.Week_01.kattis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

/**
 * Runtime can be a problem, because some test cases are apparently very big.
 * A Scanner is too slow. A BufferedReader is faster, but even so the later
 * test cases may not complete. For our purposes, that's ok.
 *
 * The simplest solution is to use some kind of SortedMap. In this solution,
 * we use a TreeMap (an implementation of SortedMap).
 *
 * If you really want to optimize for time, you will have to use an array to
 * store the values.
 */
public class hardwoodspecies {
    private static final TreeMap<String, Integer> trees = new TreeMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int count = 0;
        String treeName = in.readLine();
        while (treeName != null) {
            if (!trees.containsKey(treeName)) {
                trees.put(treeName, 1);
            } else {
                trees.put(treeName, trees.get(treeName) + 1);
            }
            count++;
            treeName = in.readLine();
        }

        for (String tree : trees.keySet()) {
            double percent = (trees.get(tree) * 100) /  (double) count;
            System.out.format("%s %f\n", tree, percent);
        }
    }
}
