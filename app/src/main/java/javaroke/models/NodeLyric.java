package javaroke.models;

public class NodeLyric {
  private Integer time;
  private String line;

  public NodeLyric(Integer time, String line) {
    this.time = time;
    this.line = line;
  }

  public Integer getTime() {

    return time;
  }

  public String getline() {
    return line;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public void setLine(String line) {
    this.line = line;
  }
}
