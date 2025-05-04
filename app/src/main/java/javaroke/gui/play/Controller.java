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
import javaroke.stack.StackSong;

public class Controller extends SceneController implements Initializable {
  DataSingleton data = DataSingleton.getInstance();
  SongQueue storageSongQueue = data.getSongQueue();
  StackSong historyStack = data.getSongHistoryStack();

  @FXML
  private ListView<String> queueList;

  @FXML
  private ListView<String> historyList;

  @FXML
  private Label songName;

  @FXML
  private Label artistName;

  @FXML
  private void handleNextSong() {
    if (storageSongQueue.hasNext()) {
      NodeSong currentSong = storageSongQueue.getCurrentSong(); // Get song before dequeuing
      if (currentSong != null) {
        historyStack.push(currentSong);
        historyList.getItems().add(0, currentSong.getTitle() + " " + currentSong.getArtist()); // Add to top of history list
      }
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

    // Populate history list on initialization
    for (NodeSong song : historyStack) {
      historyList.getItems().add(song.getTitle() + " " + song.getArtist());
    }
  }
}
