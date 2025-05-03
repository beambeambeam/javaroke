package javaroke.recommendation.core.version;

import javaroke.recommendation.core.models.graphs.GraphHashMap;
import javaroke.recommendation.core.version.implementations.V1;
import javaroke.recommendation.core.version.implementations.V2;

public class VersionFactory {
    public static RecommendationVersion<GraphHashMap> getVersion(String version) {
        return switch (version) {
            case "v1" -> new V1();
            case "v2" -> new V2();
            default -> throw new IllegalArgumentException("Unknown version: " + version);
        };
    }
}
