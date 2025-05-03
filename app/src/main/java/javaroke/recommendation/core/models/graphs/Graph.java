package javaroke.recommendation.core.models.graphs;

/**
 * Represents a generic interface for a graph structure.
 */
public interface Graph {

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex The name of the vertex to be added.
     */
    void addVertex(String vertex);

    /**
     * Adds an edge between two vertices in the graph with a specified weight.
     *
     * @param src The source vertex of the edge.
     * @param dest The destination vertex of the edge.
     * @param weight The weight of the edge.
     */
    void addEdge(String src, String dest, double weight);

    /**
     * Retrieves the weight of the edge between two vertices.
     *
     * @param src The source vertex of the edge.
     * @param dest The destination vertex of the edge.
     * @return The weight of the edge between the specified vertices.
     */
    double getWeight(String src, String dest);

    /**
     * Updates the weight of the edge between two vertices.
     *
     * @param src The source vertex of the edge.
     * @param dest The destination vertex of the edge.
     * @param value The new weight to be set for the edge.
     */
    void setWeight(String src, String dest, double value);

    /**
     * Removes a vertex from the graph along with all its associated edges.
     *
     * @param vertex The name of the vertex to be removed.
     */
    void removeVertex(String vertex);

    /**
     * Removes an edge between two vertices in the graph with a specified weight.
     *
     * @param src The source vertex of the edge.
     * @param dest The destination vertex of the edge.
     * @param weight The weight of the edge to be removed.
     */
    void removeEdge(String src, String dest, double weight);
}
