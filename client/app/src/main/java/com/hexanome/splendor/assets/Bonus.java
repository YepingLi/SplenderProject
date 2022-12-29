package com.hexanome.splendor.assets;

/**
 * Dummy.
 */

public class Bonus {
  /**
   * Dummy.
   */
  public enum BonusType {
    ONYX("ONYX"),
    SAPPHIRE("SAPPHIRE"),
    DIAMOND("DIAMOND"),
    EMERALD("EMERALD"),
    RUBY("RUBY");

    private String value;

    BonusType(String value) {
      value = value;
    }

    public String getValue() {
      return value;
    }
  }

  private BonusType bonusType;
  private int counter = 0;

  public Bonus(BonusType abonustype) {
    bonusType = abonustype;
  }

  public Bonus(BonusType abonustype, int count) {
    this(abonustype);
    counter = count;
  }

  public void addBonus() {
    counter++;
  }
  public int getNum() {
    return counter;
  }

}
