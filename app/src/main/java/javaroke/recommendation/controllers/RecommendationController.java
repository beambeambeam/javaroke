package javaroke.recommendation.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;
import javaroke.recommendation.core.utils.metrics.PerformanceTracker;

/**
 * Abstract controller for managing graph-based recommendation processing with enhanced error
 * handling, metrics tracking, and async capabilities.
 * 
 * @param <T> The type of graph this controller manages
 * @param <V> The version implementation type for this graph
 */
public abstract class RecommendationController<T, V> {
        private static final Logger LOGGER =
                        Logger.getLogger(RecommendationController.class.getName());
        protected final ExecutorService executor = Executors.newSingleThreadExecutor();

        protected T graph;
        protected V version;
        protected String savePath;
        protected PerformanceTracker metrics;

        /**
         * Create a new controller with a fresh graph
         */
        public RecommendationController(String savePath, String versionName) {
                this.savePath = ensureTrailingSlash(savePath);
                this.graph = createNewGraph();
                this.version = createVersion(versionName);
                this.metrics = createPerformanceTracker(versionName);
                LOGGER.info("Created new controller with version " + versionName);
        }

        /**
         * Create a controller with an existing graph from a file
         */
        public RecommendationController(String savePath, String versionName, String loadPath) {
                this.savePath = ensureTrailingSlash(savePath);
                this.metrics = createPerformanceTracker(versionName);

                try {
                        LOGGER.info("Loading graph from " + loadPath);
                        long startTime = System.currentTimeMillis();
                        this.graph = loadGraph(loadPath);
                        long loadTime = System.currentTimeMillis() - startTime;
                        metrics.recordLoadTime(loadTime);

                        LOGGER.info("Graph loaded with " + getGraphSize() + " vertices in "
                                        + loadTime + "ms");

                        // Create a backup if needed
                        String realGraphPath = this.savePath + getRealGraphFilename();
                        if (!Files.exists(Paths.get(realGraphPath))) {
                                saveGraph(graph, realGraphPath);
                                LOGGER.info("Created backup at " + realGraphPath);
                        }
                } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Failed to load graph from " + loadPath, e);
                        LOGGER.info("Creating new empty graph instead");
                        this.graph = createNewGraph();
                }

                this.version = createVersion(versionName);
        }

        public T getGraph() {
                return graph;
        }

        public void setGraph(T graph) {
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
                        String realGraphPath = savePath + getRealGraphFilename();
                        graph = loadGraph(realGraphPath);

                        // Process all updates
                        int updateCount = 0;
                        while (!queue.isEmpty()) {
                                Pair<String, String> pair = queue.poll();
                                addEdgeToGraph(pair.getKey(), pair.getValue());
                                updateCount++;
                        }

                        // Save the updated graph
                        saveGraph(graph, realGraphPath);

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
                        String realGraphPath = savePath + getRealGraphFilename();
                        String resultGraphPath = savePath + getResultGraphFilename();

                        LOGGER.info("Processing graph using " + getVersionName());
                        LOGGER.info("Loading from " + realGraphPath);

                        long startTime = System.currentTimeMillis();
                        graph = loadGraph(realGraphPath);

                        // Process the graph using the selected version
                        processGraphWithVersion();

                        // Save the processed result
                        saveGraph(graph, resultGraphPath);

                        long processTime = System.currentTimeMillis() - startTime;
                        metrics.recordProcessTime(processTime);

                        LOGGER.info("Processed graph in " + processTime + "ms and saved to "
                                        + resultGraphPath);
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
         * Update the graph using the specialized incremental update
         */
        public abstract void updateIncrementally(Queue<Pair<String, String>> queue);

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

        protected String ensureTrailingSlash(String path) {
                return path.endsWith("/") ? path : path + "/";
        }

        // Abstract methods that subclasses must implement
        protected abstract T createNewGraph();

        protected abstract V createVersion(String versionName);

        protected abstract PerformanceTracker createPerformanceTracker(String versionName);

        protected abstract T loadGraph(String path);

        protected abstract void saveGraph(T graph, String path);

        protected abstract void addEdgeToGraph(String source, String target);

        protected abstract int getGraphSize();

        protected abstract void processGraphWithVersion();

        protected abstract String getVersionName();

        protected abstract String getRealGraphFilename();

        protected abstract String getResultGraphFilename();
}
