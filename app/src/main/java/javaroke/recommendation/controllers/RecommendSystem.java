package javaroke.recommendation.controllers;

import java.util.List;
import java.util.Queue;
import javaroke.gui.search.Item;
import javaroke.recommendation.core.models.items.MyPair;
import javaroke.recommendation.core.utils.GraphReading.AdjacencyMatrixPrinter;
import javaroke.recommendation.core.utils.tranformers.SongIdTransformers;

public class RecommendSystem {
    private static HashMapGraphController graphController = null;

    public static void initialize() {
        graphController = new HashMapGraphController("v1");
    }

    public static void forceLoad(String loadFileName) {
        if (graphController == null)
            initialize();
        graphController.forceLoadGraph(loadFileName);
        graphController.process();
    }

    public static void process() {
        if (graphController == null)
            initialize();
        graphController.process();
    }

    public static void update(Queue<MyPair<String, String>> data) {
        if (graphController == null)
            initialize();
        graphController.updateData(data);
        graphController.process();
    }

    public static List<String> getRecommendationList() {
        if (graphController == null)
            initialize();
        return graphController.getRecommendationsList();
    }

    public static List<Item> getRecommendationItemList() {
        if (graphController == null)
            initialize();
        return SongIdTransformers.changeSongIdToItem(getRecommendationList());
    }

    public static void printAdjacencyMetrix() {
        if (graphController == null)
            initialize();
        AdjacencyMatrixPrinter
                .printAdjacencyMatrix(graphController.getGraph().getAdjacencyMatrix());
    }

    public static void printPrevoiusVertexMetrix() {
        if (graphController == null)
            initialize();
        AdjacencyMatrixPrinter
                .printPreviousVertexMatrix(graphController.getGraph().getPreviousVertexMatrix());
    }
}
