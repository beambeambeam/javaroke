package javaroke.gui.play;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javaroke.gui.DataSingleton;
import javaroke.gui.SceneController;
import javaroke.models.NodeSong;
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

  @FXML
  private void handleNextSong() {
    if (storageSongQueue.hasNext()) {
      storageSongQueue.dequeueSong();
      queueList.getItems().remove(0);
    }
  }

  @FXML
  private void handlePrevSong() {
    if (storageSongQueue.hasPrev()) {
      NodeSong prevSong = storageSongQueue.rewindToPreviousSong();
      queueList.getItems().add(0, prevSong.getTitle() + " " + prevSong.getArtist());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    queueList.getItems()
        .addListener((javafx.collections.ListChangeListener.Change<? extends String> change) -> {
          while (change.next()) {
            if (change.wasAdded() || change.wasRemoved()) {
              if (storageSongQueue.getCurrentSong() == null) {
                songName.setText("ไม่มี");
                artistName.setText("ไม่มี");
                return;
              }

              songName.setText(storageSongQueue.getCurrentSong().getTitle());
              artistName.setText(storageSongQueue.getCurrentSong().getArtist());
            }
          }
        });

    queueList.getItems().addAll(storageSongQueue.getAllSongs().stream()
        .map(song -> song.getTitle() + " " + song.getArtist()).toList());
  }
}
