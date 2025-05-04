package javaroke.gui;

import javaroke.songq.SongQueue;
import javaroke.stack.StackSong;

public class DataSingleton {
  private static final DataSingleton instance = new DataSingleton();

  private DataSingleton() {}

  public static DataSingleton getInstance() {
    return instance;
  }

  private SongQueue songQueue;
  private StackSong songHistoryStack;

  public SongQueue getSongQueue() {
    if (songQueue == null) {
      songQueue = new SongQueue();
    }
    return songQueue;
  }

  public StackSong getSongHistoryStack() {
    if (songHistoryStack == null) {
      songHistoryStack = new StackSong();
    }
    return songHistoryStack;
  }
}
