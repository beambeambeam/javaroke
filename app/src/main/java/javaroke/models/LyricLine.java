package javaroke.models;

import javafx.util.Duration;

/**
 * Represents a single line of lyrics with its corresponding timestamp.
 *
 * @param time The duration from the start of the song when this line should be displayed.
 * @param line The text content of the lyric line.
 */
public record LyricLine(Duration time, String line) {}
