package javaroke.gui.play;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javaroke.gui.DataSingleton;
import javaroke.gui.SceneController;
import javaroke.songq.SongQueue;

public class Controller extends SceneController implements Initializable {
  DataSingleton data = DataSingleton.getInstance();
  SongQueue externalQueueList = data.getSongQueue();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("Loading 3 !");
    externalQueueList.displayQueue();
  }
}
