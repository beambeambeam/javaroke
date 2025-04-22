package javaroke.stack;

import javaroke.models.NodeLyric;

public class StackLyric extends StackAbstract<NodeLyric> {
    // Set up Stact Lyric
    public StackLyric() {
        super(); // like use StackAbstract() to set up StackAbstract<NodeSong> that we are extended
    }

    // Other push function that input as string
    public void push(int time, String line) {
        push(new NodeLyric(time, line));
    }
}
