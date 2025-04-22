package javaroke.stack;

import javaroke.models.NodeSong;

public class StackSong extends StackAbstract<NodeSong> {
    // Set up Stack Song
    public StackSong() {
        super(); // like use StackAbstract() to set up StackAbstract<NodeSong> that we are extended
    }

    // Other push function that input as String
    public void push(String songId, String title, String artist, Integer duration) {
        push(new NodeSong(songId, title, artist, duration));
    }
}
