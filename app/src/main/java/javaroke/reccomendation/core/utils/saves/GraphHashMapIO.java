package javaroke.reccomendation.core.utils.saves;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import javaroke.reccomendation.core.graphs.GraphHashMap;

public class GraphHashMapIO {
    public static void saveGraphHashMap(GraphHashMap graph, String filename) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filename), graph);
    }

    public static GraphHashMap loadGraph(String filename) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        GraphHashMap graph = mapper.readValue(new File(filename), GraphHashMap.class);

        return graph;
    }
}
