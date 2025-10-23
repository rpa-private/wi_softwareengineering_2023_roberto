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

    public static SearchResult searchWithStats(MapData mapData, String start, String goal) {
        return searchWithStats(mapData, start, goal, 5);
    }

    public static SearchResult searchWithStats(MapData mapData, String start, String goal, int beamWidth) {
        if (mapData == null || start == null || goal == null) {
            return new SearchResult(null, 0);
        }

        Map<String, MapData.GPS> nodes = mapData.getNodes();
        if (!nodes.containsKey(start) || !nodes.containsKey(goal)) {
            return new SearchResult(null, 0);
        }

        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();

        double hStart = heuristic(nodes, start, goal);
        List<String> startPath = new ArrayList<>();
        startPath.add(start);

        List<PathRec> beam = new ArrayList<>();
        beam.add(new PathRec(startPath, 0.0, hStart));

        Map<String, Double> bestGForNode = new HashMap<>();
        bestGForNode.put(start, 0.0);

        Set<String> expanded = new HashSet<>();
        int visitedCount = 0;

        while (!beam.isEmpty()) {
            List<PathRec> candidates = new ArrayList<>();
            for (PathRec p : beam) {
                String last = p.nodes.get(p.nodes.size() - 1);
                if (expanded.add(last)) visitedCount++;
                if (last.equals(goal)) {
                    return new SearchResult(p.nodes, visitedCount);
                }
                for (MapData.Destination d : adj.getOrDefault(last, new ArrayList<>())) {
                    String next = d.node();
                    if (p.nodes.contains(next)) continue;
                    double g2 = p.g + d.distance();
                    Double bestG = bestGForNode.get(next);
                    if (bestG != null && g2 >= bestG) continue;
                    double f2 = g2 + heuristic(nodes, next, goal);
                    List<String> newPath = new ArrayList<>(p.nodes);
                    newPath.add(next);
                    candidates.add(new PathRec(newPath, g2, f2));
                    bestGForNode.put(next, g2);
                }
            }
            if (candidates.isEmpty()) {
                return new SearchResult(null, visitedCount);
            }

            PathRec goalCandidate = null;
            for (PathRec c : candidates) {
                String last = c.nodes.get(c.nodes.size() - 1);
                if (last.equals(goal)) {
                    if (goalCandidate == null || c.g < goalCandidate.g) {
                        goalCandidate = c;
                    }
                }
            }
            if (goalCandidate != null) {
                if (expanded.add(goalCandidate.nodes.get(goalCandidate.nodes.size() - 1))) {
                    visitedCount++;
                }
                return new SearchResult(goalCandidate.nodes, visitedCount);
            }

            candidates.sort(Comparator.comparingDouble(pr -> pr.f));
            List<PathRec> deduped = new ArrayList<>();
            Set<String> seenEnds = new HashSet<>();
            for (PathRec c : candidates) {
                String end = c.nodes.get(c.nodes.size() - 1);
                if (seenEnds.add(end)) {
                    deduped.add(c);
                }
            }

            int limit = Math.min(beamWidth, deduped.size());
            beam = new ArrayList<>(deduped.subList(0, limit));
        }

        return new SearchResult(null, visitedCount);
    }

    public static List<String> search(MapData mapData, String start, String goal) {
        return searchWithStats(mapData, start, goal).path();
    }

    public static List<String> search(MapData mapData, String start, String goal, int beamWidth) {
        return searchWithStats(mapData, start, goal, beamWidth).path();
    }

    private static double heuristic(Map<String, MapData.GPS> nodes, String a, String goal) {
        MapData.GPS ga = nodes.get(a);
        MapData.GPS gg = nodes.get(goal);
        if (ga == null || gg == null) return 0.0;
        return MapData.haversineMeters(ga.east(), ga.north(), gg.east(), gg.north());
    }

    public static double pathDistance(MapData mapData, List<String> path) {
        if (mapData == null || path == null || path.size() < 2) return 0.0;
        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();
        double sum = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            double dist = adj.getOrDefault(from, new ArrayList<>())
                    .stream()
                    .filter(d -> d.node().equals(to))
                    .map(MapData.Destination::distance)
                    .findFirst()
                    .orElse(Double.NaN);
            if (Double.isNaN(dist)) return Double.NaN;
            sum += dist;
        }
        return sum;
    }
}
