package javaroke.stack;

import org.junit.Test;
import static org.junit.Assert.*;

public class StackSongTest {
  @Test
  public void testStackSongPush() {
    StackSong stackSong = new StackSong();

    stackSong.push("Test-1", "Test 1", "Tester", 136);

    assertEquals("push failed at first in StackSong extend", "Test-1",
        stackSong.peek().getSongId());

    stackSong.push("Test-2", "Test 2", "Tester", 92);

    assertEquals("push failed at 2nd++ in StackSong extend", "Test-2",
        stackSong.peek().getSongId());
  }
}
