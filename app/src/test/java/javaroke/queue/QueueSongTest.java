package javaroke.queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class QueueSongTest {
  @Test
  public void testQueueSongEnqueue() {
    QueueSong queueSong = new QueueSong();

    queueSong.enqueue("Test-1", "Test 1", "Tester", "02:16");

    assertEquals("enqueue failed at first in QueueSong extend", "Test-1",
        queueSong.peek().getSongId());

    queueSong.enqueue("Test-2", "Test 2", "Tester", "01:32");

    assertEquals("enqueue failed at 2nd++ in QueueSong extend", "Test-1",
        queueSong.peek().getSongId());
  }
}
