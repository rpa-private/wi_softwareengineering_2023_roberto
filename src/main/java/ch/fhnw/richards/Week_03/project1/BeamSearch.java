package ch.fhnw.richards.Week_03.project1;

import java.util.*;

public class BeamSearch {
    private static final class PathRec {
        final List<String> nodes;
        final double g;
        final double f;

        PathRec(List<String> nodes, double g, double f) {
            this.nodes = nodes;
            this.g = g;
            this.f = f;
        }
    }
    public static List<String> search(MapData mapData, String start, String goal) {
        return search(mapData, start, goal, 5);
    }

    public static List<String> search(MapData mapData, String start, String goal, int beamWidth) {
        if (mapData == null || start == null || goal == null) return null;

        Map<String, MapData.GPS> nodes = mapData.getNodes();
        if (!nodes.containsKey(start) || !nodes.containsKey(goal)) return null;

        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();

        double hStart = heuristic(nodes, start, goal);
        List<String> startPath = new ArrayList<>();
        startPath.add(start);

        List<PathRec> beam = new ArrayList<>();
        beam.add(new PathRec(startPath, 0.0, hStart));

        Map<String, Double> bestGForNode = new HashMap<>();
        bestGForNode.put(start, 0.0);

        while (!beam.isEmpty()) {
            athRec bestGoal = null;
            for (PathRec p : beam) {
                String last = p.nodes.get(p.nodes.size() - 1);
                if (last.equals(goal)) {
                    if (bestGoal == null || p.g < bestGoal.g) bestGoal = p;
                }
            }
            if (bestGoal != null) return bestGoal.nodes;

            List<PathRec> candidates = new ArrayList<>();
            for (PathRec p : beam) {
                String last = p.nodes.get(p.nodes.size() - 1);
                for (MapData.Destination d : adj.getOrDefault(last, new ArrayList<>())) {
                    String next = d.node();
                    if (p.nodes.contains(next)) continue;
                    double g2 = p.g + d.distance();
                    double f2 = g2 + heuristic(nodes, next, goal);
                    List<String> newPath = new ArrayList<>(p.nodes);
                    newPath.add(next);
                    candidates.add(new PathRec(newPath, g2, f2));
                }
            }
            if (candidates.isEmpty()) return null;

            PathRec goalCandidate = null;
            for (PathRec c : candidates) {
                String last = c.nodes.get(c.nodes.size() - 1);
                if (last.equals(goal)) {
                    if (goalCandidate == null || c.g < goalCandidate.g) goalCandidate = c;
                }
            }
            if (goalCandidate != null) return goalCandidate.nodes;

            candidates.sort(Comparator.comparingDouble(pr -> pr.f));
            beam = candidates.subList(0, Math.min(beamWidth, candidates.size()));

            for (PathRec pr : beam) {
                if (pr.nodes.get(pr.nodes.size() - 1).equals(goal)) {
                    return pr.nodes;
                }
            }
        }

        return null;
    }

    private static double heuristic(Map<String, MapData.GPS> nodes, String a, String goal) {
        MapData.GPS ga = nodes.get(a);
        MapData.GPS gg = nodes.get(goal);
        if (ga == null || gg == null) return 0.0;
        return MapData.haversineMeters(ga.east(), ga.north(), gg.east(), gg.north());
    }
}
