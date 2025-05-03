package javaroke.recommendation.core.version.floydVersion;

import javaroke.recommendation.core.models.graphs.GraphInterface;

public class VersionFactory {
    public static RecommendationVersion<GraphInterface> getVersion(String version) {
        return switch (version) {
            case "v1" -> new V1();
            case "v2" -> new V2();
            default -> throw new IllegalArgumentException("Unknown version: " + version);
        };
    }
}
