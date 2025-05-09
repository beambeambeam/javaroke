package javaroke.recommendation.core.version.HashMapGraph;

import java.util.List;
import java.util.Queue;
import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javaroke.recommendation.core.models.items.MyPair;
import javaroke.recommendation.core.version.VersionConfigLoader;

public abstract class HashMapGraphVersion {
    protected VersionConfigLoader versionConfigLoader;

    public HashMapGraphVersion(String configPath) {
        versionConfigLoader = new VersionConfigLoader(configPath);
    }

    public String getVersion() {
        return getVersionInfo("version");
    }

    public String getVersionName() {
        return getVersionInfo("version-name");
    }

    public String getDescription() {
        return getVersionInfo("description");
    }

    public String getVersionInfo(String id) {
        return versionConfigLoader.get(id);
    };

    public abstract void process(HashMapGraph graph);

    public abstract void updateData(HashMapGraph graph, Queue<MyPair<String, String>> data);

    public abstract List<String> getRecommendationsList(HashMapGraph graph);

    public abstract List<String> getRecommendationsList(HashMapGraph graph, String src);

    public abstract List<String> getRecommendationsListPath(HashMapGraph graph, String src,
            String dest);
}
