package javaroke.reccomendation.tests;

import java.nio.file.Paths;
import javaroke.reccomendation.core.algorithms.GraphAlgorithmForHashMap;
import javaroke.reccomendation.core.graphs.GraphHashMap;
import javaroke.reccomendation.core.utils.AdjacencyMatrixUtils;
import javaroke.reccomendation.core.utils.saves.GraphHashMapIO;
import javaroke.reccomendation.core.utils.tranformers.WeightTranformerForHashmap;

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

        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.invertWeights(graph);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.applyBiasToFloor(graph, 10);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.applyAdditiveTransformToWeights(graph, 3);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.applyExponentialTransformToWeights(graph, 2);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.normalizeWeightsToRange01(graph);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.applyMultiplicativeTransformToWeights(graph, 0.5);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        GraphAlgorithmForHashMap.floydWarshall(graph);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        AdjacencyMatrixUtils.printPreviosVertexMatrix(graph.getPreviousVertexMetrix());
        AdjacencyMatrixUtils.printPath(
                GraphAlgorithmForHashMap.reconstructPathFromGraphHashMap(graph, "E", "C"));

    }

}
