package javaroke.recommendation.controllers;

import javaroke.recommendation.core.models.graphs.GraphHashMap;
// import javaroke.recommendation.core.utils.metrics.PerformanceTracker;
import javaroke.recommendation.core.utils.saves.GraphHashMapIO;
import javaroke.recommendation.core.version.floydVersion.RecommendationVersion;
import javaroke.recommendation.core.version.floydVersion.VersionFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;

/**
 * Controller for managing GraphHashMap-based recommendation processing with enhanced error
 * handling, metrics tracking, and async capabilities.
 */
public class GraphHashMapController {
    private static final Logger LOGGER = Logger.getLogger(GraphHashMapController.class.getName());
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private GraphHashMap graph;
    private RecommendationVersion<GraphHashMap> version;
    private String savePath;
    private PerformanceTracker metrics;

    /**
     * Create a new controller with a fresh graph
     */
    public GraphHashMapController(String savePath, String versionName) {
        this.graph = new GraphHashMap();
        this.savePath = ensureTrailingSlash(savePath);
        this.version = VersionFactory.getVersion(versionName);
        this.metrics = new PerformanceTracker(versionName);
        LOGGER.info("Created new controller with version " + versionName);
    }

    /**
     * Create a controller with an existing graph from a file
     */
    public GraphHashMapController(String savePath, String versionName, String loadPath) {
        this.savePath = ensureTrailingSlash(savePath);
        this.metrics = new PerformanceTracker(versionName);

        try {
            LOGGER.info("Loading graph from " + loadPath);
            long startTime = System.currentTimeMillis();
            this.graph = GraphHashMapIO.loadGraph(loadPath);
            long loadTime = System.currentTimeMillis() - startTime;
            metrics.recordLoadTime(loadTime);

            LOGGER.info("Graph loaded with " + graph.size + " vertices in " + loadTime + "ms");

            // Create a backup if needed
            String realGraphPath = this.savePath + "RealGraph.json";
            if (!Files.exists(Paths.get(realGraphPath))) {
                GraphHashMapIO.saveGraphHashMap(graph, realGraphPath);
                LOGGER.info("Created backup at " + realGraphPath);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load graph from " + loadPath, e);
            LOGGER.info("Creating new empty graph instead");
            this.graph = new GraphHashMap();
        }

        this.version = VersionFactory.getVersion(versionName);
    }

    public GraphHashMap getGraph() {
        return graph;
    }

    public void setGraph(GraphHashMap graph) {
        this.graph = graph;
    }

    /**
     * Update the graph with new edges
     */
    public void updateGraph(Queue<Pair<String, String>> queue) {
        if (queue == null || queue.isEmpty()) {
            LOGGER.info("No updates to process");
            return;
        }

        try {
            LOGGER.info("Processing " + queue.size() + " updates");
            long startTime = System.currentTimeMillis();

            // Load the latest saved version
            String realGraphPath = savePath + "RealGraph.json";
            graph = GraphHashMapIO.loadGraph(realGraphPath);

            // Process all updates
            int updateCount = 0;
            while (!queue.isEmpty()) {
                Pair<String, String> pair = queue.poll();
                graph.addEdge(pair.getKey(), pair.getValue(), 1.0);
                updateCount++;
            }

            // Save the updated graph
            GraphHashMapIO.saveGraphHashMap(graph, realGraphPath);

            long updateTime = System.currentTimeMillis() - startTime;
            metrics.recordUpdateTime(updateTime, updateCount);

            LOGGER.info("Updated " + updateCount + " edges in " + updateTime + "ms");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating graph", e);
            throw new RuntimeException("Failed to update graph", e);
        }
    }

    /**
     * Process the graph with the selected version's algorithm
     */
    public void processGraph() {
        try {
            String realGraphPath = savePath + "RealGraph.json";
            String resultGraphPath = savePath + "ResultGraph.json";

            LOGGER.info("Processing graph using " + version.getVersionName());
            LOGGER.info("Loading from " + realGraphPath);

            long startTime = System.currentTimeMillis();
            graph = GraphHashMapIO.loadGraph(realGraphPath);

            // Process the graph using the selected version
            version.process(graph);

            // Save the processed result
            GraphHashMapIO.saveGraphHashMap(graph, resultGraphPath);

            long processTime = System.currentTimeMillis() - startTime;
            metrics.recordProcessTime(processTime);

            LOGGER.info("Processed graph in " + processTime + "ms and saved to " + resultGraphPath);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing graph", e);
            throw new RuntimeException("Failed to process graph", e);
        }
    }

    /**
     * Process the graph asynchronously
     * 
     * @return A Future representing the pending completion of the processing
     */
    public Future<?> processGraphAsync() {
        return executor.submit(this::processGraph);
    }

    /**
     * Update the graph using the Floyd-Warshall incremental update
     */
    public void updateFloydGraph(Queue<Pair<String, String>> queue) {
        try {
            long startTime = System.currentTimeMillis();
            version.shortUpdate(graph, queue);
            long updateTime = System.currentTimeMillis() - startTime;

            metrics.recordIncrementalUpdateTime(updateTime, queue.size());
            LOGGER.info("Updated Floyd graph with " + queue.size() + " changes in " + updateTime
                    + "ms");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during Floyd graph update", e);
            throw new RuntimeException("Failed to update Floyd graph", e);
        }
    }

    /**
     * Get performance metrics for this controller
     */
    public PerformanceTracker getMetrics() {
        return metrics;
    }

    /**
     * Release resources when done
     */
    public void shutdown() {
        executor.shutdown();
    }

    private String ensureTrailingSlash(String path) {
        return path.endsWith("/") ? path : path + "/";
    }

    /**
     * Placeholder for the PerformanceTracker class - would be implemented separately
     */
    public static class PerformanceTracker {
        @SuppressWarnings("unused")
        private String versionName;

        public PerformanceTracker(String versionName) {
            this.versionName = versionName;
        }

        public void recordLoadTime(long timeMs) {
            // Implementation would track metrics
        }

        public void recordUpdateTime(long timeMs, int updateCount) {
            // Implementation would track metrics
        }

        public void recordProcessTime(long timeMs) {
            // Implementation would track metrics
        }

        public void recordIncrementalUpdateTime(long timeMs, int updateCount) {
            // Implementation would track metrics
        }
    }
}
