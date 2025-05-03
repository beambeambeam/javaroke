package javaroke.gui.search;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javaroke.gui.DataSingleton;
import javaroke.gui.SceneController;
import javaroke.songq.SongQueue;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;
import com.google.gson.*;

public class Controller extends SceneController implements Initializable {
  DataSingleton data = DataSingleton.getInstance();
  SongQueue externalQueueList = data.getSongQueue();

  @FXML
  private ListView<String> songList;

  @FXML
  private ListView<String> queueList;

  @FXML
  private Button karaokeButton;

  private List<Item> items;

  public static class Item {
    private String id;
    private String title;
    private String artist;

    public Item(String id, String title, String artist) {
      this.id = id;
      this.title = title;
      this.artist = artist;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getArtist() {
      return artist;
    }

    public void setArtist(String artist) {
      this.artist = artist;
    }
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public void loadItemsFromFolder(String folderPath) {
    Path folder = Path.of(folderPath);
    if (Files.notExists(folder) || !Files.isDirectory(folder)) {
      System.err.println("Invalid folder path: " + folderPath);
      return;
    }

    items = new ArrayList<>();

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, "*.{json,JSON}")) {
      for (Path file : stream) {
        try (
            JsonReader jr = new JsonReader(Files.newBufferedReader(file, StandardCharsets.UTF_8))) {
          JsonObject jsonObject = JsonParser.parseReader(jr).getAsJsonObject();
          String title =
              jsonObject.has("title") ? jsonObject.get("title").getAsString() : "Unknown Title";
          String artist =
              jsonObject.has("artist") ? jsonObject.get("artist").getAsString() : "Unknown Artist";
          items.add(new Item(artist, title, artist));
        } catch (Exception ex) {
          System.err.println("Error reading file: " + file.getFileName() + " - " + ex.getMessage());
        }
      }
    } catch (Exception ex) {
      System.err.println("Error accessing folder: " + ex.getMessage());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("Switch to Search View");
    loadItemsFromFolder("src/main/resources/songs");
    if (items != null) {
      for (Item item : items) {
        songList.getItems().add(item.getTitle() + " " + item.artist);
      }
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
            externalQueueList.addSong(item.getId(), item.getTitle(), item.getArtist(), 0);
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
