package ch.fhnw.richards.Week_03.project1;

import java.util.*;

public class BeamSearch {  
    // nodes = Liste der Knoten im Pfad
    // g = Kosten vom Startknoten bis zum aktuellen Knoten
    // f = Summe aus g und der Luftlinie (Heuristik) zum Zielknoten
    private static final class PathRec {
        final List<String> nodes;
        final double g;
        final double f;

        // PathRec-Konstruktor
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
        // Validierung der Eingaben
        if (mapData == null || start == null || goal == null) {
            return new SearchResult(null, 0);
        }
        // Überprüfung, ob Start- und Zielknoten im Kartendaten vorhanden sind
        Map<String, MapData.GPS> nodes = mapData.getNodes();
        if (!nodes.containsKey(start) || !nodes.containsKey(goal)) {
            return new SearchResult(null, 0);
        }
        // Initialisierung der Adjazenzliste
        // Adjazenzliste: Knoten und deren Nachbarn
        Map<String, ArrayList<MapData.Destination>> adj = mapData.getAdjacencyList();
        
        // Heuristischer Startwert
        // Heuristik: geschätzte Entfernung vom Start- zum Zielknoten
        double hStart = heuristic(nodes, start, goal);
        List<String> startPath = new ArrayList<>();
        // Startpfad: nur der Startknoten
        startPath.add(start);

        List<PathRec> beam = new ArrayList<>();
        // Initialisierung des Beam-Search mit dem Startpfad
        // nodes = [Startknoten], g = 0.0, f = hStart
        beam.add(new PathRec(startPath, 0.0, hStart));

        Map<String, Double> bestGForNode = new HashMap<>();
        // Speichert die besten g-Werte für jeden Knoten
        bestGForNode.put(start, 0.0);

        // expanded: Menge der erweiterten Knoten
        Set<String> expanded = new HashSet<>();
        int visitedCount = 0;

        // Beam ist irgewndwann leer, weil es keine Kandidaten mehr gibt
        while (!beam.isEmpty()) {
            List<PathRec> candidates = new ArrayList<>();
            // Loop über alle Pfade im aktuellen Beam
            // Aktueller Beam: Liste der besten Pfade
            for (PathRec p : beam) {
                // last: letzter Knoten im Pfad
                String last = p.nodes.get(p.nodes.size() - 1);
                // visitedCount erhöhen, wenn der Knoten zum ersten Mal erweitert wird
                if (expanded.add(last)) visitedCount++;
                // wenn der letzte Knoten im Pfad das Ziel ist, Rückgabe des Pfads
                if (last.equals(goal)) {
                    return new SearchResult(p.nodes, visitedCount);
                }

                // Loop über alle Nachbarn des letzten Knotens, damit neue Pfade erstellt werden können
                for (MapData.Destination d : adj.getOrDefault(last, new ArrayList<>())) {
                    String next = d.node();
                    // Wenn der Knoten bereits im Pfad ist, überspringen
                    if (p.nodes.contains(next)) continue;
                    // g2: Kosten vom Startknoten bis zum Nachbarknoten
                    double g2 = p.g + d.distance();
                    // bestG: bester g-Wert für den Nachbarknoten
                    Double bestG = bestGForNode.get(next);
                    // wenn der neue g-Wert nicht besser ist, überspringen
                    if (bestG != null && g2 >= bestG) continue;
                    // f2: Summe aus g2 und der Heuristik (Luftlinie) zum Zielknoten
                    double f2 = g2 + heuristic(nodes, next, goal);
                    // Liste der Knoten im neuen Pfad erstellen
                    List<String> newPath = new ArrayList<>(p.nodes);
                    // Den Nachbarknoten zum neuen Pfad hinzufügen
                    newPath.add(next);
                    // Den neuen Pfad zu den Kandidaten hinzufügen
                    candidates.add(new PathRec(newPath, g2, f2));
                    // Aktualisierung des besten g-Werts für den Nachbarknoten
                    bestGForNode.put(next, g2);
                }
            }
            // Wenn keine Kandidaten vorhanden sind, Rückgabe null
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
