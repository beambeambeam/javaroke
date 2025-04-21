package javaroke.queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class QueueLyricTest {
  @Test
  public void testQueueLyricEnqueue() {
    QueueLyric queueLyric = new QueueLyric();

    queueLyric.enqueue("00:00", "Test 1");

    assertEquals("enqueue failed at first in QueueLyric", "00:00", queueLyric.peek().getTime());
    assertEquals("enqueue failed at first in QueueLyric", "Test 1", queueLyric.peek().getline());

    queueLyric.enqueue("00:05", "Test 2");

    assertEquals("enqueue failed at 2nd++ in QueueLyric", "00:00", queueLyric.peek().getTime());
    assertEquals("enqueue failed at 2nd++ in QueueLyric", "Test 1", queueLyric.peek().getline());
  }
}
