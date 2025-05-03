package javaroke.recommendation.core.models.graphs;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class HashMapGraph implements Graph {
    // Use to keep track of the previous vertex in the shortest path algorithm
    // The previous list is a HashMap where previousVertex.get(src).get(dest)
    // mean the key of the previous vertex in the shortest path algorithm
    public HashMap<String, HashMap<String, String>> previousVertex;

    // Use to keep data about the graph

    // # Adjacency List:
    // The adjacency list is a HashMap where adjacencyList.get(src).get(dest)
    // mean the weight of the edge from src to dest (O(1) to get the weight)

    // # ArrayList:
    // The key list is an ArrayList where keyList.get(i)
    // mean the vertex name at index i (O(1) to get the vertex name)

    // Princitple:
    // ArrayList will read all data fast than Hashmap (O(n) to get all vertex name)
    // Cause ArrayList is using index, and it's so fast to call from i=1 to i=size
    // HashMap is using big memory space to store the data.
    // And to find all, It's require seach in every space.
    // The space size is 2^(log2(ceil(0.75*size)) with possible null on those space.
    // But ArrayList size is (size), no null space.
    // And we can use this ArrayList based to select data to calculate or do anything.
    // Such as select only top 100 data to calculate the shortest path.

    public HashMap<String, HashMap<String, Double>> adjacencyList;
    public ArrayList<String> keyList;
    public int size;

    // The maximum weight of the graph
    // The floor weight for no path between two vertices
    // reccomendation system still want to make all the vertices connected
    // But we don't keep those null path in HashMap, we keep it as a null
    // Then we call it, we will return the floor weight
    // Making the graph more efficient in those calculation and memory terms
    public double maximumWeight;
    public double floorWeight;

    public HashMapGraph() {
        this.previousVertex = new HashMap<>();
        this.adjacencyList = new HashMap<>();
        this.keyList = new ArrayList<>();
        this.size = 0;
        this.maximumWeight = 0.0;
        this.floorWeight = -1.0;
    }

    @Override
    public void addVertex(String vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            keyList.add(vertex);
            previousVertex.put(vertex, new HashMap<>());
            adjacencyList.put(vertex, new HashMap<>());
            adjacencyList.get(vertex).put(vertex, 0.0);
            size++;
        }
    }

    public void addEdge(String src, String dest) {
        addEdge(src, dest, 1.0);
    }

    @Override
    public void addEdge(String src, String dest, double weight) {
        addVertex(src);
        addVertex(dest);

        double newWeight = adjacencyList.get(src).getOrDefault(dest, 0.0) + weight;

        if (newWeight > maximumWeight) {
            maximumWeight = newWeight;
        }

        adjacencyList.get(src).put(dest, newWeight);
        adjacencyList.get(dest).put(src, newWeight);
    }

    @Override
    public double getWeight(String src, String dest) {
        return adjacencyList.get(src).getOrDefault(dest, floorWeight);
    }

    @Override
    public void setWeight(String src, String dest, double value) {
        if (value > maximumWeight) {
            maximumWeight = value;

        }
        adjacencyList.get(src).put(dest, value);
    }

    @Override
    public void removeVertex(String vertex) {
        if (adjacencyList.containsKey(vertex)) {
            adjacencyList.remove(vertex);
            keyList.remove(vertex);
            size--;
        }
    }

    @Override
    public void removeEdge(String src, String dest, double weight) {
        if (adjacencyList.containsKey(src) && adjacencyList.get(src).containsKey(dest)) {
            adjacencyList.get(src).remove(dest);
        }
    }

    @JsonIgnore
    public double[][] getAdjacencyMetrix() {
        double[][] adjacencyMatrix = new double[size][size];

        int i = 0, j = 0;
        for (String src : keyList) {
            for (String dest : keyList) {
                adjacencyMatrix[i][j] = adjacencyList.get(src).getOrDefault(dest, floorWeight);
                j++;
            }

            i++;
            j = 0;
        }

        return adjacencyMatrix;
    }

    @JsonIgnore
    public String[][] getPreviousVertexMetrix() {
        String[][] previousVertexMetrix = new String[size][size];

        int i = 0, j = 0;
        for (String src : keyList) {
            for (String dest : keyList) {
                previousVertexMetrix[i][j] = previousVertex.get(src).getOrDefault(dest, "null");
                j++;
            }

            i++;
            j = 0;
        }

        return previousVertexMetrix;
    }

    public HashMapGraph cloneGraph() {
        HashMapGraph cloned = new HashMapGraph();

        // Copy scalar fields
        cloned.size = this.size;
        cloned.maximumWeight = this.maximumWeight;
        cloned.floorWeight = this.floorWeight;

        // Deep copy keyList
        cloned.keyList = new ArrayList<>(this.keyList);

        // Deep copy adjacencyList
        for (Map.Entry<String, HashMap<String, Double>> entry : this.adjacencyList.entrySet()) {
            String src = entry.getKey();
            HashMap<String, Double> neighbors = entry.getValue();

            // Copy inner map
            HashMap<String, Double> copiedNeighbors = new HashMap<>();
            for (Map.Entry<String, Double> neighbor : neighbors.entrySet()) {
                copiedNeighbors.put(neighbor.getKey(), neighbor.getValue());
            }

            // Put into cloned graph
            cloned.adjacencyList.put(src, copiedNeighbors);
        }

        return cloned;
    }
}
