package ch.fhnw.richards.Week_03.project1;

import java.util.*;

/**
 * A* search with double-valued costs and a straight-line (Haversine) heuristic.
 */
public class AStar {

    private static class NodeRec {
        String id;
        double g; // cost so far
        double h; // heuristic to goal
        String parent;

        double f() { return g + h; }

        NodeRec(String id, double g, double h, String parent) {
            this.id = id;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }
    }

    public static SearchResult searchWithStats(MapData mapData, String start, String goal) {
        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();

        PriorityQueue<NodeRec> open = new PriorityQueue<>(Comparator.comparingDouble(NodeRec::f));
        Map<String, NodeRec> best = new HashMap<>(); // best known record per node
        Set<String> closed = new HashSet<>();
        int visitedCount = 0;

        NodeRec startRec = new NodeRec(start, 0.0, mapData.straightLineDistanceMeters(start, goal), null);
        open.add(startRec);
        best.put(start, startRec);

        while (!open.isEmpty()) {
            NodeRec current = open.poll();
            if (closed.contains(current.id)) continue;
            closed.add(current.id);
            visitedCount++;
            if (current.id.equals(goal)) {
                return new SearchResult(reconstruct(best, current.id), visitedCount);
            }

            for (MapData.Destination d : adj.getOrDefault(current.id, new ArrayList<>())) {
                if (closed.contains(d.node())) continue;
                double tentativeG = current.g + d.distance();
                NodeRec known = best.get(d.node());
                if (known == null || tentativeG < known.g) {
                    double h = mapData.straightLineDistanceMeters(d.node(), goal);
                    NodeRec nr = new NodeRec(d.node(), tentativeG, h, current.id);
                    best.put(d.node(), nr);
                    open.add(nr);
                }
            }
        }
        return new SearchResult(null, visitedCount); // no path
    }

    public static List<String> search(MapData mapData, String start, String goal) {
        return searchWithStats(mapData, start, goal).path();
    }

    private static List<String> reconstruct(Map<String, NodeRec> best, String goal) {
        LinkedList<String> path = new LinkedList<>();
        String cur = goal;
        while (cur != null) {
            path.addFirst(cur);
            NodeRec r = best.get(cur);
            cur = (r == null) ? null : r.parent;
        }
        return path;
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
