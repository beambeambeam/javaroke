package javaroke.recommendation.core.version.HashMapGraph;

import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javafx.util.Pair;
import java.util.Queue;

public class HashMapGraphV1 extends HashMapGraphVersion {
    @Override
    public void process(HashMapGraph graph) {
        // Implement the processing logic for version 1
        System.out.println("Processing graph in version 1");
    }

    @Override
    public void updateData(HashMapGraph graph, Queue<Pair<String, String>> data) {
        // Implement the data update logic for version 1
        System.out.println("Updating data in version 1");
    }

    @Override
    public void getRecommendationsList() {
        // Implement the logic to get recommendations list for version 1
        System.out.println("Getting recommendations list in version 1");
    }

    @Override
    public void getRecommendationsList(String src, String dest) {
        // Implement the logic to get recommendations list for version 1 with source and destination
        System.out.println(
                "Getting recommendations list from " + src + " to " + dest + " in version 1");
    }

    @Override
    public String getVersionInfo(String id) {
        // Call from JSON
        return "";
    }

}
