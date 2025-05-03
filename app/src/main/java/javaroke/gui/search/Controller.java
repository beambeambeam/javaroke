package javaroke.gui.search;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javaroke.gui.DataSingleton;
import javaroke.gui.SceneController;
import javaroke.songq.SongQueue;
import java.util.ArrayList;

public class Controller extends SceneController implements Initializable {
  DataSingleton data = DataSingleton.getInstance();
  SongQueue storageSongQueue = data.getSongQueue();

  @FXML
  private ListView<String> songList;

  @FXML
  private ListView<String> queueList;

  @FXML
  private Button karaokeButton;

  private ArrayList<Item> items = new ArrayList<>();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Item.loadItemsFromFolder("src/main/resources/songs", items);

    for (Item item : items) {
      songList.getItems().add(item.getTitle() + " " + item.getArtist());
    }

    songList.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        String selectedSong = songList.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
          queueList.getItems().add(selectedSong);
          songList.getItems().remove(selectedSong);
        }
      }
    });

    queueList.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        String selectedSong = queueList.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
          songList.getItems().add(selectedSong);
          queueList.getItems().remove(selectedSong);
        }
      }
    });

    karaokeButton.setDisable(queueList.getItems().isEmpty());

    queueList.getItems()
        .addListener((javafx.collections.ListChangeListener.Change<? extends String> change) -> {
          karaokeButton.setDisable(queueList.getItems().isEmpty());
        });

    karaokeButton.setOnAction(event -> {
      for (String queuedSong : queueList.getItems()) {
        for (Item item : items) {
          if ((item.getTitle() + " " + item.getArtist()).equals(queuedSong)) {
            storageSongQueue.addSong(item.getId(), item.getTitle(), item.getArtist(), 0);
            break;
          }
        }
      }

      try {
        switchToScene3(event);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
