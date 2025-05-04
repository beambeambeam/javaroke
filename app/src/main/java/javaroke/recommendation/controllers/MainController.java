package javaroke.recommendation.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javaroke.gui.search.Item;
import javaroke.recommendation.core.models.items.MyPair;
import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixPrinter;
import javaroke.recommendation.core.utils.tranformers.SongIdTransformers;

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
        List<String> recommendList = graphController.getRecommendationsList();
        AdjacencyMatrixPrinter.printPopularVertex(recommendList);

        // Change songId to Item, But not found cause current song Id is 'A', 'B',...
        List<Item> recommendItemList = SongIdTransformers.changeSongIdToItem(recommendList);
        System.out.println(recommendItemList);

        // Force making data
        recommendList = new ArrayList<>();
        recommendList.add(new String("jai-sung-mah"));
        recommendList.add(new String("kloem"));
        recommendItemList = SongIdTransformers.changeSongIdToItem(recommendList);
        for (Item i : recommendItemList)
            System.out.println(String.format("%s, %s, %s", i.getId(), i.getTitle(), i.getArtist()));
        System.out.println();

        // Get log
        System.out.println(graphController.getMetricSummary());
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
