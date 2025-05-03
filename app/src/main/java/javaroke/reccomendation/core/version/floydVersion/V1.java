package javaroke.reccomendation.core.version.floydVersion;

import java.util.Queue;
import javafx.util.Pair;
import javaroke.reccomendation.core.algorithms.GraphAlgorithmForHashMap;
import javaroke.reccomendation.core.models.graphs.GraphHashMap;
import javaroke.reccomendation.core.utils.tranformers.WeightTranformerForHashmap;

public class V1<T> extends ReccomendationVersion<T> {
    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getVersionName() {
        return "v1";
    }

    @Override
    public void process(T graph) {
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

    public void shortUpdate(T graph, Queue<Pair<String, String>> queue) {

    }
}
