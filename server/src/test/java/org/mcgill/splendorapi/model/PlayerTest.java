package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

  Player Playertest = new Player("abc", "Yellow");
  @Test
  void getColor() {
    assertEquals("Yellow", Playertest.getColor());
  }

  @Test
  void builder() {
    Player builderPlayer = Player.builder().name("bcd").preferredColour("Blue").build();
    assertEquals("bcd", builderPlayer.getName());
    assertEquals("Blue", builderPlayer.getColor());

  }
}