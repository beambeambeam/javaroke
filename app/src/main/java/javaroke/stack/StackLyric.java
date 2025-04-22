package javaroke.stack;

import javaroke.models.NodeLyric;

public class StackLyric extends StackAbstract<NodeLyric> {
    public StackLyric() {
        super();
    }

    public void push(int time, String line) {
        push(new NodeLyric(time, line));
    }
}
