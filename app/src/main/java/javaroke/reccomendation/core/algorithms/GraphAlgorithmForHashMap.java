package javaroke.reccomendation.core.algorithms;

import javaroke.reccomendation.core.graphs.GraphHashMap;

public class GraphAlgorithmForHashMap {
    public static void floydWarshall(GraphHashMap graph) {
        for (String k : graph.allKeyList) {
            for (String i : graph.allKeyList) {
                for (String j : graph.allKeyList) {
                    double ik = graph.adjacencyList.get(i).getOrDefault(k, graph.floorWeight);
                    double kj = graph.adjacencyList.get(k).getOrDefault(j, graph.floorWeight);
                    double ij = graph.adjacencyList.get(i).getOrDefault(j, graph.floorWeight);

                    if (ik + kj < ij) {
                        graph.adjacencyList.get(i).put(j, ik + kj);
                    }
                }
            }
        }
    }
}
