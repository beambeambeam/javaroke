package javaroke.recommendation.controllers;

import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixRead;
import javaroke.recommendation.core.models.graphs.GraphHashMap;

/**
 * Main controller class that demonstrates the use of the graph controller factory.
 */
public class MainController {
    // Base paths for data and graph files
    private static final String SAVE_PATH =
            "src/main/java/javaroke/recommendation/core/data/saves/";
    private static final String GRAPH_FILE = SAVE_PATH + "graph.json";

    public static void main(String[] args) {
        // Get the factory instance
        GraphControllerFactory factory = GraphControllerFactory.getInstance();

        // Create a GraphHashMap controller using the factory
        RecommendationController<?, ?> controller =
                factory.createController("hashmap", SAVE_PATH, "v1", GRAPH_FILE);

        // We need to cast to access specific graph methods
        GraphHashMap graph = (GraphHashMap) controller.getGraph();

        // Print the initial adjacency matrix
        System.out.println("Initial Graph:");
        AdjacencyMatrixRead.printAdjacencyMetrix(graph.getAdjacencyMetrix());

        // Process the graph
        System.out.println("\nProcessing graph...");
        controller.processGraph();

        // Print the processed adjacency matrix
        System.out.println("\nProcessed Graph:");
        AdjacencyMatrixRead.printAdjacencyMetrix(graph.getAdjacencyMetrix());

        // Clean up resources
        controller.shutdown();
        System.out.println("\nController shutdown complete.");

        // Example of how to use with different controller types:
        /*
         * // Using a different controller type RecommendationController<?, ?>
         * coordinate2DController = factory.createController("2dtree", SAVE_PATH, "v1", GRAPH_FILE);
         * 
         * // Process with the 2D coordinate controller coordinate2DController.processGraph();
         * coordinate2DController.shutdown();
         */
    }
}
