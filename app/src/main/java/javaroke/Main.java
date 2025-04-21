package javaroke;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// For queue test
import javaroke.queue.QueueLyric;
import javaroke.queue.QueueSong;
import javaroke.models.NodeLyric;

public class Main extends Application {
  @Override
  public void start(Stage stage) {
    Label label = new Label("Hello, JavaFX!");
    StackPane root = new StackPane(label);
    Scene scene = new Scene(root, 400, 200);
    stage.setScene(scene);
    stage.setTitle("JavaFX App");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static void QueueLyricTest() {
    QueueLyric queueLyric = new QueueLyric();

    queueLyric.enqueue("00:00", "I'm keep into the lesser fire");
    queueLyric.enqueue("00:07", "My body ached to be satisfy");
    queueLyric.enqueue("00:11", "My weakness come and go...");
    queueLyric.enqueueAtFront(new NodeLyric("00:11", "dunno"));
    queueLyric.enqueueAtFront(new NodeLyric("00:13", "dunno2"));

    System.out.println("QueueLyric test>> " + queueLyric.peek().getTime() + " " +
        queueLyric.peek().getline());
    queueLyric.dequeue();
    queueLyric.dequeue();
    System.out.println("QueueLyric test>> " + queueLyric.peek().getTime() + " " +
        queueLyric.peek().getline());
  }

  public static void QueueSongTest() {
    QueueSong queueSong = new QueueSong();

    queueSong.enqueue("yai-mak-mak", "Yai Mak Mak", "A1", "03:12");
    queueSong.enqueue("hai-jai-mai-ook", "Hai Jai Mai Ook", "A2", "06:46");
    queueSong.enqueue("yak-tai", "Yak Tai", "A3", "00:01");

    System.out.println("QueueSong test>> " + queueSong.peek().getSongId() + ", "
        + queueSong.peek().getTitle() + ", " + queueSong.peek().getDuration());
    queueSong.dequeue();
    queueSong.dequeue();
    System.out.println("QueueSong test>> " + queueSong.peek().getSongId() + ", "
        + queueSong.peek().getTitle() + ", " + queueSong.peek().getDuration());
  }
}