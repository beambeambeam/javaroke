package javaroke.queue;

import org.junit.Test;
import static org.junit.Assert.*;


public class QueueAbstractTest {
  @Test
  public void testQueueAbstractInitialize() {
    QueueAbstract<String> queue = new QueueAbstract<String>() {};
    assertNotNull("queue initialize failed", queue);
  }

  @Test
  public void testQueueAbstractSize() {
    QueueAbstract<String> queue = new QueueAbstract<String>() {};

    assertEquals("blank queue size failed", 0, queue.size());

    queue.enqueue("Test 1");
    queue.enqueue("Test 1");
    queue.enqueue("Test 1");
    queue.enqueue("Test 1");

    assertEquals("enqueue size failed", 4, queue.size());

    queue.dequeue();

    assertEquals("dequeue size failed", 3, queue.size());

    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    assertEquals("dequeue to blank size failed", 0, queue.size());
  }

  @Test
  public void testQueueAbstractPeek() {
    QueueAbstract<String> queue = new QueueAbstract<String>() {};

    queue.enqueue("Test 1");

    assertEquals("peek failed", "Test 1", queue.peek());
  }

  @Test
  public void testQueueAbstractEnqueue() {
    QueueAbstract<String> queue = new QueueAbstract<String>() {};

    queue.enqueue("Test 1");

    assertEquals("enqueue failed at first", "Test 1", queue.peek());

    queue.enqueue("Test 2");

    assertEquals("enqueue failed at 2nd++", "Test 1", queue.peek());
  }

  @Test
  public void testQueueAbstractEnqueueAtFront() {
    QueueAbstract<String> queue = new QueueAbstract<String>() {};

    queue.enqueueAtFront("Test 1");

    assertEquals("enqueueAtFront failed at first", "Test 1", queue.peek());

    queue.enqueueAtFront("Test 2");

    assertEquals("enqueueAtFront failed at 2nd++", "Test 2", queue.peek());
  }

  @Test
  public void testQueueAbstractDequeue() {
    QueueAbstract<String> queue = new QueueAbstract<String>() {};

    queue.enqueue("Test 1");
    queue.enqueue("Test 2");
    queue.enqueue("Test 3");
    queue.enqueue("Test 4");

    assertEquals("enqueue failed at first", "Test 1", queue.peek());

    assertEquals("dequeue failed at first", "Test 1", queue.dequeue());
    assertEquals("dequeue failed at 2nd", "Test 2", queue.dequeue());

    assertEquals("peek failed at 3rd", "Test 3", queue.peek());
  }

  @Test
  public void testQueueAbstractNull() {
    QueueAbstract<String> queue = new QueueAbstract<String>() {};

    assertNull("peek failed at blank queue", queue.peek());

    assertNull("dequeue failed at blank queue", queue.dequeue());

    queue.enqueue("Test 1");
    queue.enqueue(null);

    assertEquals("enqueue failed at null input", "Test 1", queue.peek());

    queue.enqueueAtFront(null);

    assertEquals("enqueueAtFront failed at null input", "Test 1", queue.peek());

    queue.dequeue();

    assertNull("dequeue failed at last node", queue.dequeue());
  }

  @Test
  public void testQueueAbstractIterable() {
    // Create an anonymous concrete implementation of QueueAbstract
    QueueAbstract<String> queue = new QueueAbstract<String>() {};

    // Test data
    String[] expectedValues = {"Test 1", "Test 2", "Test 3", "Test 4"};

    // Fill the queue
    for (String value : expectedValues) {
      queue.enqueue(value);
    }

    // Test size before iteration
    assertEquals("Queue size should be 4", 4, queue.size());

    // Test iteration
    int i = 0;
    for (String data : queue) {
      assertEquals("Element at position " + i + " should match expected value", expectedValues[i],
          data);
      i++;
    }

    // Verify we iterated through all elements
    assertEquals("Should have iterated through 4 elements", 4, i);

    // Verify queue is unchanged after iteration (still has all elements)
    assertEquals("Queue size should remain 4 after iteration", 4, queue.size());
  }
}
