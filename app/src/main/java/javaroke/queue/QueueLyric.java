package javaroke.queue;

import javaroke.models.NodeLyric;
import javaroke.stack.StackLyric;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

// JSON reader
import com.google.gson.stream.JsonReader;
import com.google.gson.*;

public class QueueLyric extends QueueAbstract<NodeLyric> {
  // Set up Queue Lyric
  public QueueLyric() {
    super(); // Initialize the base queue
    LyricHistory = new StackLyric();
  }

  public QueueLyric(String filePath) {
    super(); // Initialize the base queue
    LyricHistory = new StackLyric();
    JSONLyricsToQueue(filePath); // Initialize Lyrics Data
  }

  // read lyrics song structure formated JSON and convert it into queue
  private void JSONLyricsToQueue(String filePath) {
    Path path = Path.of(filePath);
    if (Files.notExists(path) || !Files.isReadable(path)) {
      System.err.println("Cannot access lyrics file: " + filePath);
      return;
    }
    try (JsonReader jr = new JsonReader(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
      JsonArray lyrics = JsonParser.parseReader(jr).getAsJsonObject().getAsJsonArray("lyrics");

      if (lyrics == null) {
        System.err.println("No 'lyrics' array found.");
        return;
      }

      for (JsonElement e : lyrics) {
        JsonObject l = e.getAsJsonObject();
        String[] t = l.get("time").getAsString().split(":");
        int secs = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
        String lineTxt = l.get("line").getAsString();
        enqueue(secs, lineTxt);
      }

    } catch (Exception ex) {
      System.err.println("Error loading lyrics: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  // Other enqueue function that input as string
  public void enqueue(Integer time, String line) {
    enqueue(new NodeLyric(time, line));
  }

  // Other enqueueAtFront function that input as string
  public void enqueueAtFront(Integer time, String line) {
    enqueueAtFront(new NodeLyric(time, line));
  }

  // get lyric of current time
  public String getCurrentLyric() {
    return peek().getline();
  }

  // Return List that carry all lyrics return as ex. [[time, line], [time, line], ...]
  public List<String[]> getAllLyrics() {
    List<String[]> lyricsAndTimes = new ArrayList<>();
    List<NodeLyric> tempNodes = new ArrayList<>();

    while (!isEmpty()) {
      NodeLyric current = dequeue();
      int timeInSeconds = current.getTime();
      int minutes = timeInSeconds / 60;
      int seconds = timeInSeconds % 60;
      String formattedTime = String.format("%02d:%02d", minutes, seconds);
      lyricsAndTimes.add(new String[]{formattedTime, current.getline()});
      tempNodes.add(current);
    }

    // Restore the queue in the original order
    for (NodeLyric node : tempNodes) {
      enqueue(node);
    }

    return lyricsAndTimes;
  }

  private StackLyric LyricHistory;

  // Skip to into the time
  public void skipTo(String songTime) {
    String[] timeParts = songTime.split(":");
    int targetTimeInSeconds = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);

    while (!isEmpty() && peek().getTime() < targetTimeInSeconds) {
      NodeLyric skipped = dequeue();
      LyricHistory.push(skipped);
    }
  }

  // Skip back into the previous time
  public void skipBack() {
    if (!LyricHistory.isEmpty()) {
      NodeLyric previous = LyricHistory.pop();
      enqueueAtFront(previous);
    }
  }
}
