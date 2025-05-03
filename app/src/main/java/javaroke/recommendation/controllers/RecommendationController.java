package javaroke.recommendation.controllers;

import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixRead;

public class RecommendationController {
        public static void main(String[] args) {
                GraphHashMapController graphHashMapTypeController = new GraphHashMapController(
                                "src/main/java/javaroke/recommendation/core/data/saves/", "v1",
                                "src/main/java/javaroke/recommendation/core/data/saves/graph.json");

                AdjacencyMatrixRead.printAdjacencyMatrix(
                                graphHashMapTypeController.getGraph().getAdjacencyMetrix());
                graphHashMapTypeController.processGraph();
                AdjacencyMatrixRead.printAdjacencyMatrix(
                                graphHashMapTypeController.getGraph().getAdjacencyMetrix());

        }
}
