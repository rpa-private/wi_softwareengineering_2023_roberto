package ch.fhnw.richards.Week_03.project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapData {
    public record GPS(double lon, double lat) {
        public double east()  { return lon; }
        public double north() { return lat; }
    }
    public record Destination(String node, double distance) {}

    private final String nodeFile;
    private final String edgeFile;

    private final Map<String, GPS> nodes = new HashMap<>();
    private final Map<String, ArrayList<Destination>> adjacencyList = new HashMap<>();

    public MapData() throws IOException {
        this(null, null);
    }

    //Bradley bitte Pfad zu den Nodes und Edges ersetzten

    public MapData(String nodeFile, String edgeFile) throws IOException {
        String baseDir = "/Users/roberto.panizza/Github/FHNW/Nodes_Edges";

        this.nodeFile = (nodeFile == null || nodeFile.isBlank())
                ? Path.of(baseDir, "Nodes.csv").toString()
                : nodeFile;
        this.edgeFile = (edgeFile == null || edgeFile.isBlank())
                ? Path.of(baseDir, "Edges.csv").toString()
                : edgeFile;

        createNodes();
        createAdjacencyList();
    }

    public Map<String, GPS> getNodes() {
        return nodes;
    }

    public Map<String, ArrayList<Destination>> getAdjacencyList() {
        return adjacencyList;
    }

    private void createNodes() throws IOException {
        File file = new File(nodeFile); // See Readme.txt for IDE resource loading caveat
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] a = splitSemicolon(line);
                if (a.length < 3) continue;
                String name = a[0].trim();
                double lon = parseDouble(a[1]);
                double lat = parseDouble(a[2]);
                nodes.put(name, new GPS(lon, lat));
            }
        }
    }

    private void createAdjacencyList() throws IOException {
        File file = new File(edgeFile); // See Readme.txt for IDE resource loading caveat
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] a = splitSemicolon(line);
                if (a.length < 3) continue;
                String from = a[0].trim();
                String to   = a[1].trim();
                double dist = parseDouble(a[2]);
                addDestination(from, to, dist);
                addDestination(to, from, dist); // bidirectional
            }
        }
    }

    private void addDestination(String from, String to, double dist) {
        ArrayList<Destination> list = adjacencyList.computeIfAbsent(from, k -> new ArrayList<>());
        list.add(new Destination(to, dist));
    }

    private static String[] splitSemicolon(String line) {
        // no quoting expected in provided data; keep it simple
        return line.split("\\s*;\\s*");
    }

    private static double parseDouble(String s) {
        // Be robust against commas as decimal separators
        String norm = s.trim().replace(',', '.');
        return Double.parseDouble(norm);
    }

    /**
     * Returns the great-circle distance (Haversine) between two named nodes, in meters.
     * If any node is unknown, returns 0.
     */
    public double straightLineDistanceMeters(String nodeA, String nodeB) {
        GPS a = nodes.get(nodeA);
        GPS b = nodes.get(nodeB);
        if (a == null || b == null) return 0.0;
        return haversineMeters(a.lon, a.lat, b.lon, b.lat);
    }

    /** Haversine formula for distance between two lon/lat points in meters. */
    public static double haversineMeters(double lon1, double lat1, double lon2, double lat2) {
        final double R = 6371_000.0; // Earth radius in meters
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double dPhi = Math.toRadians(lat2 - lat1);
        double dLam = Math.toRadians(lon2 - lon1);
        double sinDphi = Math.sin(dPhi / 2);
        double sinDlam = Math.sin(dLam / 2);
        double a = sinDphi * sinDphi + Math.cos(phi1) * Math.cos(phi2) * sinDlam * sinDlam;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
