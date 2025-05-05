package javaroke.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty; // Added import
import javafx.beans.property.ReadOnlyObjectWrapper; // Added import
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status; // Added import
import javafx.util.Duration;
import javaroke.models.LyricLine;
import javaroke.models.NodeSong;

public class MediaPlayerService {

  private MediaPlayer mediaPlayer;
  private List<LyricLine> currentLyrics = new ArrayList<>();

  private final ObjectProperty<NodeSong> currentSongProperty = new SimpleObjectProperty<>(null);
  private final StringProperty currentLyricProperty = new SimpleStringProperty("...");
  // Wrappers for time properties
  private final ReadOnlyObjectWrapper<Duration> currentTime =
      new ReadOnlyObjectWrapper<>(Duration.ZERO);
  private final ReadOnlyObjectWrapper<Duration> totalDuration =
      new ReadOnlyObjectWrapper<>(Duration.ZERO);
  // Wrapper for status property
  private final ReadOnlyObjectWrapper<Status> status = new ReadOnlyObjectWrapper<>(Status.UNKNOWN);

  private Runnable onSongEndCallback;

  public ObjectProperty<NodeSong> currentSongProperty() {
    return currentSongProperty;
  }

  public StringProperty currentLyricProperty() {
    return currentLyricProperty;
  }

  // Getters for read-only time properties
  public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
    return currentTime.getReadOnlyProperty();
  }

  public ReadOnlyObjectProperty<Duration> totalDurationProperty() {
    return totalDuration.getReadOnlyProperty();
  }

  // Getter for read-only status property
  public ReadOnlyObjectProperty<Status> statusProperty() {
    return status.getReadOnlyProperty();
  }

  public void setOnSongEndCallback(Runnable onSongEndCallback) {
    this.onSongEndCallback = onSongEndCallback;
  }

  public void playSong(NodeSong song) {
    stop(); // Stop previous song if any

    currentSongProperty.set(song);
    currentLyrics.clear();
    currentLyricProperty.set("...");
    // Reset time and status properties when stopping/changing song
    currentTime.set(Duration.ZERO);
    totalDuration.set(Duration.ZERO);
    status.set(Status.STOPPED); // Set status to STOPPED

    if (song == null) {
      return; // Nothing to play
    }

    loadAndParseLyrics(song.getSongId());

    String mp3ResourcePath = "/songs/" + song.getSongId() + "/" + song.getSongId() + ".mp3";
    URL mp3Url = getClass().getResource(mp3ResourcePath);

    if (mp3Url != null) {
      try {
        Media media = new Media(mp3Url.toExternalForm());
        mediaPlayer = new MediaPlayer(media);

        // Update internal status property based on MediaPlayer
        mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> {
          status.set(newStatus); // Update status wrapper
        });

        // Update internal time properties based on MediaPlayer
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
          if (newTime != null) {
            currentTime.set(newTime); // Update current time wrapper
          }

          // Lyric update logic (remains the same)
          String currentLine = "...";
          for (int i = currentLyrics.size() - 1; i >= 0; i--) {
            // Use accessor methods time() and line() for the record
            if (newTime != null && newTime.greaterThanOrEqualTo(currentLyrics.get(i).time())) {
              currentLine = currentLyrics.get(i).line();
              break;
            }
          }
          final String lineToShow = currentLine;
          // Update lyric property on JavaFX Application Thread
          Platform.runLater(() -> currentLyricProperty.set(lineToShow));
        });

        // Update total duration when ready
        mediaPlayer.setOnReady(() -> {
          Duration duration = mediaPlayer.getMedia().getDuration();
          if (duration != null && !duration.isUnknown() && !duration.isIndefinite()) {
            totalDuration.set(duration);
          } else {
            totalDuration.set(Duration.ZERO); // Or handle appropriately
          }
        });
        // Also listen for changes in duration property itself (might change)
        mediaPlayer.totalDurationProperty().addListener((obs, oldDuration, newDuration) -> {
          if (newDuration != null && !newDuration.isUnknown() && !newDuration.isIndefinite()) {
            totalDuration.set(newDuration);
          } else {
            totalDuration.set(Duration.ZERO);
          }
        });


        mediaPlayer.setOnEndOfMedia(() -> {
          if (onSongEndCallback != null) {
            Platform.runLater(onSongEndCallback); // Run callback on FX thread
          }
        });

        mediaPlayer.setOnError(() -> {
          System.err.println("MediaPlayer Error: " + mediaPlayer.getError());
          Platform.runLater(() -> currentLyricProperty.set("Playback Error"));
        });

        mediaPlayer.play();
        status.set(Status.PLAYING); // Set initial status

      } catch (Exception e) {
        System.err
            .println("Error setting up MediaPlayer for " + mp3ResourcePath + ": " + e.getMessage());
        Platform.runLater(() -> currentLyricProperty.set("Error playing song."));
      }
    } else {
      System.err.println("MP3 resource not found: " + mp3ResourcePath);
      Platform.runLater(() -> currentLyricProperty.set("Song file not found."));
    }
  }

  public void stop() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.dispose();
      mediaPlayer = null;
    }
    // Reset time and status properties on stop
    currentTime.set(Duration.ZERO);
    totalDuration.set(Duration.ZERO);
    status.set(Status.STOPPED);
  }

  public void togglePlayPause() {
    if (mediaPlayer != null) {
      Status currentStatus = status.get();
      if (currentStatus == Status.PLAYING) {
        mediaPlayer.pause();
      } else if (currentStatus == Status.PAUSED || currentStatus == Status.READY
          || currentStatus == Status.STOPPED) {
        // Allow playing from PAUSED, READY, or even STOPPED (will restart if stopped)
        mediaPlayer.play();
      }
      // Status property will be updated by the listener we added
    }
  }

  public void seek(Duration duration) {
    if (mediaPlayer != null) {
      Status status = mediaPlayer.getStatus();
      // Allow seeking only when playing, paused, or ready
      if (status == Status.PLAYING || status == Status.PAUSED || status == Status.READY) {
        if (duration != null && duration.greaterThanOrEqualTo(Duration.ZERO)) {
          Duration total = totalDuration.get();
          // Ensure seek time is within bounds
          if (total != null && total.greaterThan(Duration.ZERO)
              && duration.lessThanOrEqualTo(total)) {
            mediaPlayer.seek(duration);
            // Update currentTime immediately after seek for responsiveness
            currentTime.set(duration);
          } else if (total != null && total.greaterThan(Duration.ZERO)
              && duration.greaterThan(total)) {
            // If seeking past the end, seek to the end
            mediaPlayer.seek(total);
            currentTime.set(total);
          } else {
            mediaPlayer.seek(duration); // Seek even if total duration is unknown
            currentTime.set(duration);
          }
        }
      }
    }
  }


  private void loadAndParseLyrics(String songId) {
    currentLyrics.clear();
    String jsonResourcePath = "/songs/" + songId + "/" + songId + ".JSON";
    URL jsonUrl = getClass().getResource(jsonResourcePath);

    if (jsonUrl != null) {
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(jsonUrl.openStream(), StandardCharsets.UTF_8))) {
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        if (jsonObject.has("lyrics") && jsonObject.get("lyrics").isJsonArray()) {
          JsonArray lyricsArray = jsonObject.getAsJsonArray("lyrics");
          for (int i = 0; i < lyricsArray.size(); i++) {
            JsonObject lyricObject = lyricsArray.get(i).getAsJsonObject();
            String timeStr = lyricObject.get("time").getAsString();
            String line = lyricObject.get("line").getAsString();

            try {
              String[] parts = timeStr.split(":");
              if (parts.length == 2) {
                int minutes = Integer.parseInt(parts[0]);
                int seconds = Integer.parseInt(parts[1]);
                Duration time = Duration.minutes(minutes).add(Duration.seconds(seconds));
                currentLyrics.add(new LyricLine(time, line));
              }
            } catch (NumberFormatException e) {
              System.err.println("Error parsing time: " + timeStr + " for song " + songId);
            }
          }
          // Use accessor method time() for sorting
          currentLyrics.sort((l1, l2) -> l1.time().compareTo(l2.time()));
        }
      } catch (IOException e) {
        System.err
            .println("Error reading lyric resource: " + jsonResourcePath + " - " + e.getMessage());
        Platform.runLater(() -> currentLyricProperty.set("Error loading lyrics."));
      } catch (JsonSyntaxException e) {
        System.err
            .println("Error parsing JSON resource: " + jsonResourcePath + " - " + e.getMessage());
        Platform.runLater(() -> currentLyricProperty.set("Error parsing lyrics."));
      }
    } else {
      System.err.println("Lyric resource not found: " + jsonResourcePath);
      Platform.runLater(() -> currentLyricProperty.set("Lyrics not found."));
    }
  }
}
