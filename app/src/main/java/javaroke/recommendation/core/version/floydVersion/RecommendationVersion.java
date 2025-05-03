package javaroke.recommendation.core.version.floydVersion;

import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;
import javaroke.recommendation.core.models.graphs.GraphInterface;

/**
 * Abstract base class for recommendation system versions. Each version represents a distinct
 * processing pipeline with specific algorithms.
 * 
 * @param <T> The graph implementation type this version works with
 */
public abstract class RecommendationVersion<T extends GraphInterface> {
    private static final Logger LOGGER = Logger.getLogger(RecommendationVersion.class.getName());

    /**
     * Gets the semantic version number (e.g., "1.0.0")
     */
    public abstract String getVersion();

    /**
     * Gets the shorthand version name (e.g., "v1")
     */
    public abstract String getVersionName();

    /**
     * Process the graph with this version's algorithm pipeline
     * 
     * @param graph The graph to process
     * @throws UnsupportedOperationException if the graph type is incompatible
     */
    public abstract void process(T graph);

    /**
     * Performs an incremental update of the graph with new edge data
     * 
     * @param graph The graph to update
     * @param queue Queue of edges to add or update
     */
    public abstract void shortUpdate(T graph, Queue<Pair<String, String>> queue);

    /**
     * Helper method to log processing information
     */
    protected void logProcessingInfo(String message) {
        LOGGER.log(Level.INFO, "{0}: {1}", new Object[] {getVersionName(), message});
    }
}
