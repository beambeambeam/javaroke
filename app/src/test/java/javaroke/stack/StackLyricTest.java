package javaroke.stack;

import org.junit.Test;
import static org.junit.Assert.*;

public class StackLyricTest {
  @Test
  public void testStackLyricPush() {
    StackLyric stackLyric = new StackLyric();

    stackLyric.push(0, "Test 1");

    assertEquals("push failed at first in StackLyric", 0, (int) stackLyric.peek().getTime());
    assertEquals("push failed at first in StackLyric", "Test 1", stackLyric.peek().getline());

    stackLyric.push(5, "Test 2");

    assertEquals("push failed at 2nd++ in StackLyric", 5, (int) stackLyric.peek().getTime());
    assertEquals("push failed at 2nd++ in StackLyric", "Test 2", stackLyric.peek().getline());
  }
}
