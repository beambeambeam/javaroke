package javaroke.stack;

import org.junit.Test;
import static org.junit.Assert.*;

public class StackAbstractTest {
  @Test
  public void testStackAbstractInitialize() {
    StackAbstract<String> stack = new StackAbstract<String>() {};
    assertNotNull("stack initialize failed", stack);
  }

  @Test
  public void testStackAbstractSize() {
    StackAbstract<String> stack = new StackAbstract<String>() {};

    assertEquals("blank stack size failed", 0, stack.size());

    stack.push("Test 1");
    stack.push("Test 1");
    stack.push("Test 1");
    stack.push("Test 1");

    assertEquals("push size failed", 4, stack.size());

    stack.pop();

    assertEquals("pop size failed", 3, stack.size());

    stack.pop();
    stack.pop();
    stack.pop();
    assertEquals("pop to blank size failed", 0, stack.size());
  }

  @Test
  public void testStackAbstractPeek() {
    StackAbstract<String> stack = new StackAbstract<String>() {};

    stack.push("Test 1");

    assertEquals("peek failed", "Test 1", stack.peek());
  }

  @Test
  public void testStackAbstractPush() {
    StackAbstract<String> stack = new StackAbstract<String>() {};

    stack.push("Test 1");
    assertEquals("push failed at first", "Test 1", stack.peek());

    stack.push("Test 2");
    assertEquals("push failed at 2nd++", "Test 2", stack.peek());
  }

  @Test
  public void testStackAbstractPop() {
    StackAbstract<String> stack = new StackAbstract<String>() {};

    stack.push("Test 1");
    stack.push("Test 2");
    stack.push("Test 3");
    stack.push("Test 4");

    assertEquals("peek before pop failed", "Test 4", stack.peek());

    assertEquals("pop failed at first", "Test 4", stack.pop());
    assertEquals("pop failed at second", "Test 3", stack.pop());

    assertEquals("peek after pop failed", "Test 2", stack.peek());
  }

  @Test
  public void testStackAbstractNull() {
    StackAbstract<String> stack = new StackAbstract<String>() {};

    assertNull("peek failed at blank stack", stack.peek());
    assertNull("pop failed at blank stack", stack.pop());

    stack.push("Test 1");
    stack.push(null); // push null should be ignored or handled

    assertEquals("push failed at null input", "Test 1", stack.peek());

    stack.pop(); // Should pop "Test 1"
    assertNull("pop failed at last node", stack.pop());
  }

  // @Test
  // public void testStackAbstractIterable() {
  // StackAbstract<String> stack = new StackAbstract<String>() {};

  // String[] inputs = {"Test 1", "Test 2", "Test 3", "Test 4"};
  // for (String value : inputs) {
  // stack.push(value);
  // }

  // assertEquals("Stack size should be 4", 4, stack.size());

  // // Since it's LIFO, iteration might go in reverse order if not handled specifically
  // String[] expectedValues = {"Test 4", "Test 3", "Test 2", "Test 1"};

  // int i = 0;
  // for (String data : stack) {
  // assertEquals("Element at position " + i + " should match expected value", expectedValues[i],
  // data);
  // i++;
  // }

  // assertEquals("Should have iterated through 4 elements", 4, i);
  // assertEquals("Stack size should remain 4 after iteration", 4, stack.size());
  // }
}
