package javaroke.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javaroke.models.Node;

/**
 * This class is an abstract implementation of a queue data structure in Java. It provides basic
 * queue operations such as enqueue, dequeue, and peek, as well as utility methods like isEmpty and
 * size.
 *
 * @param <T> The type of elements held in this queue.
 */
public abstract class QueueAbstract<T> implements Iterable<T> {
  private Node<T> front, rear;

  private int size;

  public QueueAbstract() {
    this.front = null;
    this.rear = null;
    this.size = 0;
  }

  /**
   * The `isEmpty` function in Java returns true if the size is equal to 0.
   *
   * @return The method `isEmpty()` returns a boolean value, which indicates whether the size is
   *         equal to 0.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * The `size()` function in Java returns the size of a data structure.
   *
   * @return The `size` variable is being returned.
   */
  public int size() {
    return size;
  }

  /**
   * The `enqueue` function adds a new node with the given data to the end of a queue, increasing
   * the size of the queue by 1.
   *
   * @param data The `data` parameter in the `enqueue` method represents the element that you want
   *        to add to the queue. It is of type `T`, which means it can be any data type as specified
   *        when the class or method is defined. In this method, a new node containing this data is
   *        created
   */
  public void enqueue(T data) {
    // If input data is null, exist this enqueue process
    if (data == null) {
      return;
    }

    // Create newNode as class T,and call Node() self setup function.
    Node<T> newNode = new Node<T>(data);

    // Main operation
    if (isEmpty()) {
      // Front = rear point at new node
      front = rear = newNode;
    } else {
      // Connect rear.next to newNode, making newNode be lastest rear
      rear.setNext(newNode);

      // Move rear to newNode that live in the last rear of this liked list
      rear = newNode;
    }

    // If endqueue function work correctly, plus size of queue by 1
    size++;
  }

  /**
   * The `enqueueAtFront` method adds an element to the front of a queue by temporarily storing the
   * existing elements in a separate queue.
   *
   * @param data The `enqueueAtFront` method you provided is used to add an element at the front of
   *        the queue. The `data` parameter represents the element that you want to add to the front
   *        of the queue. If the `data` parameter is `null`, the method will exit without performing
   *        any enqueue
   */
  public void enqueueAtFront(T data) {
    // If input data is null, exist this enqueue process
    if (data == null) {
      return;
    }

    // Main operation
    if (isEmpty()) {
      // Direct enqueue, cause it already has nothing inside
      enqueue(data);
    } else {
      // Create new queue for tempo dump data from current queue
      // Due to queue is abstract class now, have to use {} for make it be concrete
      QueueAbstract<T> tempoQueue = new QueueAbstract<T>() {};

      // Dump all data from current to tempoQueue
      // Making current queue empty
      while (!isEmpty()) {
        tempoQueue.enqueue(dequeue());
      }

      // Enqueue data first
      enqueue(data);

      // Dump all old data from tempoQueue to current again
      while (!tempoQueue.isEmpty()) {
        enqueue(tempoQueue.dequeue());
      }
    }
  }

  // enqueueAtFront Option 2 with O(1).
  // Still be Queue main process, but not FIFO at all
  // public void enqueueAtFront(T data) {
  // // Create newNode as class T,and call Node() self setup function.
  // Node<T> newNode = new Node<T>(data);

  // // Main operation
  // if (isEmpty()) {
  // // Front = rear point at new node
  // front = rear = newNode;
  // } else {
  // // Connect newNode.next to front, making newNode be lastest front
  // newNode.next = front;

  // // Move front to newNode that live in the lastest front of this liked list
  // front = newNode;
  // }

  // // If endqueue function work correctly, plus size of queue by 1
  // size++;
  // }

  /**
   * The `dequeue` function removes and returns the front element of the queue, updating the front
   * pointer and size accordingly.
   *
   * @return The `dequeue` method returns the data that was removed from the front of the queue.
   */
  public T dequeue() {
    // If this queue empty, end this dequeue operation and return as a null value
    if (isEmpty()) {
      return null;
    }

    // Recieve Front data before move node pointer
    T data = front.getData();

    // Move front pointer to front.next
    front = front.getNext();

    // If dequeue function work correctly, reduce size of queue by 1
    size--;

    // Set up pointer to default. when Front == null, Mean real must be null too
    // Use to prevent future conflict in mis match pointer
    if (front == null) {
      rear = null;
    }

    // Return Front data that keep before move pointer forward
    return data;
  }

  /**
   * The `peek` function in Java returns the data in the front node of a queue, or null if the queue
   * is empty.
   *
   * @return The `peek` method is returning the data stored in the front node of the queue.
   */
  public T peek() {
    // If this queue empty, end this peek operation and return as a null value
    if (isEmpty()) {
      return null;
    }

    // Return data in front node
    return front.getData();
  }

  /**
   * The `iterator` method returns an iterator that iterates over a collection of elements stored in
   * a linked list.
   *
   * @return An iterator object that allows iterating over elements of a collection of type T is
   *         being returned.
   */
  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private Node<T> current = front;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        T data = current.getData();
        current = current.getNext();
        return data;

      }
    };
  }
}
