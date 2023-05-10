package org.mcgill.splendorapi.api.move.enums;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReplacementTest {

  private Replacement replacement;

  @BeforeEach
  void setUp() {
    replacement = Replacement.DISCARD;
  }

  @Test
  void testReplace() {
    String originalString = "DISCARD_Gem";
    String expectedString = "Gem";
    String actualString = replacement.replace(originalString);
    assertEquals(expectedString, actualString, "The replaced string should match the expected string.");
  }

  @Test
  void testStartsWith() {
    String stringToCheck = "DISCARD_Gem";
    boolean actualResult = replacement.startsWith(stringToCheck);
    assertTrue(actualResult);
  }

}
