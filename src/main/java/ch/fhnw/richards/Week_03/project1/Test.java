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
        String start = "Rothrist/Neue Aarburgerstrasse/2";
        String goal  = "Aarau/Allmendweg/1";

        // Depth-First Suche
        long depthStart = System.nanoTime();
        SearchResult depthFirstResult = DepthFirst.searchWithStats(mapData, start, goal);
        long depthElapsed = System.nanoTime() - depthStart;
        List<String> depthFirstPath = depthFirstResult.path();
        System.out.println("DepthFirst Strecke: " + depthFirstPath);
        System.out.printf("DepthFirst Länge (Summe der Edges): %.1f m%n",
                DepthFirst.pathDistance(mapData, depthFirstPath));
        System.out.printf("DepthFirst Zeit: %.3f ms%n", depthElapsed / 1_000_000.0);
        System.out.printf("DepthFirst besuchte Knoten: %d%n", depthFirstResult.nodesVisited());

        // Breadth-First Suche
        long breadthStart = System.nanoTime();
        SearchResult breadthFirstResult = BreadthFirst.searchWithStats(mapData, start, goal);
        long breadthElapsed = System.nanoTime() - breadthStart;
        List<String> breadthFirstPath = breadthFirstResult.path();
        System.out.println("BreadthFirst Strecke: " + breadthFirstPath);
        System.out.printf("BreadthFirst Länge (Summe der Edges): %.1f m%n",
                BreadthFirst.pathDistance(mapData, breadthFirstPath));
        System.out.printf("BreadthFirst Zeit: %.3f ms%n", breadthElapsed / 1_000_000.0);
        System.out.printf("BreadthFirst besuchte Knoten: %d%n", breadthFirstResult.nodesVisited());

        // Greedy Best-First Suche
        long greedyStart = System.nanoTime();
        SearchResult bestFirstResult = GreedyBestFirst.searchWithStats(mapData, start, goal);
        long greedyElapsed = System.nanoTime() - greedyStart;
        List<String> bestFirstPath = bestFirstResult.path();
        System.out.println("Greedy BestFirst Strecke: " + bestFirstPath);
        System.out.printf("Greedy BestFirst Länge (Summe der Edges): %.1f m%n",
                GreedyBestFirst.pathDistance(mapData, bestFirstPath));
        System.out.printf("Greedy BestFirst Zeit: %.3f ms%n", greedyElapsed / 1_000_000.0);
        System.out.printf("Greedy BestFirst besuchte Knoten: %d%n", bestFirstResult.nodesVisited());

        // A* suche
        long aStarStart = System.nanoTime();
        SearchResult aStarResult = AStar.searchWithStats(mapData, start, goal);
        long aStarElapsed = System.nanoTime() - aStarStart;
        List<String> aStarPath = aStarResult.path();
        System.out.println("A* Strecke: " + aStarPath);
        System.out.printf("A* Länge (Summe der Edges): %.1f m%n",
                AStar.pathDistance(mapData, aStarPath));
        System.out.printf("A* Zeit: %.3f ms%n", aStarElapsed / 1_000_000.0);
        System.out.printf("A* besuchte Knoten: %d%n", aStarResult.nodesVisited());

        // Beam Search (k = 5)
        long beamStart = System.nanoTime();
        SearchResult beamResult = BeamSearch.searchWithStats(mapData, start, goal, 5);
        long beamElapsed = System.nanoTime() - beamStart;
        List<String> beamPath = beamResult.path();
        System.out.println("BeamSearch Strecke: " + beamPath);
        System.out.printf("BeamSearch Länge (Summe der Edges): %.1f m%n",
                BeamSearch.pathDistance(mapData, beamPath));
        System.out.printf("BeamSearch Zeit: %.3f ms%n", beamElapsed / 1_000_000.0);
        System.out.printf("BeamSearch besuchte Knoten: %d%n", beamResult.nodesVisited());
    }
}
