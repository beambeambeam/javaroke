package javaroke.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeTest {
  @Test
  public void testConstructorAndGetters() {
    Edge edge = new Edge("destination1", 10);
    assertEquals("destination1", edge.getDestination());
    assertEquals(10, edge.getWeight());
  }

  @Test
  public void testSetDestination() {
    Edge edge = new Edge("destination1", 10);
    edge.setDestination("destination2");
    assertEquals("destination2", edge.getDestination());
  }

  @Test
  public void testSetWeight() {
    Edge edge = new Edge("destination1", 10);
    edge.setWeight(20);
    assertEquals(20, edge.getWeight());
  }

  @Test
  public void testPlusWeight() {
    Edge edge = new Edge("destination1", 10);
    edge.plusWeight(5);
    assertEquals(15, edge.getWeight());
  }
}
