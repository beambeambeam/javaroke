package javaroke.songq;

import javaroke.queue.QueueSong;
import javaroke.stack.StackSong;
import javaroke.models.NodeSong;
import java.util.List;
import java.util.ArrayList;

public class SongQueue {

  private QueueSong queue;
  private StackSong history;
  private NodeSong currentSong;

  public SongQueue() {
    this.queue = new QueueSong();
    this.history = new StackSong();
    this.currentSong = null;
  }

  public void addSong(String songId, String title, String artist, int duration) {
    NodeSong newSong = new NodeSong(songId, title, artist, duration);
    queue.enqueue(newSong);
  }

  public NodeSong removeSong() {
    NodeSong song = queue.dequeue();
    if (song != null) {
      history.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
      currentSong = queue.peek();
    }
    return song;
  }

  public NodeSong getCurrentSong() {
    return currentSong;
  }

  public void historySong() {
    System.out.println("Play History:");
    if (history.isEmpty()) {
      System.out.println("No songs played yet.");
    } else {
      StackSong tempStack = new StackSong();
      while (!history.isEmpty()) {
        NodeSong song = history.pop();
        tempStack.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
      }
      while (!tempStack.isEmpty()) {
        NodeSong song = tempStack.pop();
        System.out.println("- " + song.getSongId());
        history.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
      }
    }
  }

  public void displayQueue() {
    System.out.println("Song Queue:");
    if (queue.isEmpty()) {
      System.out.println("Queue is empty.");
    } else {
      for (NodeSong song : queue) {
        System.out.println("- " + song.getSongId());
      }
    }
  }

  public List<NodeSong> getAllSong() {
    List<NodeSong> allSongs = new ArrayList<>();
    for (NodeSong song : queue) {
      allSongs.add(song);
    }
    return allSongs;
  }
}
