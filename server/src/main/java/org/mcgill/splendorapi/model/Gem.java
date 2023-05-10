package org.mcgill.splendorapi.model;

import lombok.Builder;
import lombok.Getter;


/**
 * Gem representation.
 */
@Builder
@Getter
public class Gem {
  private final String id;
  private boolean isAvailable;
  private final GemType type;

  /**
   * Sets the gem back to begin free.
   */
  public void free() {
    isAvailable = true;
  }

  /**
   * Sets the availability to false.
   */
  public void purchase() {
    isAvailable = false;
  }
}
