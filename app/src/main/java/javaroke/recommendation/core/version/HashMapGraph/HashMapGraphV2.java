package javaroke.recommendation.core.version.HashMapGraph;

import javaroke.recommendation.core.algorithms.pathFinding.FloydWallshallForHashMapGraph;
import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javaroke.recommendation.core.utils.tranformers.WeightTransformerForHashmapGraph;

public class HashMapGraphV2 extends HashMapGraphV1 {
    private static final String configPath =
            "src/main/java/javaroke/recommendation/core/version/HashMapGraph/HashMapGraphV2.json";

    public HashMapGraphV2() {
        super(configPath);
    }

    @Override
    public void process(HashMapGraph graph) {
        WeightTransformerForHashmapGraph.invertWeights(graph);
        WeightTransformerForHashmapGraph.applyBiasToFloor(graph, 20);
        // WeightTransformerForHashmapGraph.applyExponentialTransformToWeights(graph, 2);
        FloydWallshallForHashMapGraph.floydWarshall(graph);
    }
}
