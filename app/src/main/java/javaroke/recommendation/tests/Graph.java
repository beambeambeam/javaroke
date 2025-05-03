package javaroke.recommendation.tests;

import javaroke.recommendation.core.algorithms.pathFinding.floydWallshall.FloydWallshallForGraphHashMap;
import javaroke.recommendation.core.models.graphs.GraphHashMap;
import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixRead;
import javaroke.recommendation.core.utils.saves.GraphHashMapIO;
import javaroke.recommendation.core.utils.tranformers.WeightTransformerForHashmap;

public class Graph {
    public static void test() {
        GraphHashMap graph = new GraphHashMap();
        graph.addEdge("A", "B", 2.0);
        graph.addEdge("B", "C", 2.0);
        graph.addEdge("C", "D", 2.0);
        graph.addEdge("D", "E", 2.0);
        graph.addEdge("E", "A", 2.0);
        graph.addEdge("A", "X", 3.0);
        graph.addEdge("X", "C", 3.0);

        try {
            // String path = Paths.get("..", "data", "saves", "graph.json").toString();
            String path = "src/main/java/javaroke/reccomendation/core/data/saves/graph.json";

            GraphHashMapIO.saveGraphHashMap(graph, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AdjacencyMatrixRead.printAdjacencyMetrix(graph.getAdjacencyMetrix());
        WeightTransformerForHashmap.invertWeights(graph);
        AdjacencyMatrixRead.printAdjacencyMetrix(graph.getAdjacencyMetrix());
        WeightTransformerForHashmap.applyBiasToFloor(graph, 10);
        AdjacencyMatrixRead.printAdjacencyMetrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.applyAdditiveTransformToWeights(graph, 3);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTransformerForHashmap.applyExponentialTransformToWeights(graph, 2);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.normalizeWeightsToRange01(graph);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.applyMultiplicativeTransformToWeights(graph, 0.5);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        FloydWallshallForGraphHashMap.floydWarshall(graph);
        AdjacencyMatrixRead.printAdjacencyMetrix(graph.getAdjacencyMetrix());
        AdjacencyMatrixRead.printPreviosVertexMetrix(graph.getPreviousVertexMetrix());
        AdjacencyMatrixRead.printPath(
                FloydWallshallForGraphHashMap.reconstructPathFromGraphHashMap(graph, "A", "D"));
        AdjacencyMatrixRead.printPath(
                FloydWallshallForGraphHashMap.reconstructPathFromGraphHashMap(graph, "E", "C"));

        ;
        WeightTransformerForHashmap.invertWeights(graph);
        WeightTransformerForHashmap.applyBiasToFloor(graph, 10);
        WeightTransformerForHashmap.applyAdditiveTransformToWeights(graph, 3);
        WeightTransformerForHashmap.applyExponentialTransformToWeights(graph, 2);
        WeightTransformerForHashmap.normalizeWeightsToRange01(graph);
        WeightTransformerForHashmap.applyMultiplicativeTransformToWeights(graph, 0.5);
        FloydWallshallForGraphHashMap.floydWarshall(graph);
        AdjacencyMatrixRead.printAdjacencyMetrix(graph.getAdjacencyMetrix());
        AdjacencyMatrixRead.printPreviosVertexMetrix(graph.getPreviousVertexMetrix());

    }

}
