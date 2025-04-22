package javaroke.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javaroke.models.Node;;

public abstract class StackAbstract<T> implements Iterable<T> {
    private Node<T> top;
    private int size;

    public StackAbstract() {
        this.top = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(T data) {
        // If input data is null, exist this push process
        if (data == null) {
            return;
        }

        // Create newNode as class T,and call Node() self setup function.
        Node<T> newNode = new Node<T>(data);

        // Connect newNode to top, making newNode be lastest top
        newNode.setNext(top);

        // Move top to newNode that live in the last top of this liked list
        top = newNode;

        // If push function work correctly, plus size of queue by 1
        size++;
    }

    public T pop() {
        // If this stact empty, end this pop operation and return as a null value
        if (isEmpty()) {
            return null;
        }

        // Recieve Top data before move node pointer
        T data = top.getData();

        // Move front pointer to top.next
        top = top.getNext();

        // If pop function work correctly, reduce size of queue by 1
        size--;

        // Return Top data that keep before move pointer forward
        return data;
    }

    public T peek() {
        // If this stack empty, end this peek operation and return as a null value
        if (isEmpty()) {
            return null;
        }

        // Return data in Top node
        return top.getData();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = top;

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
