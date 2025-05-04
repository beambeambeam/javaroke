package javaroke.recommendation.tests;

import javaroke.recommendation.core.algorithms.pathFinding.FloydWallshallForHashMapGraph;
import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javaroke.recommendation.core.saves.HashMapGraphIO;
import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixPrinter;
import javaroke.recommendation.core.utils.tranformers.WeightTransformerForHashmapGraph;

public class Graph {
    public static void test() {
        HashMapGraph graph = new HashMapGraph();
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

            HashMapGraphIO.saveGraphHashMap(graph, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AdjacencyMatrixPrinter.printAdjacencyMatrix(graph.getAdjacencyMatrix());
        WeightTransformerForHashmapGraph.invertWeights(graph);
        AdjacencyMatrixPrinter.printAdjacencyMatrix(graph.getAdjacencyMatrix());
        WeightTransformerForHashmapGraph.applyBiasToFloor(graph, 10);
        AdjacencyMatrixPrinter.printAdjacencyMatrix(graph.getAdjacencyMatrix());
        // WeightTranformerForHashmap.applyAdditiveTransformToWeights(graph, 3);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        WeightTransformerForHashmapGraph.applyExponentialTransformToWeights(graph, 2);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.normalizeWeightsToRange01(graph);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        // WeightTranformerForHashmap.applyMultiplicativeTransformToWeights(graph, 0.5);
        // AdjacencyMatrixUtils.printAdjacencyMatrix(graph.getAdjacencyMetrix());
        FloydWallshallForHashMapGraph.floydWarshall(graph);
        AdjacencyMatrixPrinter.printAdjacencyMatrix(graph.getAdjacencyMatrix());
        AdjacencyMatrixPrinter.printPreviosVertexMatrix(graph.getPreviousVertexMatrix());
        AdjacencyMatrixPrinter
                .printPath(FloydWallshallForHashMapGraph.reconstructPath(graph, "A", "D"));
        AdjacencyMatrixPrinter
                .printPath(FloydWallshallForHashMapGraph.reconstructPath(graph, "E", "C"));

        ;
        WeightTransformerForHashmapGraph.invertWeights(graph);
        WeightTransformerForHashmapGraph.applyBiasToFloor(graph, 10);
        WeightTransformerForHashmapGraph.applyAdditiveTransformToWeights(graph, 3);
        WeightTransformerForHashmapGraph.applyExponentialTransformToWeights(graph, 2);
        WeightTransformerForHashmapGraph.normalizeWeightsToRange01(graph);
        WeightTransformerForHashmapGraph.applyMultiplicativeTransformToWeights(graph, 0.5);
        FloydWallshallForHashMapGraph.floydWarshall(graph);
        AdjacencyMatrixPrinter.printAdjacencyMatrix(graph.getAdjacencyMatrix());
        AdjacencyMatrixPrinter.printPreviosVertexMatrix(graph.getPreviousVertexMatrix());

    }

}
