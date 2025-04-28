package javaroke;

import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.util.List;
import javaroke.gui.Controller;
import javaroke.models.NodeLyric;
import javaroke.queue.QueueLyric;
import javaroke.stack.StackLyric;
import javaroke.stack.StackSong;

public class Main {

  public static void main(String[] args) {
    stackLyricTest();
    System.out.println("");
    // stackSongTest();
    SongQueueTest();
    Controller.main(args);
  }


  public static void stackLyricTest() {
    StackLyric stackLyric = new StackLyric();

    stackLyric.push(0, "know you're all");
    stackLyric.push(2, "that I want this life");
    stackLyric.push(8, "UIIAAIUIA");
    stackLyric.push(11, "U A A I A");
    stackLyric.push(12, "I U U A");

    System.out.println(
        "StackLyric test>>  " + stackLyric.peek().getTime() + " " + stackLyric.peek().getline());
    stackLyric.pop();
    stackLyric.pop();
    System.out.println(
        "StackLyric test>>  " + stackLyric.peek().getTime() + " " + stackLyric.peek().getline());
  }

  public static void stackSongTest() {
    StackSong stackSong = new StackSong();

    stackSong.push("U-I-A-Cat-x-yung-kai-blue", "UIA Cat x yung kai blue", "U1", 153);
    stackSong.push("name2", "Name 2", "U2", 123);
    stackSong.push("3eman", "3 Eman", "U3", 164);

    System.out.println("StackSong test>>  " + stackSong.peek().getSongId());
    stackSong.pop();
    stackSong.pop();
    System.out.println("StackSong test>>  " + stackSong.peek().getSongId());
  }


  // Test queue of running lyrics
  public static void SongQueueTest() {
    QueueLyric lyricsQueue = new QueueLyric("src/main/songs/jai-sung-mah-eng.json");

    System.out.println("Song Queue Test - Time Simulation");

    // Show All Lyrics List
    List<String[]> allLyrics = lyricsQueue.getAllLyrics();
    System.out.println("Total lyrics loaded: " + allLyrics.size());
    System.out.println("All lyrics:");
    for (String[] lyric : allLyrics) {
      System.out.println("[" + lyric[0] + ", \"" + lyric[1] + "\"]");
    }
    int currentTimeSeconds = 0;
    int maxTimeSeconds = 240;

    // Play MP3 file asynchronously
    new Thread(() -> {
      try {
        FileInputStream fis = new FileInputStream("src/main/songs/mp3/jai-sung-mah.mp3");
        Player player = new Player(fis);
        System.out.println("Now playing: jai-sung-mah.mp3");
        player.play();
      } catch (Exception e) {
        System.out.println("Error playing MP3 file: " + e.getMessage());
        e.printStackTrace();
      }
    }).start();

    while (currentTimeSeconds <= maxTimeSeconds && !lyricsQueue.isEmpty()) {
      String formattedTime =
          String.format("%02d:%02d", currentTimeSeconds / 60, currentTimeSeconds % 60);

      // Check if we've reached the next lyric's timestamp
      if (!lyricsQueue.isEmpty() && lyricsQueue.peek().getTime() <= currentTimeSeconds) {
        NodeLyric currentLyric = lyricsQueue.dequeue();
        System.out.println(
            formattedTime + " (" + currentLyric.getTime() + "s) -> " + currentLyric.getline());
      }

      // Increment 1 Second
      currentTimeSeconds++;

      try {
        Thread.sleep(1000); // 1 seconds time count
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    if (lyricsQueue.isEmpty()) {
      System.out.println("All lyrics have been displayed.");
    } else {
      System.out.println("Simulation ended with remaining lyrics.");
    }

  }
}
