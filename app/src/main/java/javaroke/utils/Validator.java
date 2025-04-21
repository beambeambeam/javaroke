package javaroke.utils;

/**
 * Utility class for validating song IDs.
 * <p>
 * Ensures that song IDs are non-empty and do not contain spaces.
 */

public class Validator {
  /**
   * Validates the format of the provided song ID.
   *
   * @param songId the song ID to validate
   * @throws IllegalArgumentException if the song ID contains spaces or is empty
   */
  public static void validateSongId(String songId) {
    if (songId.contains(" ")) {
      throw new IllegalArgumentException(
          "Invalid songId format: " + songId + "\nIt shouldn't have spaces.");
    }

    if (songId.isEmpty()) {
      throw new IllegalArgumentException("Invalid songId format: songId cannot be empty.");
    }
  }
}
