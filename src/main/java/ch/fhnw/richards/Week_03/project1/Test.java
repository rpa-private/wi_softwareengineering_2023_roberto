package ch.fhnw.richards.Week_03.project1;

import java.util.List;
import java.util.Map;

//https://s.geo.admin.ch/syvoz9cgz26e

public class Test {
    public static void main(String[] args) throws Exception {

        MapData mapData = new MapData();


        Map<String, MapData.GPS> nodes = mapData.getNodes();
        for (String node : nodes.keySet()) {
            MapData.GPS gps = nodes.get(node);
            System.out.printf("%s: (lon=%.6f, lat=%.6f)%n", node, gps.east(), gps.north());
        }

        System.out.println("=========================");

        // Start und ende wählen
        String start = "Rothrist/Schellbergweg/1";
        String goal  = "Aarau/Buchsstrasse/1";

        // Depth-First Suche
        long depthStart = System.nanoTime();
        List<String> depthFirstPath = DepthFirst.search(mapData, start, goal);
        long depthElapsed = System.nanoTime() - depthStart;
        System.out.println("DepthFirst Strecke: " + depthFirstPath);
        System.out.printf("DepthFirst Länge (Summe der Edges): %.1f m%n",
                DepthFirst.pathDistance(mapData, depthFirstPath));
        System.out.printf("DepthFirst Zeit: %.3f ms%n", depthElapsed / 1_000_000.0);

        // Breadth-First Suche
        long breadthStart = System.nanoTime();
        List<String> breadthFirstPath = BreadthFirst.search(mapData, start, goal);
        long breadthElapsed = System.nanoTime() - breadthStart;
        System.out.println("BreadthFirst Strecke: " + breadthFirstPath);
        System.out.printf("BreadthFirst Länge (Summe der Edges): %.1f m%n",
                BreadthFirst.pathDistance(mapData, breadthFirstPath));
        System.out.printf("BreadthFirst Zeit: %.3f ms%n", breadthElapsed / 1_000_000.0);

        // Greedy Best-First Suche
        long greedyStart = System.nanoTime();
        List<String> bestFirstPath = GreedyBestFirst.search(mapData, start, goal);
        long greedyElapsed = System.nanoTime() - greedyStart;
        System.out.println("Greedy BestFirst Strecke: " + bestFirstPath);
        System.out.printf("Greedy BestFirst Länge (Summe der Edges): %.1f m%n",
                GreedyBestFirst.pathDistance(mapData, bestFirstPath));
        System.out.printf("Greedy BestFirst Zeit: %.3f ms%n", greedyElapsed / 1_000_000.0);

        // A* suche
        long aStarStart = System.nanoTime();
        List<String> aStarPath = AStar.search(mapData, start, goal);
        long aStarElapsed = System.nanoTime() - aStarStart;
        System.out.println("A* Strecke: " + aStarPath);
        System.out.printf("A* Länge (Summe der Edges): %.1f m%n",
                AStar.pathDistance(mapData, aStarPath));
        System.out.printf("A* Zeit: %.3f ms%n", aStarElapsed / 1_000_000.0);

        // Beam Search
        // Beam Search (k = 5)
        long beamStart = System.nanoTime();
        List<String> beamPath = BeamSearch.search(mapData, start, goal, 5);
        long beamElapsed = System.nanoTime() - beamStart;
        System.out.println("BeamSearch Strecke: " + beamPath);
        System.out.printf("BeamSearch Länge (Summe der Edges): %.1f m%n",
                BeamSearch.pathDistance(mapData, beamPath));
        System.out.printf("BeamSearch Zeit: %.3f ms%n", beamElapsed / 1_000_000.0);
    }
}
