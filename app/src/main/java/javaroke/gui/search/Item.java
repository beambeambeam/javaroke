package javaroke.gui.search;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;
import com.google.gson.*;


public class Item {
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

  public static void loadItemsFromFolder(String folderPath, ArrayList<Item> items) {
    Path folder = Path.of(folderPath);
    if (Files.notExists(folder) || !Files.isDirectory(folder)) {
      System.err.println("Invalid folder path: " + folderPath);
      return;
    }

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
}
