package javaroke.gui.play;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javaroke.gui.DataSingleton;
import javaroke.gui.SceneController;
import javaroke.songq.SongQueue;

public class Controller extends SceneController implements Initializable {
  DataSingleton data = DataSingleton.getInstance();
  SongQueue storageSongQueue = data.getSongQueue();

  @FXML
  private ListView<String> queueList;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    queueList.getItems().addAll(storageSongQueue.getAllSong().stream()
        .map(song -> song.getTitle() + " " + song.getArtist()).toList());

  }
}
