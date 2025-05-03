package javaroke.recommendation.core.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javaroke.recommendation.core.models.graphs.GraphHashMap;

public class GraphAlgorithmForHashMap {
    static class NodeForDijkstraTraversal {
        String name;
        double distance;

        NodeForDijkstraTraversal(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }
    }

    public static void floydWarshall(GraphHashMap graph) {
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

    private static void constructPathRecursive(GraphHashMap graph, String start, String end,
            List<String> path) {
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
    public static List<String> reconstructPathFromGraphHashMap(GraphHashMap graph, String start,
            String end) {

        List<String> path = new ArrayList<>();
        constructPathRecursive(graph, start, end, path);
        return path;
    }

    // public static List<String> reconstructPathFromGraphHashMap(GraphHashMap graph, String start,
    // String end) {
    // HashMap<String, String> previous = graph.previousVertex.get(start);
    // if (previous == null)
    // return new ArrayList<>();

    // List<String> path = new ArrayList<>();
    // for (String at = end; at != null; at = previous.get(at)) {
    // path.add(at);
    // }
    // path.add(start);
    // Collections.reverse(path);

    // // return path.get(0).equals(start) ? path : new ArrayList<>();
    // return path;
    // }


    public static Map<String, Double> dijkstra(GraphHashMap graph, String startNode) {
        Map<String, Double> distances = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<NodeForDijkstraTraversal> queue =
                new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));

        // Initialize distances
        for (String node : graph.keyList) {
            distances.put(node, node.equals(startNode) ? 0.0 : graph.floorWeight);
        }

        queue.add(new NodeForDijkstraTraversal(startNode, 0.0));

        while (!queue.isEmpty()) {
            NodeForDijkstraTraversal current = queue.poll();

            if (visited.contains(current.name))
                continue;

            visited.add(current.name);

            Map<String, Double> neighbors =
                    graph.adjacencyList.getOrDefault(current.name, new HashMap<>());

            for (Map.Entry<String, Double> entry : neighbors.entrySet()) {
                String neighbor = entry.getKey();
                double weight = entry.getValue();

                if (visited.contains(neighbor))
                    continue;

                double newDist = distances.get(current.name) + weight;
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    queue.add(new NodeForDijkstraTraversal(neighbor, newDist));
                }
            }
        }

        return distances;
    }



}
