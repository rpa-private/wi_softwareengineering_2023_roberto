package ch.fhnw.richards.Week_03.project1;

import java.util.*;

public class DepthFirst {

    public static SearchResult searchWithStats(MapData mapData, String start, String goal) {
        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();
        Set<String> visited = new HashSet<>();
        Deque<List<String>> stack = new ArrayDeque<>();
        int visitedCount = 0;

        stack.push(Collections.singletonList(start));

        while (!stack.isEmpty()) {
            List<String> path = stack.pop();
            String last = path.get(path.size() - 1);
            if (!visited.add(last)) continue;
            visitedCount++;
            if (last.equals(goal)) {
                return new SearchResult(path, visitedCount);
            }

            ArrayList<MapData.Destination> neighbors = adj.getOrDefault(last, new ArrayList<>());
            
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                MapData.Destination dest = neighbors.get(i);
                if (visited.contains(dest.node())) continue;
                if (path.contains(dest.node())) continue; 
                List<String> newPath = new ArrayList<>(path);
                newPath.add(dest.node());
                stack.push(newPath);
            }
        }
        return new SearchResult(null, visitedCount); 
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
