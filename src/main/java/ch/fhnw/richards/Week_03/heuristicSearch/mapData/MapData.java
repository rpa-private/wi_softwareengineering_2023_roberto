package ch.fhnw.richards.Week_03.heuristicSearch.mapData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Read in map data and create two data structures:
 * - A list of nodes with their GPS coordinates
 * - An adjacency list, mapping nodes to an ArrayList of all possible definitions.
 *
 * Note that all edges are assumed to be bidirectional. Also, for simplicity, node names
 * are strings. These data structures may not be the most efficient, but they should be
 * easy to understand and use.
 */
public class MapData {
    private static final String EdgeFile = "/tmp/Edges.csv";
    private static final String NodeFile = "/tmp/Nodes.csv";
    private static final Map<String, ArrayList<Destination>> adjacencyList = new HashMap<>();
    private static final Map<String, GPS> nodes = new HashMap<>();

    /**
     * Simple class for GPS coordinates
     */
    public record GPS(Integer east, Integer north) { };

    /**
     * Simple class for edge destinations
     */
    public record Destination(String node, double distance) { };

    /**
     * Not nice - if anything goes wrong, we throw an exception
     */
    public MapData() throws Exception {
        createNodes();
        createAdjacencyList();
    }

    public Map<String, GPS> getNodes() {
        return nodes;
    }

    public Map<String, ArrayList<Destination>> getAdjacencyList() {
        return adjacencyList;
    }

    private void createNodes() throws Exception {
        File file = new File(NodeFile); // See Readme.txt !!!
        Files.lines(Paths.get(file.toURI())).map(line -> line.split(";")).forEach(a -> nodes.put(a[0], new GPS(Integer.parseInt(a[1]), Integer.parseInt(a[2]))));
    }

    private void createAdjacencyList() throws Exception {
        File file = new File(EdgeFile); // See Readme.txt !!!
        Files.lines(Paths.get(file.toURI())).map(line -> line.split(";")).forEach(
                a -> {
                    addDestination(a[0], a[1], a[2]);
                    addDestination(a[1], a[0], a[2]);
                }
        );
    }

    private void addDestination(String from, String to, String dist) {
        ArrayList<Destination> destinations = adjacencyList.get(from);
        if (destinations == null) {
            destinations = new ArrayList<Destination>();
            adjacencyList.put(from, destinations);
        }
        destinations.add(new Destination(to, Double.parseDouble(dist)));
    }
}
