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

  @Test
  public void testValidateTimeForm_validTime() {
    Validator.validateTimeForm("03:45");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateTimeForm_invalidTime() {
    Validator.validateTimeForm("345");
  }
}
