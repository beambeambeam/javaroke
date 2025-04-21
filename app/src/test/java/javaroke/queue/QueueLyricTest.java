package javaroke.queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class QueueLyricTest {
  @Test
  public void testQueueLyricEnqueue() {
    QueueLyric queueLyric = new QueueLyric();

    queueLyric.enqueue(0, "Test 1");

    assertEquals("enqueue failed at first in QueueLyric", 0, (int) queueLyric.peek().getTime());
    assertEquals("enqueue failed at first in QueueLyric", "Test 1", queueLyric.peek().getline());

    queueLyric.enqueue(5, "Test 2");

    assertEquals("enqueue failed at 2nd++ in QueueLyric", 0, (int) queueLyric.peek().getTime());
    assertEquals("enqueue failed at 2nd++ in QueueLyric", "Test 1", queueLyric.peek().getline());
  }
}
