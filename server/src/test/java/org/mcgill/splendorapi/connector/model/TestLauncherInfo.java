package org.mcgill.splendorapi.connector.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLauncherInfo {
  @Test
  public void testBuild(){
    List<LsPlayer> players = new ArrayList<>();
    players.add(LsPlayer.builder().name("maex").color("blue").build());
    LauncherInfo launcherInfo = LauncherInfo.builder()
                                            .saveGameId("abc-123")
                                            .name("Something")
                                            .players(players)
                                            .build();
    assertEquals("abc-123", launcherInfo.getSaveGameId());
    assertEquals("Something", launcherInfo.getName());
    assertTrue(launcherInfo.getPlayers()
                           .stream()
                           .filter(player -> player.getName().equals("maex"))
                           .collect(Collectors.toList())
                           .size() == 1);
  }
}
