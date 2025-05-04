package javaroke.recommendation.tests;

import java.util.LinkedList;
import java.util.Queue;
import javaroke.recommendation.controllers.HashMapGraphController;
import javaroke.recommendation.core.models.items.MyPair;
import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixPrinter;

public class RecommendationTest {
        public static void main(String[] args) {
                MainControllerTest.test();
                // test2();
        }

        public static void test2() {
                // Initialize the graph controller with a specific version
                HashMapGraphController graphController =
                                new HashMapGraphController("graph.json", "v1");

                AdjacencyMatrixPrinter.printAdjacencyMatrix(
                                graphController.getGraph().getAdjacencyMatrix());

                // Updare teh graph
                Queue<MyPair<String, String>> updateQueue = new LinkedList<>();
                updateQueue.add(new MyPair<String, String>("A", "B"));
                updateQueue.add(new MyPair<String, String>("A", "B"));
                updateQueue.add(new MyPair<String, String>("A", "B"));
                updateQueue.add(new MyPair<String, String>("A", "B"));

                graphController.updateData(updateQueue);

                AdjacencyMatrixPrinter.printAdjacencyMatrix(
                                graphController.getGraph().getAdjacencyMatrix());
                // Process the graph
                graphController.process();

                // Check Matrix
                AdjacencyMatrixPrinter.printAdjacencyMatrix(
                                graphController.getGraph().getAdjacencyMatrix());
                AdjacencyMatrixPrinter.printPreviousVertexMatrix(
                                graphController.getGraph().getPreviousVertexMatrix());

                // Get adjacency matrix ArrayList<String> songId;
                AdjacencyMatrixPrinter.printPopularVertex(graphController.getRecommendationsList());
        }

        public static void test1() {

                // Initialize the graph controller with a specific version
                HashMapGraphController graphController =
                                new HashMapGraphController("graph.json", "v1");

                AdjacencyMatrixPrinter.printAdjacencyMatrix(
                                graphController.getGraph().getAdjacencyMatrix());

                // Process the graph
                graphController.process();

                // Check Matrix
                AdjacencyMatrixPrinter.printAdjacencyMatrix(
                                graphController.getGraph().getAdjacencyMatrix());
                AdjacencyMatrixPrinter.printPreviousVertexMatrix(
                                graphController.getGraph().getPreviousVertexMatrix());

                // Get adjacency matrix ArrayList<String> songId;
                AdjacencyMatrixPrinter.printPopularVertex(graphController.getRecommendationsList());
        }
}
