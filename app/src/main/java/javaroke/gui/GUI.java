

package javaroke.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The `Controller` class in Java displays a JavaFX window showing the Java and JavaFX versions
 * being used.
 */
public class GUI extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/home.fxml"));
    Parent content = loader.load();

    Scene scene = new Scene(content);
    // Apply the CSS stylesheet
    scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
