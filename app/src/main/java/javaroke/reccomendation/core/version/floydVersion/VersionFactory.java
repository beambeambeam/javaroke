package javaroke.reccomendation.core.version.floydVersion;

import javaroke.reccomendation.core.models.graphs.GraphHashMap;

public class VersionFactory {
    public static ReccomendationVersion<GraphHashMap> getVersion(String version) {
        return switch (version) {
            case "v1" -> new V1<GraphHashMap>();
            case "v2" -> new V2<GraphHashMap>();
            default -> throw new IllegalArgumentException("Unknown version: " + version);
        };
    }
}
