package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BonusTest {
  Bonus testBinus = new Bonus(Bonus.BonusType.DIAMOND, 2);

  @Test
  void getBonusType() {
    assertEquals(Bonus.BonusType.DIAMOND, testBinus.getBonusType());
    assertNotEquals(Bonus.BonusType.SAPPHIRE, testBinus.getBonusType());
  }

  @Test
  void getTotalBonus() {
    assertEquals(2, testBinus.getTotalBonus());
    assertNotEquals(5, testBinus.getTotalBonus());
  }

}