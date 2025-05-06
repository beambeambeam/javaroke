package javaroke.gui.play;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image; // Added import
import javafx.scene.image.ImageView; // Added import
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer.Status;
import javaroke.gui.DataSingleton;
import javaroke.gui.SceneController;
import javaroke.models.NodeSong;
import javaroke.service.MediaPlayerService;
import javaroke.songq.SongQueue;
import javaroke.stack.StackSong;
import javaroke.utils.Formatter;

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
  private Label lyricLabel;

  @FXML // Added FXML variable for the song image
  private ImageView songImageView;

  @FXML // Added FXML variables for slider and time labels
  private Slider timeSlider;
  @FXML
  private Label currentTimeLabel;
  @FXML
  private Label totalDurationLabel;
  @FXML // Added FXML variable for play/pause button
  private Button playPauseButton;
  @FXML // Added FXML variable for play/pause button
  private Button goBackButton;

  // Service instance
  private MediaPlayerService mediaPlayerService = new MediaPlayerService();
  private boolean sliderWasDragged = false;

  @FXML
  private void handleNextSong() {
    if (storageSongQueue.hasNext()) {
      NodeSong currentSong = storageSongQueue.getCurrentSong(); // Get song before dequeuing
      if (currentSong != null) {
        historyStack.push(currentSong);
        historyList.getItems().add(0, currentSong.getTitle() + " " + currentSong.getArtist());
      }
      storageSongQueue.dequeueSong();
      queueList.getItems().remove(0);
      // Tell the service to play the new current song
      mediaPlayerService.playSong(storageSongQueue.getCurrentSong());
    } else {
      // If queue becomes empty, tell the service to stop
      mediaPlayerService.playSong(null); // Passing null stops playback
    }
  }

  @FXML
  private void handlePrevSong() {
    if (storageSongQueue.hasPrev()) {
      NodeSong prevSong = storageSongQueue.rewindToPreviousSong();
      queueList.getItems().add(0, prevSong.getTitle() + " " + prevSong.getArtist());
      if (!historyList.getItems().isEmpty()) {
        historyList.getItems().remove(0);
      }
      // Tell the service to play the song we rewound to
      mediaPlayerService.playSong(prevSong);
    } // Added missing closing brace
  }

  @FXML // Added handler for play/pause button
  private void handlePlayPause() {
    mediaPlayerService.togglePlayPause();
  }

  @FXML
  private Slider volumnSlider;

  @FXML
  private Label volumnPercentage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println(storageSongQueue.getAllSongs());

    // Bind lyric label
    lyricLabel.textProperty().bind(mediaPlayerService.currentLyricProperty());

    mediaPlayerService.currentSongProperty().addListener((obs, oldSong, newSong) -> {
      if (newSong != null) {
        songName.setText(newSong.getTitle());
        artistName.setText(newSong.getArtist());

        // --- Load Song Image using Classpath Resource ---
        String songDirName = newSong.getSongId();
        // Construct path relative to classpath root (starts with /)
        String resourcePath = "/songs/" + songDirName + "/" + songDirName + ".jpg";
        URL imageUrl = getClass().getResource(resourcePath); // Get URL using class loader

        if (imageUrl != null) {
          try {
            Image songImage = new Image(imageUrl.toString()); // Load image from URL
            songImageView.setImage(songImage);
            songImageView.setVisible(true);
            songImageView.setManaged(true);
          } catch (Exception e) {
            System.err
                .println("Error loading image resource: " + resourcePath + " - " + e.getMessage());
            // Keep image hidden if loading fails
            songImageView.setImage(null);
            songImageView.setVisible(false);
            songImageView.setManaged(false);
          }
        } else {
          System.err.println("Image resource not found: " + resourcePath); // Log if resource not
                                                                           // found
          songImageView.setImage(null);
          songImageView.setVisible(false);
          songImageView.setManaged(false);
        }
        // --- End Load Song Image ---

      } else {
        songName.setText("ไม่มี");
        artistName.setText("ไม่มี");
        // Hide image view if no song is playing
        songImageView.setImage(null);
        songImageView.setVisible(false);
        songImageView.setManaged(false);
      }
    });

    // Listener for current time change (updates label and slider value)
    mediaPlayerService.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
      Platform.runLater(() -> {
        // Update label of seeking state
        currentTimeLabel.setText(Formatter.formatDuration(newTime));
        // Update slider only if the user is NOT currently interacting with it
        if (!timeSlider.isValueChanging() && newTime != null && !newTime.isUnknown()) {
          Duration total = mediaPlayerService.totalDurationProperty().get();
          if (total != null && total.greaterThan(Duration.ZERO)) {
            timeSlider.setValue(newTime.toSeconds());
          } else {
            timeSlider.setValue(0);
          }
        }
      });
    });

    // Listener for total duration change (updates label and slider max)
    mediaPlayerService.totalDurationProperty().addListener((obs, oldDuration, newDuration) -> {
      Platform.runLater(() -> {
        totalDurationLabel.setText(Formatter.formatDuration(newDuration));
        if (newDuration != null && newDuration.greaterThan(Duration.ZERO)) {
          timeSlider.setMax(newDuration.toSeconds());
          timeSlider.setDisable(false);
        } else {
          timeSlider.setMax(1);
          timeSlider.setValue(0);
          timeSlider.setDisable(true);
        }
      });
    });

    // Listener for media player status change (updates play/pause button text)
    mediaPlayerService.statusProperty().addListener((obs, oldStatus, newStatus) -> {
      Platform.runLater(() -> {
        if (newStatus == Status.PLAYING) {
          playPauseButton.setText("Pause");
        } else {
          playPauseButton.setText("Play"); // Show Play for PAUSED, READY, STOPPED, etc.
        }
      });
    });

    // Listener for slider value changes (updates label during drag)
    timeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
      // Optional: Update the current time label instantly while dragging
      if (timeSlider.isValueChanging()) {
        currentTimeLabel.setText(Formatter.formatDuration(Duration.seconds(newVal.doubleValue())));
      }
    });

    // --- Slider Seek Logic (Drag vs Click) ---

    // Reset flag on press
    timeSlider.setOnMousePressed(event -> {
      sliderWasDragged = false;
    });

    // Set flag on drag
    timeSlider.setOnMouseDragged(event -> {
      sliderWasDragged = true;
    });

    // Seek if dragged, toggle play/pause if clicked
    timeSlider.setOnMouseReleased(event -> {
      if (sliderWasDragged) {
        // If slider was dragged, seek to the new position
        mediaPlayerService.seek(Duration.seconds(timeSlider.getValue()));
      }
      sliderWasDragged = false;
    });

    timeSlider.setOnKeyReleased(event -> {
      mediaPlayerService.seek(Duration.seconds(timeSlider.getValue()));
    });

    mediaPlayerService.setOnSongEndCallback(this::handleNextSong);

    queueList.getItems().addAll(storageSongQueue.getAllSongs().stream()
        .map(song -> song.getTitle() + " " + song.getArtist()).toList());

    for (NodeSong song : historyStack) {
      historyList.getItems().add(song.getTitle() + " " + song.getArtist());
    }

    mediaPlayerService.playSong(storageSongQueue.getCurrentSong());

    goBackButton.setOnAction(event -> {
      mediaPlayerService.stop();
      try {
        switchToScene2(event);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    initializeVolumeSettings();
  }

  private void initializeVolumeSettings() {
    volumnSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
      mediaPlayerService.setVolume(newVal.doubleValue() / 100.0);
      volumnPercentage.setText(String.format("%.0f%%", newVal.doubleValue()));
    });

    double initialVolume = mediaPlayerService.getCurrentVolume() * 100.0;
    volumnSlider.setValue(initialVolume);
    volumnPercentage.setText(String.format("%.0f%%", initialVolume));
  }
}
