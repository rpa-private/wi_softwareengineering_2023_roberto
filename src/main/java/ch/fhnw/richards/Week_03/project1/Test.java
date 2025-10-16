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
        String goal  = "Buchs/A1R/1";

        // Best-First Suche
        List<String> bestFirstPath = BestFirst.search(mapData, start, goal);
        System.out.println("BestFirst Strecke: " + bestFirstPath);
        System.out.printf("BestFirst Länge (Summe der Edges): %.1f m%n",
                BestFirst.pathDistance(mapData, bestFirstPath));

        // A* suche
        List<String> aStarPath = AStar.search(mapData, start, goal);
        System.out.println("A* Strecke: " + aStarPath);
        System.out.printf("A* Länge (Summe der Edges): %.1f m%n",
                AStar.pathDistance(mapData, aStarPath));
    }
}
