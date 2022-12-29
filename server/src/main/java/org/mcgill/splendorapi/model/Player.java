package org.mcgill.splendorapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Player as received from the LS.
 */
@Getter
public class Player extends BasePlayer {
  private final String color;

  /**
   * Build it.
   *
   * @param name username
   * @param preferredColour their color
   */
  @Jacksonized
  @Builder
  public Player(String name, String preferredColour) {
    super(name);
    color = preferredColour;
  }
}
