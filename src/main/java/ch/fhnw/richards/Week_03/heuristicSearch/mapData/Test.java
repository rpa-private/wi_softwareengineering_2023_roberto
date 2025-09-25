package ch.fhnw.richards.Week_03.heuristicSearch.mapData;

import java.util.ArrayList;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {
        MapData mapData = new MapData();

        Map<String, MapData.GPS> nodes = mapData.getNodes();
        for (String node : nodes.keySet()) {
            MapData.GPS gps = nodes.get(node);
            System.out.println(node + ": (" + gps.east() + "," + gps.north()+ ")");
        }

        System.out.println("=========================");

        Map<String, ArrayList<MapData.Destination>> edges = mapData.getAdjacencyList();
        for (String node : edges.keySet()) {
            edges.get(node).forEach( d -> System.out.println(node + " -> " + d.node() + " (" + d.distance() + ")"));
        }
    }
}
