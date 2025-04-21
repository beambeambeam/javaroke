package javaroke.models;

/**
 * The `Node` class in Java represents a node in a linked list that stores data of type `T` and has
 * methods to get and set the data and the next node.
 *
 * @param <T> The type of data that the node will store.
 */
public class Node<T> {
  private T data;
  private Node<T> next;

  public Node(T data) {
    this.next = null;
    this.data = data;
  }

  /**
   * The `getData` function in Java returns the data stored in the object.
   *
   * @return The method `getData()` is returning the variable `data` of type `T`.
   */
  public T getData() {
    return data;
  }

  /**
   * The `getNext` function in Java returns the next node in a linked list.
   *
   * @return The `next` node of type `T` is being returned.
   */
  public Node<T> getNext() {
    return next;
  }

  /**
   * The function setData sets the value of a variable data.
   *
   * @param data The `setData` method is a generic method that takes a parameter `data` of type `T`
   *        and sets the instance variable `this.data` to the value of the `data` parameter. The
   *        type `T` is a placeholder for any data type that can be specified when calling the
   *        method
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * The setNext function in Java sets the next node in a linked list.
   *
   * @param next The `next` parameter in the `setNext` method is of type `Node<T>`, which means it
   *        is a reference to the next node in a linked list that stores elements of type `T`.
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }

}
