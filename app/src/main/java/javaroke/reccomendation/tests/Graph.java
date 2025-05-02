package javaroke.reccomendation.tests;

import javaroke.reccomendation.core.algorithms.GraphAlgorithmForHashMap;
import javaroke.reccomendation.core.graphs.GraphHashMap;
import javaroke.reccomendation.core.utils.AdjacencyMatrixUtils;
import javaroke.reccomendation.core.utils.tranformers.WeightTranformerForHashmap;

public class Graph {
    public static void test() {
        GraphHashMap graph = new GraphHashMap();
        graph.addEdge("A", "B", 5.0);
        graph.addEdge("B", "C", 3.0);
        graph.addEdge("A", "C", 3.0);
        graph.addEdge("C", "B", 2.0);
        graph.addEdge("C", "D", 1.0);
        graph.addEdge("D", "A", 3.0);
        graph.addEdge("B", "A", 4.0);

        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.invertWeights(graph);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.applyBiasToFloor(graph, 5);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.applyAdditiveTransformToWeights(graph, 3);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.applyExponentialTransformToWeights(graph, 2);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.normalizeWeightsToRange01(graph);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.applyMultiplicativeTransformToWeights(graph, 0.5);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        GraphAlgorithmForHashMap.floydWarshall(graph);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        AdjacencyMatrixUtils.printPreviosVertexMatrix(graph.getPreviousVertexMetrix());
        AdjacencyMatrixUtils.printPath(
                GraphAlgorithmForHashMap.reconstructPathFromGraphHashMap(graph, "C", "A"));
    }

}
