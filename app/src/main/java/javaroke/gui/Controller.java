package javaroke.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The `Controller` class in Java displays a JavaFX window showing the Java and JavaFX versions
 * being used.
 */
public class Controller extends Application {

  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(createContents(), 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  private Region createContents() {
    VBox results = new VBox(20, createMain());
    results.setAlignment(Pos.CENTER);
    results.getStylesheets()
        .add(this.getClass().getResource("/javaroke/gui/controller.css").toExternalForm());
    return results;
  }

  private HBox createMain() {
    Label mainText = new Label("JAVAROKE!");
    HBox hBox = new HBox(6, mainText);
    hBox.setAlignment(Pos.CENTER);
    return hBox;
  }
}
