package javaroke.recommendation.controllers;

import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixPrinter;

public class MainController {
    public static void main(String[] args) {
        // Load configuration
        // ControllerConfigLoader.loadConfig();

        // Initialize the graph controller with a specific version
        HashMapGraphController graphController = new HashMapGraphController("graph.json", "v1");

        // Process the graph
        // graphController.process();

        AdjacencyMatrixPrinter
                .printAdjacencyMatrix(graphController.getGraph().getAdjacencyMatrix());

    }
}
