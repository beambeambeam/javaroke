package javaroke.reccomendation.controllers;

import javaroke.reccomendation.core.models.graphs.GraphHashMap;
import javaroke.reccomendation.core.utils.saves.GraphHashMapIO;
import javaroke.reccomendation.core.version.floydVersion.ReccomendationVersion;
import javaroke.reccomendation.core.version.floydVersion.VersionFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import javafx.util.Pair;

public class GraphHashMapTypeController {
    GraphHashMap graph;
    ReccomendationVersion<GraphHashMap> version;
    String savePath;

    public GraphHashMapTypeController(String savePath, String version) {
        graph = new GraphHashMap();
        this.savePath = savePath;
        this.version = VersionFactory.getVersion(version);
    }

    public GraphHashMapTypeController(String savePath, String version, String loadpath) {
        this.savePath = savePath;
        try {
            graph = GraphHashMapIO.loadGraph(loadpath);

            if (!Files.exists(Paths.get(savePath + "RealGraph.json"))) {
                GraphHashMapIO.saveGraphHashMap(graph, savePath + "RealGraph.json");
            }

        } catch (Exception e) {
            System.out.println("Error loading graph: " + e.getMessage());
            graph = new GraphHashMap();
        }

        this.version = VersionFactory.getVersion(version);
    }

    public void updateGraph(Queue<Pair<String, String>> queue) {
        try {
            graph = GraphHashMapIO.loadGraph(savePath + "RealGraph.json");
            while (!queue.isEmpty()) {
                Pair<String, String> pair = queue.poll();
                graph.addEdge(pair.getKey(), pair.getValue(), 1.0);
            }
            GraphHashMapIO.saveGraphHashMap(graph, savePath + "RealGraph.json");
        } catch (Exception e) {
            System.out.println("Error processing graph: " + e.getMessage());
        }
    }

    public void processGraph() {
        try {
            System.out.println(savePath + "RealGraph.json");
            graph = GraphHashMapIO.loadGraph(savePath + "RealGraph.json");
            version.process(graph);
            GraphHashMapIO.saveGraphHashMap(graph, savePath + "ResultGraph.json");
        } catch (Exception e) {
            System.out.println("Error processing graph: " + e.getMessage());
        }
    }

    public void updateFloydGraph(Queue<Pair<String, String>> queue) {
        version.shortUpdate(graph, queue);
    }
}
