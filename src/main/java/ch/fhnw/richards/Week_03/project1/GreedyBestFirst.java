package ch.fhnw.richards.Week_03.project1;

import java.util.*;

/**
 * Greedy Best-First Search using a heuristic (straight-line distance in meters).
 * Uses double values for distances.
 */
public class GreedyBestFirst {

    /** Simple container for a path plus its heuristic value. */
    private record Path(List<String> nodes, double h) {}

    public static SearchResult searchWithStats(MapData mapData, String start, String goal) {
        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingDouble(p -> p.h));
        int visitedCount = 0;

        queue.add(new Path(Collections.singletonList(start), mapData.straightLineDistanceMeters(start, goal)));

        while (!queue.isEmpty()) {
            Path current = queue.poll();
            String last = current.nodes.get(current.nodes.size() - 1);
            if (!visited.add(last)) continue;
            visitedCount++;
            if (last.equals(goal)) {
                return new SearchResult(current.nodes, visitedCount);
            }

            for (MapData.Destination d : adj.getOrDefault(last, new ArrayList<>())) {
                if (visited.contains(d.node())) continue;
                List<String> newPath = new ArrayList<>(current.nodes);
                newPath.add(d.node());
                double h = mapData.straightLineDistanceMeters(d.node(), goal);
                queue.add(new Path(newPath, h));
            }
        }
        return new SearchResult(null, visitedCount); // no path found
    }

    public static List<String> search(MapData mapData, String start, String goal) {
        return searchWithStats(mapData, start, goal).path();
    }

    /** Utility to sum the edge distances of a path (in meters). */
    public static double pathDistance(MapData mapData, List<String> path) {
        if (path == null || path.size() < 2) return 0.0;
        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();
        double sum = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            double dist = adj.getOrDefault(from, new ArrayList<>())
                    .stream().filter(d -> d.node().equals(to))
                    .map(MapData.Destination::distance)
                    .findFirst().orElse(Double.NaN);
            if (Double.isNaN(dist)) return Double.NaN;
            sum += dist;
        }
        return sum;
    }
}
