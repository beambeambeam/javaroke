package javaroke.recommendation.controllers;

import javaroke.recommendation.core.utils.AdjacencyMatrixUtils;

public class RecommendationController {
        public static void main(String[] args) {
                GraphHashMapController graphHashMapTypeController = new GraphHashMapController(
                                "src/main/java/javaroke/recommendation/core/data/saves/", "v1",
                                "src/main/java/javaroke/recommendation/core/data/saves/graph.json");

                AdjacencyMatrixUtils.printAdjacencyMatrix(
                                graphHashMapTypeController.getGraph().getAdjacencyMetrix());
                graphHashMapTypeController.processGraph();
                AdjacencyMatrixUtils.printAdjacencyMatrix(
                                graphHashMapTypeController.getGraph().getAdjacencyMetrix());

        }
}
