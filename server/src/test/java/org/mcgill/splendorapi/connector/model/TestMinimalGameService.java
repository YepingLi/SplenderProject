package org.mcgill.splendorapi.connector.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMinimalGameService {
  @Test
  public void testBuild(){
    MinimalGameService minimalGameService = new MinimalGameService("name", "displayName");
    assertEquals("displayName", minimalGameService.getDisplayName());
    assertEquals("name", minimalGameService.getName());
  }
}
