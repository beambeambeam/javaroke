package javaroke.reccomendation.tests;

import javaroke.reccomendation.models.graphs.TrippleHashMap;

public class Graph {
    public static void test() {
        TrippleHashMap graph = new TrippleHashMap();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", 1.0);
        System.out.println(graph.getWeight("A", "B")); // Should print 1.0
        System.out.println(graph.getNeighbors("A")); // Should print {B=1.0}
        graph.setWeight("A", "B", 2.0);
        System.out.println(graph.getWeight("A", "B")); // Should print 2.0
        graph.removeEdge("A", "B", 1.0);
        System.out.println(graph.getWeight("A", "B")); // Should print null
    }
}
