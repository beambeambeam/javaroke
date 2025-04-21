package javaroke.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {
  @Test
  public void testNodeInitialization() {
    Node<String> node = new Node<>("TestData");
    assertEquals("TestData", node.getData());
    assertNull(node.getNext());
  }

  @Test
  public void testSetData() {
    Node<String> node = new Node<>("InitialData");
    node.setData("UpdatedData");
    assertEquals("UpdatedData", node.getData());
  }

  @Test
  public void testSetNext() {
    Node<String> node1 = new Node<>("Node1");
    Node<String> node2 = new Node<>("Node2");
    node1.setNext(node2);
    assertEquals(node2, node1.getNext());
  }

  @Test
  public void testChainedNodes() {
    Node<String> node1 = new Node<>("Node1");
    Node<String> node2 = new Node<>("Node2");
    Node<String> node3 = new Node<>("Node3");

    node1.setNext(node2);
    node2.setNext(node3);

    assertEquals(node2, node1.getNext());
    assertEquals(node3, node2.getNext());
    assertNull(node3.getNext());
  }
}
