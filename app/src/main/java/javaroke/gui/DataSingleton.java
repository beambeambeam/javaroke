package javaroke.gui;

import javaroke.songq.SongQueue;

public class DataSingleton {
  private static final DataSingleton instance = new DataSingleton();

  private DataSingleton() {}

  public static DataSingleton getInstance() {
    return instance;
  }

  private SongQueue songQueue;

  public SongQueue getSongQueue() {
    if (songQueue == null) {
      songQueue = new SongQueue();
    }
    return songQueue;
  }
}
