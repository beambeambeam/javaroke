package javaroke.stack;

import javaroke.models.NodeSong;

public class StackSong extends StackAbstract<NodeSong> {
    public StackSong() {
        super();
    }


    public void push(String songId, String title, String artist, Integer duration) {
        push(new NodeSong(songId, title, artist, duration));
    }
}
