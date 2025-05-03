package javaroke.recommendation.controllers.implement;

import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;
import javaroke.recommendation.controllers.RecommendationController;
import javaroke.recommendation.core.models.graphs.GraphHashMap;
import javaroke.recommendation.core.utils.metrics.PerformanceTracker;
import javaroke.recommendation.core.utils.saves.GraphHashMapIO;
import javaroke.recommendation.core.version.RecommendationVersion;
import javaroke.recommendation.core.version.VersionFactory;

/**
 * Concrete implementation of AbstractGraphController for GraphHashMap-based recommendation
 * processing.
 */
public class GraphHashMapController
        extends RecommendationController<GraphHashMap, RecommendationVersion<GraphHashMap>> {
    private static final Logger LOGGER = Logger.getLogger(GraphHashMapController.class.getName());

    /**
     * Create a new controller with a fresh graph
     */
    public GraphHashMapController(String savePath, String versionName) {
        super(savePath, versionName);
    }

    /**
     * Create a controller with an existing graph from a file
     */
    public GraphHashMapController(String savePath, String versionName, String loadPath) {
        super(savePath, versionName, loadPath);
    }

    @Override
    protected GraphHashMap createNewGraph() {
        return new GraphHashMap();
    }

    @Override
    protected RecommendationVersion<GraphHashMap> createVersion(String versionName) {
        return VersionFactory.getVersion(versionName);
    }

    @Override
    protected PerformanceTracker createPerformanceTracker(String versionName) {
        return new PerformanceTracker(versionName);
    }

    @Override
    protected GraphHashMap loadGraph(String path) {
        try {
            return GraphHashMapIO.loadGraph(path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load graph from " + path, e);
        }
    }

    @Override
    protected void saveGraph(GraphHashMap graph, String path) {
        try {
            GraphHashMapIO.saveGraphHashMap(graph, path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save graph to " + path, e);
        }
    }

    @Override
    protected void addEdgeToGraph(String source, String target) {
        graph.addEdge(source, target, 1.0);
    }

    @Override
    protected int getGraphSize() {
        return graph.size;
    }

    @Override
    protected void processGraphWithVersion() {
        version.process(graph);
    }

    @Override
    protected String getVersionName() {
        return version.getVersionName();
    }

    @Override
    protected String getRealGraphFilename() {
        return "RealGraph.json";
    }

    @Override
    protected String getResultGraphFilename() {
        return "ResultGraph.json";
    }

    /**
     * Update the graph using the Floyd-Warshall incremental update
     */
    @Override
    public void updateIncrementally(Queue<Pair<String, String>> queue) {
        try {
            long startTime = System.currentTimeMillis();
            version.shortUpdate(graph, queue);
            long updateTime = System.currentTimeMillis() - startTime;

            metrics.recordIncrementalUpdateTime(updateTime, queue.size());
            LOGGER.info("Updated graph incrementally with " + queue.size() + " changes in "
                    + updateTime + "ms");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during incremental graph update", e);
            throw new RuntimeException("Failed to update graph incrementally", e);
        }
    }
}
