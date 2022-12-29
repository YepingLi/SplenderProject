package org.mcgill.splendorapi.connector.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestGameService {
  @Test
  public void testBuild(){
    GameService service = GameService.builder()
        .displayName("display")
        .location("http://localhost")
        .minPlayers(100)
        .maxPlayers(200)
        .name("gameService")
        .support(false)
        .build();

    assertEquals("http://localhost", service.getLocation());
    assertEquals("display", service.getDisplayName());
    assertEquals("gameService", service.getName());
    assertEquals(Integer.valueOf(200), service.getMaxSessionPlayers());
    assertEquals(Integer.valueOf(100), service.getMinSessionPlayers());
    assertFalse(service.getWebSupport());
  }
}
