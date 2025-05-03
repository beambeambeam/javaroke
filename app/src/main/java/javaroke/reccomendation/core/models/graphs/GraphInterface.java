package javaroke.reccomendation.core.models.graphs;

public interface GraphInterface {

    void addVertex(String vertex);

    void addEdge(String src, String dest, double weight);

    double getWeight(String src, String dest);

    void setWeight(String src, String dest, double value);

    void removeVertex(String vertex);

    void removeEdge(String src, String dest, double weight);
}
