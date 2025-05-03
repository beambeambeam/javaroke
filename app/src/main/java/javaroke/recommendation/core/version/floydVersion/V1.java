package javaroke.recommendation.core.version.floydVersion;

import java.util.Queue;
import javafx.util.Pair;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaroke.recommendation.core.algorithms.GraphAlgorithmForHashMap;
import javaroke.recommendation.core.models.graphs.GraphHashMap;
import javaroke.recommendation.core.utils.tranformers.WeightTransformerForHashmap;

public class V1 extends RecommendationVersion<GraphHashMap> {
    private static final Logger LOGGER = Logger.getLogger(V1.class.getName());
    private static final double BIAS_VALUE = 10.0;
    private static final double EXPONENT_VALUE = 2.0;

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getVersionName() {
        return "v1";
    }

    @Override
    public void process(GraphHashMap graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        logProcessingInfo("Starting graph processing");

        try {
            // Apply weight transformations
            WeightTransformerForHashmap.invertWeights(graph);
            logProcessingInfo("Inverted weights");

            WeightTransformerForHashmap.applyBiasToFloor(graph, BIAS_VALUE);
            logProcessingInfo("Applied bias to floor: " + BIAS_VALUE);

            WeightTransformerForHashmap.applyExponentialTransformToWeights(graph, EXPONENT_VALUE);
            logProcessingInfo("Applied exponential transformation: " + EXPONENT_VALUE);

            // Apply Floyd-Warshall algorithm
            long startTime = System.currentTimeMillis();
            GraphAlgorithmForHashMap.floydWarshall(graph);
            long endTime = System.currentTimeMillis();

            logProcessingInfo("Completed Floyd-Warshall in " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing graph with " + getVersionName(), e);
            throw new RuntimeException("Failed to process graph with " + getVersionName(), e);
        }
    }

    @Override
    public void shortUpdate(GraphHashMap graph, Queue<Pair<String, String>> queue) {
        if (queue == null || queue.isEmpty()) {
            return;
        }

        logProcessingInfo("Short update requested with " + queue.size() + " items");
        // For V1, we don't have an incremental update implementation
        // so we just reprocess the entire graph
        process(graph);
    }
}
