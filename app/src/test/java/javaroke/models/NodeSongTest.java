package javaroke.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class NodeSongTest {

  @Test
  public void testConstructorAndGetters() {
    NodeSong song = new NodeSong("123", "Test Title", "Test Artist", 225);
    assertEquals("123", song.getSongId());
    assertEquals("Test Title", song.getTitle());
    assertEquals("Test Artist", song.getArtist());
    assertEquals(225, (int) song.getDuration());
  }

  @Test
  public void testSetSongId() {
    NodeSong song = new NodeSong("123", "Test Title", "Test Artist", 225);
    song.setSongId("456");
    assertEquals("456", song.getSongId());
  }

  @Test
  public void testSetTitle() {
    NodeSong song = new NodeSong("123", "Test Title", "Test Artist", 225);
    song.setTitle("New Title");
    assertEquals("New Title", song.getTitle());
  }

  @Test
  public void testSetArtist() {
    NodeSong song = new NodeSong("123", "Test Title", "Test Artist", 225);
    song.setArtist("New Artist");
    assertEquals("New Artist", song.getArtist());
  }

  @Test
  public void testSetDuration() {
    NodeSong song = new NodeSong("123", "Test Title", "Test Artist", 225);
    song.setDuration(270);
    assertEquals(270, (int) song.getDuration());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSongId() {
    new NodeSong("", "Test Title", "Test Artist", 225);
  }
}
