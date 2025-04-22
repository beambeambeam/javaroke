package javaroke.stack;

import javaroke.models.Node;;

public abstract class StackAbstract<T> {
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
        if (data == null) {
            return;
        }

        Node<T> newNode = new Node<T>(data);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T data = top.getData();
        top = top.getNext();
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return top.getData();
    }
}
