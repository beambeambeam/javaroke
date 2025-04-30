package javaroke.reccomendation.tests;

import javaroke.reccomendation.core.graphs.GraphHashMap;
import javaroke.reccomendation.core.utils.AdjacencyMatrixUtils;
import javaroke.reccomendation.core.utils.weight.WeightTranformerForHashmap;

public class Graph {
    public static void test() {
        GraphHashMap graph = new GraphHashMap();
        graph.addEdge("A", "B", 5.0);
        graph.addEdge("B", "C", 3.0);
        graph.addEdge("A", "C", 3.0);
        graph.addEdge("C", "B", 2.0);
        graph.addEdge("C", "D", 1.0);

        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.flipWeight(graph);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.addBias2Floor(graph, 10);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.additive2Weight(graph, 3);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.exponential2Weight(graph, 2);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.normalize0to1(graph);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTranformerForHashmap.normalizeScaleWeight(graph, 0.5);
        AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
    }

}
