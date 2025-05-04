package javaroke.recommendation.core.algorithms.pathFinding;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javaroke.recommendation.core.algorithms.pathFinding.FloydWallshallForHashMapGraph.NodeForDijkstraTraversal;
import javaroke.recommendation.core.models.graphs.HashMapGraph;

public class DijkstraForHashMapGraph {
    public static Map<String, Double> dijkstra(HashMapGraph graph, String startNode) {
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
