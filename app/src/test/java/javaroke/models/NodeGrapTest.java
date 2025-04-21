package javaroke.models;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class NodeGrapTest {

  @Test
  public void testConstructor() {
    NodeGraph node = new NodeGraph("song123");
    assertEquals("song123", node.getSongID());
    assertTrue(node.getEdges().isEmpty());
    assertFalse(node.isVisited());
    assertEquals(Integer.MAX_VALUE, node.getDistance());
  }

  @Test
  public void testSetSongID() {
    NodeGraph node = new NodeGraph("song123");
    node.setSongID("song456");
    assertEquals("song456", node.getSongID());
  }

  @Test
  public void testSetEdges() {
    NodeGraph node = new NodeGraph("song123");
    List<Edge> edges = new ArrayList<>();
    edges.add(new Edge("song456", 10));
    node.setEdges(edges);
    assertEquals(1, node.getEdges().size());
    assertEquals("song456", node.getEdges().get(0).getDestination());
  }

  @Test
  public void testSetVisited() {
    NodeGraph node = new NodeGraph("song123");
    node.setVisited(true);
    assertTrue(node.isVisited());
  }

  @Test
  public void testSetDistance() {
    NodeGraph node = new NodeGraph("song123");
    node.setDistance(50);
    assertEquals(50, node.getDistance());
  }

  @Test
  public void testPlusDistance() {
    NodeGraph node = new NodeGraph("song123");
    node.setDistance(50);
    node.plusDistance(20);
    assertEquals(70, node.getDistance());
  }

  @Test
  public void testAddEdge_NewEdge() {
    NodeGraph node = new NodeGraph("song123");
    Edge edge = new Edge("song456", 10);
    node.addEdge(edge);
    assertEquals(1, node.getEdges().size());
    assertEquals("song456", node.getEdges().get(0).getDestination());
    assertEquals(10, node.getEdges().get(0).getWeight());
  }

  @Test
  public void testAddEdge_ExistingEdge() {
    NodeGraph node = new NodeGraph("song123");
    Edge edge1 = new Edge("song456", 10);
    Edge edge2 = new Edge("song456", 5);
    node.addEdge(edge1);
    node.addEdge(edge2);
    assertEquals(1, node.getEdges().size());
    assertEquals("song456", node.getEdges().get(0).getDestination());
    assertEquals(15, node.getEdges().get(0).getWeight());
  }
}
