package javaroke.recommendation.core.version.HashMapGraph;

import javaroke.recommendation.core.algorithms.pathFinding.FloydWallshallForHashMapGraph;
import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javaroke.recommendation.core.models.items.MyPair;
import javaroke.recommendation.core.utils.tranformers.WeightTransformerForHashmapGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class HashMapGraphV1 extends HashMapGraphVersion {
    private static final String configPath =
            "src/main/java/javaroke/recommendation/core/version/HashMapGraph/HashMapGraphV1.json";

    public HashMapGraphV1() {
        super(configPath);
    }

    public HashMapGraphV1(String path) {
        super(path);
    }

    @Override
    public void process(HashMapGraph graph) {
        WeightTransformerForHashmapGraph.invertWeights(graph);
        WeightTransformerForHashmapGraph.applyBiasToFloor(graph, 10);
        WeightTransformerForHashmapGraph.applyExponentialTransformToWeights(graph, 2);
        FloydWallshallForHashMapGraph.floydWarshall(graph);
    }

    @Override
    public void updateData(HashMapGraph graph, Queue<MyPair<String, String>> data) {
        while (!data.isEmpty()) {
            MyPair<String, String> pair = data.poll();
            String src = pair.first;
            String dest = pair.second;

            graph.addEdge(src, dest, 1);

        }
    }

    @Override
    public List<String> getRecommendationsList(HashMapGraph graph) {
        String maximumWeightSrc = graph.maximumWeightPosition.first;
        return getRecommendationsList(graph, maximumWeightSrc);
    }

    @Override
    public List<String> getRecommendationsList(HashMapGraph graph, String src) {
        // STEP 1: Calculate and sort destinations by shortest distance from max-weight source
        List<MyPair<Double, String>> sortedDestinations = new ArrayList<>();
        for (String dest : graph.keyList) {
            double weight = graph.getWeight(src, dest);
            sortedDestinations.add(new MyPair<>(weight, dest));
        }

        // Sort ascending (shortest path first)
        sortedDestinations.sort((a, b) -> Double.compare(a.first, b.first));
        // sortedDestinations.sort(Comparator.comparingDouble(pair -> pair.first));

        // STEP 2: Count appearances of vertices in top 10 shortest paths
        Map<String, Double> vertexFrequencyMap = new HashMap<>();
        int count = 0;
        for (MyPair<Double, String> pair : sortedDestinations) {
            if (count++ >= 10)
                break;

            List<String> path = getRecommendationsListPath(graph, src, pair.second);

            for (String vertex : path) {
                vertexFrequencyMap.put(vertex, vertexFrequencyMap.getOrDefault(vertex, 0.0) + 1.0);
            }
        }


        // Sort descending (most frequent first)
        List<MyPair<Double, String>> frequencySorted = new ArrayList<>();
        for (Map.Entry<String, Double> entry : vertexFrequencyMap.entrySet()) {
            frequencySorted.add(new MyPair<>(entry.getValue(), entry.getKey()));
        }
        frequencySorted.sort((a, b) -> Double.compare(b.first, a.first));

        // STEP 4: Return top 10 most common vertices
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 10 && i < frequencySorted.size(); i++) {
            result.add(frequencySorted.get(i).second);
        }

        return result;
    }

    @Override
    public List<String> getRecommendationsListPath(HashMapGraph graph, String src, String dest) {
        return FloydWallshallForHashMapGraph.reconstructPath(graph, src, dest);
    }
}
