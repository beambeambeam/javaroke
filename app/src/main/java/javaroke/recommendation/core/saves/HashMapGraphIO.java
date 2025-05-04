package javaroke.recommendation.core.saves;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import javaroke.recommendation.core.models.graphs.HashMapGraph;

public class HashMapGraphIO {
    public static void saveGraphHashMap(HashMapGraph graph, String filename) throws Exception {
        if (!Files.exists(Paths.get(filename))) {
            System.out.println("File does not exist. Creating new file...");
            File file = new File(filename);
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
            file.createNewFile(); // Create the file
        }
        System.out.println("File already exists. Overwriting...");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filename), graph);
    }

    public static HashMapGraph loadGraph(String filename) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        HashMapGraph graph = mapper.readValue(new File(filename), HashMapGraph.class);

        return graph;
    }
}
