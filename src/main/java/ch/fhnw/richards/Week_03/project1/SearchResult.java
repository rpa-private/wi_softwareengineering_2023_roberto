package ch.fhnw.richards.Week_03.project1;

import java.util.List;

/**
 * Simple container for a search outcome plus statistics.
 *
 * @param path         The path found (null if none).
 * @param nodesVisited Number of nodes expanded/visited during the search.
 */
public record SearchResult(List<String> path, int nodesVisited) {}
