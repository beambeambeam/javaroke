package javaroke.reccomendation.models.graphs;

import java.util.Map;
import javaroke.reccomendation.models.MatrixWithLabels;

public interface GraphInterface {
    void addVertex(String vertex);

    void addEdge(String src, String dst, double weight);

    double getWeight(String src, String dst);

    Map<String, Double> getNeighbors(String node);

    void setWeight(String src, String dst, double value);

    void removeVertex(String vertex);

    void removeEdge(String src, String dst, double weight);

    int getSize();

    MatrixWithLabels getAdjacencyMetrix();
}
