package org.mcgill.splendorapi.connector.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGame {
  @Test
  public void testBuild(){
    List<String> players = new ArrayList<>();
    players.add("maex");
    Game game = Game.builder().saveGameId("abc-123").gameName("Something").players(players).build();
    assertEquals("abc-123", game.getSaveGameId());
    assertEquals("Something", game.getGameName());
    assertTrue(game.getPlayers().contains("maex"));
  }
}
