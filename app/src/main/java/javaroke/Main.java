package javaroke;

import javaroke.gui.Controller;
import javaroke.stack.StackLyric;
import javaroke.stack.StackSong;

public class Main {

  public static void main(String[] args) {
    stackLyricTest();
    System.out.println("");
    stackSongTest();
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
}
