package javaroke.utils;

import javafx.util.Duration;

public class Formatter {
  public static String formatDuration(Duration duration) {
    if (duration == null || duration.isUnknown() || duration.isIndefinite()) {
      return "00:00";
    }
    long totalSeconds = (long) duration.toSeconds();
    long minutes = totalSeconds / 60;
    long seconds = totalSeconds % 60;
    return String.format("%02d:%02d", minutes, seconds);
  }
}
