package org.mcgill.splendorapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrientBonusTest {

  @Test
  public void testFromString_validValue_returnEnum() {
    AbstractBonus bonus = OrientBonus.fromString("GOLD");
    assertEquals(OrientBonus.GOLD, bonus);
  }

  @Test
  public void testFromString_invalidValue_throwException() {
    assertThrows(IllegalArgumentException.class, () -> OrientBonus.fromString("invalid"));
  }

  // Add more test cases as needed
}
