package javaroke.recommendation.core.version.floydVersion;

import java.util.Queue;
import javafx.util.Pair;
import javaroke.recommendation.core.algorithms.GraphAlgorithmForHashMap;
import javaroke.recommendation.core.models.graphs.GraphHashMap;
import javaroke.recommendation.core.models.graphs.GraphInterface;
import javaroke.recommendation.core.utils.tranformers.WeightTranformerForHashmap;

public class V2 extends RecommendationVersion<GraphInterface> {
    @Override
    public String getVersion() {
        return "2.0.0";
    }

    @Override
    public String getVersionName() {
        return "v2";
    }

    @Override
    public void process(GraphInterface graph) {
        if (graph instanceof GraphHashMap) {
            processWithGraphHashMap((GraphHashMap) graph);
        } else {
            throw new IllegalArgumentException("Graph must be an instance of GraphHashMap");
        }
    }

    public void processWithGraphHashMap(GraphHashMap graph) {
        WeightTranformerForHashmap.invertWeights(graph);
        WeightTranformerForHashmap.applyBiasToFloor(graph, 10);
        WeightTranformerForHashmap.applyExponentialTransformToWeights(graph, 2);
        GraphAlgorithmForHashMap.floydWarshall(graph);
    }

    public void shortUpdate(GraphInterface graph, Queue<Pair<String, String>> queue) {

    }
}
