package javaroke.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The `Controller` class in Java displays a JavaFX window showing the Java and JavaFX versions
 * being used.
 */
public class Controller extends Application {
  /**
   * The start method initializes a JavaFX application window displaying the Java and JavaFX
   * versions being used.
   *
   * @param stage The `stage` parameter in the `start` method represents the primary stage for the
   *        JavaFX application. It is the top-level container for the JavaFX application window
   *        where you can set scenes, show or hide the window, and perform other window-related
   *        operations.
   */
  @Override
  public void start(Stage stage) {
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label l =
        new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    Scene scene = new Scene(new StackPane(l), 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The main function in Java that launches the program with command-line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
