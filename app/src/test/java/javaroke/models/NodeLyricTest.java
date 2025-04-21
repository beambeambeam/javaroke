package javaroke.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class NodeLyricTest {

  @Test
  public void testConstructorAndGetters() {
    NodeLyric nodeLyric = new NodeLyric("01:23", "Hello World");
    assertEquals(83, nodeLyric.getTime());
    assertEquals("Hello World", nodeLyric.getline());
  }

  @Test
  public void testSetTime() {
    NodeLyric nodeLyric = new NodeLyric("01:23", "Hello World");
    nodeLyric.setTime("02:34");
    assertEquals(154, nodeLyric.getTime());
  }

  @Test
  public void testSetLine() {
    NodeLyric nodeLyric = new NodeLyric("01:23", "Hello World");
    nodeLyric.setLine("New Line");
    assertEquals("New Line", nodeLyric.getline());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTimeFormatInConstructor() {
    new NodeLyric("invalid_time", "Hello World");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTimeFormatInSetTime() {
    NodeLyric nodeLyric = new NodeLyric("01:23", "Hello World");
    nodeLyric.setTime("invalid_time");
  }
}
