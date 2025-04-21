package javaroke.graph;

import org.junit.Test;
import static org.junit.Assert.*;
import javaroke.models.Edge;

public class GraphAbstractTest {

  // A concrete implementation of GraphAbstract for testing
  private static class TestGraph extends GraphAbstract {
    @Override
    public void addEdge(String source, String destination, int weight) {
      addNode(source);
      addNode(destination);
      adjacencyList.get(source).addEdge(new Edge(destination, weight));
    }
  }

  @Test
  public void testAddNode() {
    GraphAbstract graph = new TestGraph();
    graph.addNode("A");
    graph.addNode("B");

    assertEquals(2, graph.numVertices);
    assertTrue(graph.adjacencyList.containsKey("A"));
    assertTrue(graph.adjacencyList.containsKey("B"));
    assertTrue(graph.idList.contains("A"));
    assertTrue(graph.idList.contains("B"));
  }

  @Test
  public void testAddEdge() {
    GraphAbstract graph = new TestGraph();
    graph.addEdge("A", "B", 5);

    assertEquals(2, graph.numVertices);
    assertTrue(graph.adjacencyList.get("A").getEdges().stream()
        .anyMatch(edge -> edge.getDestination().equals("B") && edge.getWeight() == 5));
  }

  @Test
  public void testShowAdjacencyMatrix() {
    GraphAbstract graph = new TestGraph();
    graph.addEdge("A", "B", 5);
    graph.addEdge("B", "C", 3);

    // Redirect output to verify adjacency matrix
    graph.showAdjacencyMatric();
  }
}
