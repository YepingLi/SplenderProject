package org.mcgill.splendorapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Gem representation.
 */
@Builder
@Getter
public class Gem {
  private final String id;
  private final String uri;
  private boolean isAvailable;
  private final Type type;

  /**
   * The gem type.
   */
  @RequiredArgsConstructor
  public enum Type {
    DIAMOND(0, 7), EMERALD(1, 7), ONYX(2, 7), RUBY(3, 7), SAPPHIRE(4, 7), GOLD(5, 5);
    final int value;
    final int maxValue;

    /**
     * Get the maximum number of tokens per token type.
     *
     * @param numPlayers Number of players in the game
     * @return The number of tokens
     */
    public int getMaxNumber(int numPlayers) {
      if (numPlayers == 3) {
        return maxValue - 2;
      } else if (numPlayers == 2) {
        return maxValue - 3;
      }

      return maxValue;
    }
  }

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
