package javaroke.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class NodeLyricTest {

  @Test
  public void testConstructorAndGetters() {
    NodeLyric nodeLyric = new NodeLyric(83, "Hello World");
    assertEquals(83, (int) nodeLyric.getTime());
    assertEquals("Hello World", nodeLyric.getline());
  }

  @Test
  public void testSetTime() {
    NodeLyric nodeLyric = new NodeLyric(83, "Hello World");
    nodeLyric.setTime(154);
    assertEquals(154, (int) nodeLyric.getTime());
  }

  @Test
  public void testSetLine() {
    NodeLyric nodeLyric = new NodeLyric(83, "Hello World");
    nodeLyric.setLine("New Line");
    assertEquals("New Line", nodeLyric.getline());
  }
}
