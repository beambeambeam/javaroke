package javaroke.recommendation.core.version.HashMapGraph;

import java.util.Queue;
import javafx.util.Pair;
import javaroke.recommendation.core.models.graphs.HashMapGraph;

public abstract class HashMapGraphVersion {
    public String getVersion() {
        return getVersionInfo("version");
    }

    public String getVersionName() {
        return getVersionInfo("versionName");
    }

    public String getDescription() {
        return getVersionInfo("description");
    }

    public abstract void process(HashMapGraph graph);

    public abstract void updateData(HashMapGraph graph, Queue<Pair<String, String>> data);

    public abstract void getRecommendationsList();

    public abstract void getRecommendationsList(String src, String dest);

    public abstract String getVersionInfo(String id);
}
