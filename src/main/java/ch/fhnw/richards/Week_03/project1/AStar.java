package ch.fhnw.richards.Week_03.project1;

import ch.fhnw.richards.Week_03.project1.MapData;

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

    public static List<String> search(MapData mapData, String start, String goal) {
        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();

        PriorityQueue<NodeRec> open = new PriorityQueue<>(Comparator.comparingDouble(NodeRec::f));
        Map<String, NodeRec> best = new HashMap<>(); // best known record per node
        Set<String> closed = new HashSet<>();

        NodeRec startRec = new NodeRec(start, 0.0, mapData.straightLineDistanceMeters(start, goal), null);
        open.add(startRec);
        best.put(start, startRec);

        while (!open.isEmpty()) {
            NodeRec current = open.poll();
            if (closed.contains(current.id)) continue;
            if (current.id.equals(goal)) {
                return reconstruct(best, current.id);
            }
            closed.add(current.id);

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
        return null; // no path
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
