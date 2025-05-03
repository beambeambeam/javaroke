package javaroke.songq;

import javaroke.queue.QueueSong;
import javaroke.stack.StackSong;
import javaroke.models.NodeSong;
import java.util.List;
import java.util.ArrayList;

public class SongQueue {

  private QueueSong queue;
  private StackSong history;

  public SongQueue() {
    this.queue = new QueueSong();
    this.history = new StackSong();
  }

  public void enqueueSong(String songId, String title, String artist, int duration) {
    NodeSong newSong = new NodeSong(songId, title, artist, duration);
    queue.enqueue(newSong);
  }

  public void dequeueSong() {
    if (queue.isEmpty()) {
      return;
    }

    NodeSong song = queue.dequeue();
    if (song != null) {
      history.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
    }
  }

  public List<NodeSong> getPendingSongs() {
    List<NodeSong> pendingSongs = new ArrayList<>();
    QueueSong tempQueue = new QueueSong();

    while (!queue.isEmpty()) {
      NodeSong song = queue.dequeue();
      pendingSongs.add(song);
      tempQueue.enqueue(song);
    }

    while (!tempQueue.isEmpty()) {
      queue.enqueue(tempQueue.dequeue());
    }

    return pendingSongs;
  }

  public List<NodeSong> getHistorySongs() {
    List<NodeSong> historySongs = new ArrayList<>();
    StackSong tempStack = new StackSong();

    while (!history.isEmpty()) {
      NodeSong song = history.pop();
      historySongs.add(song);
      tempStack.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
    }

    while (!tempStack.isEmpty()) {
      NodeSong song = tempStack.pop();
      history.push(song.getSongId(), song.getTitle(), song.getArtist(), song.getDuration());
    }

    return historySongs;
  }

  public List<NodeSong> getAllSongs() {
    List<NodeSong> allSongs = new ArrayList<>();
    allSongs.addAll(getPendingSongs());
    allSongs.addAll(getHistorySongs());
    return allSongs;
  }

  public NodeSong getCurrentSong() {
    if (!queue.isEmpty()) {
      return queue.peek();
    }
    return null;
  }

  public NodeSong rewindToPreviousSong() {
    if (!history.isEmpty()) {
      NodeSong prevSong = history.pop();
      queue.enqueueAtFront(prevSong);
      return prevSong;
    }
    return null;
  }

  public NodeSong playNextSong() {
    if (!queue.isEmpty()) {
      NodeSong nextSong = queue.dequeue();
      history.push(nextSong.getSongId(), nextSong.getTitle(), nextSong.getArtist(),
          nextSong.getDuration());
      return nextSong;
    }
    return null;
  }

  public boolean hasNext() {
    return !queue.isEmpty();
  }

  public boolean hasPrev() {
    return !history.isEmpty();
  }
}
