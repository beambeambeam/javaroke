package javaroke.validator;

import org.junit.Test;
import javaroke.utils.Validator;

public class ValidatorTest {
  @Test
  public void testValidateSongId_validId() {
    Validator.validateSongId("validSongId123");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateSongId_invalidId() {
    Validator.validateSongId("invalid song id");
  }
}
