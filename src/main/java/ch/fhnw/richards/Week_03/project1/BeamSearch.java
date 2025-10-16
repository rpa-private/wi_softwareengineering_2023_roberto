package ch.fhnw.richards.Week_03.project1;

import java.util.*;

public class BeamSearch {
    public static List<String> search(MapData mapData, String start, String goal) {
        return search(mapData, start, goal, 5);
    }

    public static List<String> search(MapData mapData, String start, String goal, int beamWidth) {
        return null;
    }

    private static double heuristic(Map<String, MapData.GPS> nodes, String a, String goal) {
        MapData.GPS ga = nodes.get(a);
        MapData.GPS gg = nodes.get(goal);
        if (ga == null || gg == null) return 0.0;
        return MapData.haversineMeters(ga.east(), ga.north(), gg.east(), gg.north());
    }
}
