package javaroke.graph;

import javaroke.models.Edge;
import javaroke.models.NodeGraph;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectedGraphTest {

  @Test
  public void testAddEdge() {
    DirectedGraph graph = new DirectedGraph();

    // Add edges
    graph.addEdge("A", "B", 5);
    graph.addEdge("A", "C", 3);
    graph.addEdge("B", "C", 2);

    // Verify adjacency list
    NodeGraph nodeA = graph.adjacencyList.get("A");
    NodeGraph nodeB = graph.adjacencyList.get("B");
    NodeGraph nodeC = graph.adjacencyList.get("C");

    assertNotNull(nodeA);
    assertNotNull(nodeB);
    assertNotNull(nodeC);

    assertEquals(2, nodeA.getEdges().size());
    assertEquals(1, nodeB.getEdges().size());
    assertEquals(0, nodeC.getEdges().size());

    // Verify edges
    Edge edgeAB = nodeA.getEdges().stream().filter(edge -> edge.getDestination().equals("B"))
        .findFirst().orElse(null);
    assertNotNull(edgeAB);
    assertEquals(5, edgeAB.getWeight());

    Edge edgeAC = nodeA.getEdges().stream().filter(edge -> edge.getDestination().equals("C"))
        .findFirst().orElse(null);
    assertNotNull(edgeAC);
    assertEquals(3, edgeAC.getWeight());

    Edge edgeBC = nodeB.getEdges().stream().filter(edge -> edge.getDestination().equals("C"))
        .findFirst().orElse(null);
    assertNotNull(edgeBC);
    assertEquals(2, edgeBC.getWeight());
  }

  @Test
  public void testShowAdjacencyMatrix() {
    DirectedGraph graph = new DirectedGraph();

    // Add edges
    graph.addEdge("A", "B", 5);
    graph.addEdge("A", "C", 3);
    graph.addEdge("B", "C", 2);

    // Capture adjacency matrix output
    graph.showAdjacencyMatric();
    // Note: You can redirect System.out to capture the printed matrix if needed for assertions.
  }
}
