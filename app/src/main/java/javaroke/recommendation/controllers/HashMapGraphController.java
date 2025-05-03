package javaroke.recommendation.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaroke.recommendation.core.models.graphs.HashMapGraph;
import javaroke.recommendation.core.utils.saves.HashMapGraphIO;
import javaroke.recommendation.core.version.HashMapGraph.HashMapGraphV1;
import javaroke.recommendation.core.version.HashMapGraph.HashMapGraphVersion;

public class HashMapGraphController {
    private static final Logger LOGGER = Logger.getLogger(HashMapGraphController.class.getName());
    private HashMapGraph graph;
    private HashMapGraphVersion version;

    public HashMapGraphController(String version) {
        try {
            this.graph = new HashMapGraph();
            this.version = createVersion(version);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Initial failed: " + e.getMessage());
            throw e;
        }
    }

    public HashMapGraphController(String loadFileName, String version) {
        try {
            this.graph = loadGraph(loadFileName);
            this.version = createVersion(version);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Initial failed: " + e.getMessage());
            throw e;
        }
    }

    public void process() {
        version.process(graph);
    }

    private HashMapGraph loadGraph(String loadFileName) {
        if (loadFileName == null || loadFileName.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }

        Path path = Paths.get(loadFileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File does not exist: " + loadFileName);
        }

        try {
            LOGGER.log(Level.INFO, "Loading graph: {0}", loadFileName);
            return HashMapGraphIO.loadGraph(loadFileName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Loading graph failed: {0}", e.toString());
            return new HashMapGraph();
        }
    }


    private HashMapGraphVersion createVersion(String version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("Version cannot be null or empty");
        }

        LOGGER.log(Level.INFO, "Loading version: {0}", version);

        switch (version.toLowerCase()) {
            case "v1":
                return new HashMapGraphV1();
            // case "v2":
            // return new HashMapGraphV2();
            default:
                throw new IllegalArgumentException("Unsupported version: " + version);
        }
    }

}
