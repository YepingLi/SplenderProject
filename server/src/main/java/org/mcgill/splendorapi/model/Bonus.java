package org.mcgill.splendorapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

/**
 * Bonus representation.
 */
@Jacksonized
@Builder
@Getter
public class Bonus {

  /**
   * Gem of the gem.
   */
  @RequiredArgsConstructor
  @Getter
  public enum BonusType {
    ONYX("ONYX"),
    SAPPHIRE("SAPPHIRE"),
    DIAMOND("DIAMOND"),
    EMERALD("EMERALD"),
    RUBY("RUBY");

    private final String value;
  }

  private BonusType bonusType;
  private final int totalBonus;
}
