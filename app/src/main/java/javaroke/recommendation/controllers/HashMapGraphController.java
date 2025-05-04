package javaroke.recommendation.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javaroke.recommendation.core.models.items.MyPair;
import javaroke.recommendation.core.saves.HashMapGraphIO;
import javaroke.recommendation.core.utils.metrics.PerformanceTracker;
import javaroke.recommendation.core.version.HashMapGraph.HashMapGraphV1;
import javaroke.recommendation.core.version.HashMapGraph.HashMapGraphV2;
import javaroke.recommendation.core.version.HashMapGraph.HashMapGraphVersion;

public class HashMapGraphController {
    private static final String ORIGINAL_GRAPH_FILE = "original_graph.json";
    private static final String FLOYD_GRAPH_FILE = "floyd_graph.json";
    private static final Logger LOGGER = Logger.getLogger(HashMapGraphController.class.getName());

    private HashMapGraph graph;
    private HashMapGraphVersion version;
    private final PerformanceTracker tracker;

    public HashMapGraphController(String version) {
        this.tracker = new PerformanceTracker(version);
        try {
            this.graph = new HashMapGraph();
            this.version = loadVersion(version);
            LOGGER.log(Level.INFO, "Loaded version successfully: {0}", version);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Initialization failed: " + e.getMessage(), e);
        }
    }

    public HashMapGraphController(String loadFileName, String version) {
        this.tracker = new PerformanceTracker(version);
        try {
            this.graph = loadGraph(loadFileName);
            LOGGER.log(Level.INFO, "Loaded graph successfully from: {0}", loadFileName);

            this.version = loadVersion(version);
            LOGGER.log(Level.INFO, "Loaded version successfully: {0}", version);

            saveGraph(ORIGINAL_GRAPH_FILE);
            LOGGER.log(Level.INFO, "Saved graph successfully to: {0}", ORIGINAL_GRAPH_FILE);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Initialization failed: " + e.getMessage(), e);
        }
    }

    public void process() {
        try {
            long start = System.currentTimeMillis();

            graph = loadGraph(ORIGINAL_GRAPH_FILE);
            LOGGER.log(Level.INFO, "Loaded original graph successfully from: {0}",
                    ORIGINAL_GRAPH_FILE);

            version.process(graph);
            LOGGER.log(Level.INFO, "Processed graph with version: {0}",
                    version.getClass().getSimpleName());

            saveGraph(FLOYD_GRAPH_FILE);
            LOGGER.log(Level.INFO, "Saved processed graph successfully to: {0}", FLOYD_GRAPH_FILE);

            tracker.recordProcessTime(System.currentTimeMillis() - start);
            LOGGER.log(Level.INFO, "Process successfully: {0} ms",
                    System.currentTimeMillis() - start);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Graph processing failed: " + e.getMessage(), e);
        }
    }

    public void updateData(Queue<MyPair<String, String>> data) {
        try {
            long start = System.currentTimeMillis();

            graph = loadGraph(ORIGINAL_GRAPH_FILE);
            LOGGER.log(Level.INFO, "Loaded original graph successfully from: {0}",
                    ORIGINAL_GRAPH_FILE);

            version.updateData(graph, data);
            LOGGER.log(Level.INFO, "Updated graph with version: {0}",
                    version.getClass().getSimpleName());

            saveGraph(ORIGINAL_GRAPH_FILE);
            LOGGER.log(Level.INFO, "Saved processed graph successfully to: {0}", FLOYD_GRAPH_FILE);

            tracker.recordUpdateTime(System.currentTimeMillis() - start, data.size());
            LOGGER.log(Level.INFO, "Recommendations list retrieved successfully: {0} ms",
                    System.currentTimeMillis() - start);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Graph updating failed: " + e.getMessage(), e);
        }

    }

    public List<String> getRecommendationsList() {
        try {
            List<String> recommendationVertexList = new ArrayList<>();

            long start = System.currentTimeMillis();
            graph = loadGraph(FLOYD_GRAPH_FILE);
            LOGGER.log(Level.INFO, "Loaded original graph successfully from: {0}",
                    ORIGINAL_GRAPH_FILE);

            recommendationVertexList = version.getRecommendationsList(graph);
            tracker.recordGetRecommendList(System.currentTimeMillis() - start);
            LOGGER.log(Level.INFO, "Recommendations list retrieved successfully: {0} ms",
                    System.currentTimeMillis() - start);

            return recommendationVertexList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to get recommendations list: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }



    private void saveGraph(String fileName) throws Exception {
        long start = System.currentTimeMillis();
        if (isNullOrEmpty(fileName)) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }

        String path = resolvePath(fileName);
        HashMapGraphIO.saveGraphHashMap(graph, path);
        tracker.recordUpdateTime(System.currentTimeMillis() - start, graph.size);
    }

    private HashMapGraph loadGraph(String fileName) throws Exception {
        long start = System.currentTimeMillis();
        if (isNullOrEmpty(fileName)) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }

        String path = resolvePath(fileName);
        if (!Files.exists(Paths.get(path))) {
            throw new IllegalArgumentException("File does not exist: " + path);
        }

        HashMapGraph graph = HashMapGraphIO.loadGraph(path);

        if (graph.maximumWeightPosition.first == null) {
            graph.updateMaximumWeightPosition();
        }
        tracker.recordLoadTime(System.currentTimeMillis() - start);

        return graph;
    }

    private HashMapGraphVersion loadVersion(String version) {
        if (isNullOrEmpty(version)) {
            throw new IllegalArgumentException("Version cannot be null or empty");
        }

        LOGGER.log(Level.FINE, "Loading version: {0}", version);
        switch (version.toLowerCase()) {
            case "v1":
                return new HashMapGraphV1();
            case "v2":
                return new HashMapGraphV2() {

                };
            default:
                throw new IllegalArgumentException("Unsupported version: " + version);
        }
    }

    private String resolvePath(String fileName) {
        String folder = ControllerConfigLoader.get("SAVE_FOLDER");
        return Paths.get(folder, fileName).toString();
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public HashMapGraph getGraph() {
        return graph;
    }

    public HashMapGraphVersion getVersion() {
        return version;
    }

    public Map<String, Object> getMetricSummary() {
        return tracker.getMetricSummary();
    }
}
