package javaroke.reccomendation.models.graphs;

import java.util.*;
import javaroke.reccomendation.models.MatrixWithLabels;

public class TrippleHashMap implements GraphInterface {

    private HashMap<String, HashMap<String, Double>> adjacencyList;

    private ArrayList<String> allKeyList;

    private int size;

    public TrippleHashMap() {
        this.adjacencyList = new HashMap<>();
        this.allKeyList = new ArrayList<>();
        this.size = 0;
    }

    @Override
    public void addVertex(String vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            allKeyList.add(vertex);
            adjacencyList.put(vertex, new HashMap<>());
            size++;
        }
    }

    @Override
    public void addEdge(String src, String dst, double weight) {
        addVertex(src);
        addVertex(dst);

        double newWeight = adjacencyList.get(src).getOrDefault(dst, 0.0) + weight;

        adjacencyList.get(src).put(dst, newWeight);
    }

    @Override
    public double getWeight(String src, String dst) {
        return adjacencyList.get(src).getOrDefault(dst, 0.0);
    }

    @Override
    public Map<String, Double> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, null);
    }

    @Override
    public void setWeight(String src, String dst, double value) {
        adjacencyList.get(src).put(dst, value);
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
    public void removeEdge(String from, String to, double weight) {
        if (adjacencyList.containsKey(from) && adjacencyList.get(from).containsKey(to)) {
            adjacencyList.get(from).remove(to);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public MatrixWithLabels getAdjacencyMetrix() {
        ArrayList<ArrayList<Double>> adjacencyMatrix = new ArrayList<>();

        for (String src : allKeyList) {
            ArrayList<Double> row = new ArrayList<>();
            for (String dst : allKeyList) {
                row.add(adjacencyList.get(src).getOrDefault(dst, 0.0));
            }
            adjacencyMatrix.add(row);
        }

        return new MatrixWithLabels(adjacencyMatrix, allKeyList, size);
    }
}
