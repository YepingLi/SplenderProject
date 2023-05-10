package org.mcgill.splendorapi.model.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
  private City city;
  private CardMeta meta;
  private int prestigePoints;
  private int sameRequirement;

  @BeforeEach
  void setUp() {
    meta = new CardMeta((short) 1, CardType.CITY);
    prestigePoints = 5;
    sameRequirement = 2;
    try {
      city = new City(meta, prestigePoints, new HashMap<>(), sameRequirement);
    } catch (InvalidCardType e) {
      fail("Unexpected InvalidCardType exception");
    }
  }

  @Test
  void testCopyConstructor() {
    try {
      City copy = new City(city);
      assertEquals(city.getMeta(), copy.getMeta());
      assertEquals(city.getPrestigePoints(), copy.getPrestigePoints());
      assertEquals(city.getRequirements(), copy.getRequirements());
      assertEquals(city.getSameRequirement(), copy.getSameRequirement());
    } catch (InvalidCardType e) {
      fail("Unexpected InvalidCardType exception");
    }
  }

  @Test
  void testChangeState() {
    try {
      city.changeState(CardState.PURCHASED);
      assertEquals(CardState.PURCHASED, city.getState());
    } catch (IllegalMoveException e) {
      fail("Unexpected IllegalMoveException");
    }
  }
  @Test
  void testSetPosition() {
    city.setPosition(3);
    assertEquals(3, city.getPosition());
    assertThrows(AssertionError.class, () -> city.setPosition(-1));
  }
}