package javaroke.models;

import javaroke.utils.Validator;

/**
 * Represents a song node with details such as song ID, title, artist, and duration.
 */
public class NodeSong {
  private String songId;
  private String title;
  private String artist;
  private Integer duration;

  /**
   * Represents a song node with its associated details.
   *
   * @param songId Unique identifier for the song.
   * @param title Title of the song.
   * @param artist Artist of the song.
   * @param duration Duration of the song in seconds.
   */
  public NodeSong(String songId, String title, String artist, Integer duration) {
    Validator.validateSongId(songId);
    this.songId = songId;
    this.title = title;
    this.artist = artist;
    this.duration = duration;
  }

  /**
   * Retrieves the unique identifier of the song.
   *
   * @return the song ID as a String.
   */
  public String getSongId() {
    return songId;
  }

  /**
   * Retrieves the title of the song.
   *
   * @return the title as a String.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Retrieves the artist of the song.
   *
   * @return the artist name as a String.
   */
  public String getArtist() {
    return artist;
  }

  /**
   * Retrieves the duration of the song.
   *
   * @return the duration in seconds as an Integer.
   */
  public Integer getDuration() {
    return duration;
  }

  /**
   * Sets the song ID for the node. Validates the provided song ID before assigning it.
   *
   * @param songId the unique identifier of the song
   * @throws IllegalArgumentException if the song ID is invalid
   */
  public void setSongId(String songId) {
    Validator.validateSongId(songId);
    this.songId = songId;
  }

  /**
   * Sets the title of the song.
   *
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Sets the artist name for the song.
   *
   * @param artist the name of the artist to set
   */
  public void setArtist(String artist) {
    this.artist = artist;
  }

  /**
   * Sets the duration of the song.
   *
   * @param duration the duration of the song in seconds
   */
  public void setDuration(Integer duration) {
    this.duration = duration;
  }
}
