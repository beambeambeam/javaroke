package javaroke.gui.play;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javaroke.gui.DataSingleton;
import javaroke.gui.SceneController;
import javaroke.songq.SongQueue;

public class Controller extends SceneController implements Initializable {
  DataSingleton data = DataSingleton.getInstance();
  SongQueue storageSongQueue = data.getSongQueue();

  @FXML
  private ListView<String> queueList;

  @FXML
  private Label songName;

  @FXML
  private Label artistName;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    queueList.getItems()
        .addListener((javafx.collections.ListChangeListener.Change<? extends String> change) -> {
          while (change.next()) {
            if (change.wasAdded() || change.wasRemoved()) {

              songName.setText(storageSongQueue.getCurrentSong().getTitle());
              artistName.setText(storageSongQueue.getCurrentSong().getArtist());
            }
          }
        });

    queueList.getItems().addAll(storageSongQueue.getAllSong().stream()
        .map(song -> song.getTitle() + " " + song.getArtist()).toList());


  }
}
