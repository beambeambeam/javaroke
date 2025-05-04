package javaroke.recommendation.controllers;

import java.util.LinkedList;
import java.util.Queue;
import javaroke.recommendation.core.models.items.MyPair;
import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixPrinter;

public class MainController {
    public static void main(String[] args) {
        // Initialize the graph controller with a specific version
        HashMapGraphController graphController = new HashMapGraphController("graph.json", "v1");


        // Process the graph
        graphController.process();

        // Get adjacency matrix ArrayList<String> songId;
        AdjacencyMatrixPrinter.printPopularVertex(graphController.getRecommendationsList());


        // Update weight
        graphController.updateData(getExampleQueue());
        graphController.process();

        // Get adjacency matrix ArrayList<String> songId;
        AdjacencyMatrixPrinter.printPopularVertex(graphController.getRecommendationsList());


        // Get log
        System.out.println(graphController.getMetricSummary());;
    }

    public static Queue<MyPair<String, String>> getExampleQueue() {
        Queue<MyPair<String, String>> updateQueue = new LinkedList<>();
        updateQueue.add(new MyPair<String, String>("A", "B"));
        updateQueue.add(new MyPair<String, String>("A", "B"));
        updateQueue.add(new MyPair<String, String>("A", "B"));
        updateQueue.add(new MyPair<String, String>("A", "B"));

        return updateQueue;
    }
}
