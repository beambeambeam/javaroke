package javaroke.recommendation.tests;

import java.util.List;
import java.util.Queue;
import javaroke.gui.search.Item;
import javaroke.recommendation.controllers.HashMapGraphController;
import javaroke.recommendation.core.models.items.MyPair;
import javaroke.recommendation.core.utils.tranformers.SongIdTransformers;

public class ControllerTest {
    private static HashMapGraphController graphController = null;

    public static void initialize() {
        graphController = new HashMapGraphController("graph.json", "v1");
    }

    public static void process() {
        if (graphController == null)
            initialize();
        process();
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
}
