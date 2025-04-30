package javaroke.reccomendation.core.graphs;

import java.util.*;

public class GraphHashMap implements GraphInterface {

    public HashMap<String, HashMap<String, Double>> adjacencyList;
    public ArrayList<String> allKeyList;
    public int size;
    public boolean isPositive;
    public double maximumWeight;
    public double floorWeight;

    public GraphHashMap() {
        this.adjacencyList = new HashMap<>();
        this.allKeyList = new ArrayList<>();
        this.size = 0;
        this.isPositive = true;
        this.maximumWeight = 0.0;
        this.floorWeight = -1.0;
    }

    @Override
    public void addVertex(String vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            allKeyList.add(vertex);
            adjacencyList.put(vertex, new HashMap<>());
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

        if (!isPositive)
            weight = -weight;

        double newWeight = adjacencyList.get(src).getOrDefault(dest, 0.0) + weight;

        if (newWeight > maximumWeight) {
            maximumWeight = newWeight;
        }

        adjacencyList.get(src).put(dest, newWeight);
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
            allKeyList.remove(vertex);
            size--;
        }
    }

    @Override
    public void removeEdge(String src, String dest, double weight) {
        if (adjacencyList.containsKey(src) && adjacencyList.get(src).containsKey(dest)) {
            adjacencyList.get(src).remove(dest);
        }
    }

    public double[][] getAdjacencyMetrix() {
        double[][] adjacencyMatrix = new double[size][size];

        int i = 0, j = 0;
        for (String src : allKeyList) {
            for (String dest : allKeyList) {
                if (i == j)
                    adjacencyMatrix[i][j] = 0.0;
                else
                    adjacencyMatrix[i][j] = adjacencyList.get(src).getOrDefault(dest, floorWeight);
                j++;
            }

            i++;
            j = 0;
        }

        return adjacencyMatrix;
    }
}
