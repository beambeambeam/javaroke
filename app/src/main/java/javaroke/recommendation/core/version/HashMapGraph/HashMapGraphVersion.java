package javaroke.recommendation.core.version.HashMapGraph;

import java.util.List;
import java.util.Queue;
import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javaroke.recommendation.core.models.items.MyPair;

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

    public abstract void updateData(HashMapGraph graph, Queue<MyPair<String, String>> data);

    public abstract List<String> getRecommendationsList(HashMapGraph graph);

    public abstract List<String> getRecommendationsList(HashMapGraph graph, String src,
            String dest);

    public abstract String getVersionInfo(String id);
}
