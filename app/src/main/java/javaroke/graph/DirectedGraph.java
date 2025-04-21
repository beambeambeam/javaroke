package javaroke.graph;

import javaroke.models.Edge;

/**
 * The DirectedGraph class extends GraphAbstract and implements a method to add directed edges with
 * weights between nodes.
 */
public class DirectedGraph extends GraphAbstract {
  /**
   * The DirectedGraph class extends GraphAbstract and implements a method to add directed edges
   * with weights between nodes.
   */
  @Override
  public void addEdge(String source, String destination, int weight) {
    addNode(source);
    addNode(destination);

    adjacencyList.get(source).addEdge(new Edge(destination, weight));
  }
}
