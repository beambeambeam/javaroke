package javaroke.reccomendation.controllers;

import javaroke.reccomendation.core.utils.AdjacencyMatrixUtils;

public class RecommendationController {
    public static void main(String[] args) {
        GraphHashMapTypeController graphHashMapTypeController = new GraphHashMapTypeController(
                "src/main/java/javaroke/reccomendation/core/data/saves/", "v1",
                "src/main/java/javaroke/reccomendation/core/data/saves/graph.json");

        AdjacencyMatrixUtils
                .printAdjacencyMatrix(graphHashMapTypeController.graph.getAdjacencyMetrix());

    }
}
