package javaroke.recommendation.core.version;

import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javaroke.recommendation.core.version.implementations.V1;
import javaroke.recommendation.core.version.implementations.V2;

public class VersionFactory {
    public static RecommendationVersion<HashMapGraph> getVersion(String version) {
        return switch (version) {
            case "v1" -> new V1();
            case "v2" -> new V2();
            default -> throw new IllegalArgumentException("Unknown version: " + version);
        };
    }
}
