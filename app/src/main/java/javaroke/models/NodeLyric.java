package javaroke.models;

/**
 * This class likely represents a node with time as seconds and line as print out lyrics
 */
public class NodeLyric {
  private Integer time;
  private String line;

  /**
   * Represents a lyric node with a timestamp and a line of text.
   *
   * @param time The timestamp of the lyric in seconds.
   * @param line The text of the lyric.
   */
  public NodeLyric(Integer time, String line) {
    this.time = time;
    this.line = line;
  }

  /**
   * Retrieves the time associated with this node.
   *
   * @return the time as an Integer.
   */
  public Integer getTime() {

    return time;
  }

  /**
   * Retrieves the value of the line.
   *
   * @return the line as a String
   */
  public String getline() {
    return line;
  }

  /**
   * Sets the time for the NodeLyric.
   *
   * @param time the time value to set
   */
  public void setTime(Integer time) {
    this.time = time;
  }

  /**
   * Sets the lyric line for this node.
   *
   * @param line the lyric line to set
   */
  public void setLine(String line) {
    this.line = line;
  }
}
