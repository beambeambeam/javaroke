package javaroke.recommendation.core.algorithms.pathFinding;

import java.util.ArrayList;
import java.util.List;
import javaroke.recommendation.core.models.graphs.HashMapGraph;

public class FloydWallshallForHashMapGraph {
    static class NodeForDijkstraTraversal {
        String name;
        double distance;

        NodeForDijkstraTraversal(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }
    }

    public static void floydWarshall(HashMapGraph graph) {
        for (String k : graph.keyList) {
            for (String i : graph.keyList) {
                for (String j : graph.keyList) {
                    double ik = graph.adjacencyList.get(i).getOrDefault(k, graph.floorWeight);
                    double kj = graph.adjacencyList.get(k).getOrDefault(j, graph.floorWeight);
                    double ij = graph.adjacencyList.get(i).getOrDefault(j, graph.floorWeight);

                    if (ik + kj < ij) {
                        graph.adjacencyList.get(i).put(j, ik + kj);
                        graph.previousVertex.get(i).put(j, k);
                    }
                }
            }
        }
    }

    private static void constructPathRecursive(HashMapGraph graph, String start, String end,
            List<String> path) {
        if (!graph.previousVertex.containsKey(start)) {
            return;
        }

        String mid = graph.previousVertex.get(start).get(end);

        if (mid == null) {
            if (graph.adjacencyList.get(start).containsKey(end)) {
                path.add(start);
                if (!start.equals(end))
                    path.add(end);
            }
        } else {
            constructPathRecursive(graph, start, mid, path);
            path.remove(path.size() - 1); // remove duplicate mid
            constructPathRecursive(graph, mid, end, path);
        }
    }

    // Example usage of constructPathRecursive to ensure it is used
    public static List<String> reconstructPath(HashMapGraph graph, String start, String end) {

        List<String> path = new ArrayList<>();
        constructPathRecursive(graph, start, end, path);
        return path;
    }
}
