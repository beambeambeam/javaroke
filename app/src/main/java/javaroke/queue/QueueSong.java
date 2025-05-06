package javaroke.queue;

import javaroke.models.NodeSong;

public class QueueSong extends QueueAbstract<NodeSong> {
  // Set up Queue Song
  public QueueSong() {
    super(); // like use QueueAbstract() to set up queue<NodeSong> that we are extended
  }

  // Other enqueue function that input as String
  public void enqueue(String songId, String title, String artist, Integer duration) {
    enqueue(new NodeSong(songId, title, artist, duration));
  }

  // Other enqueueAtFront function that input as String
  public void enqueueAtFront(String songId, String title, String artist, Integer duration) {
    enqueueAtFront(new NodeSong(songId, title, artist, duration));
  }

  // Clear all songs in the queue
  public void clearAllSong() {
    while (!isEmpty()) {
      dequeue();
    }
  }
}
