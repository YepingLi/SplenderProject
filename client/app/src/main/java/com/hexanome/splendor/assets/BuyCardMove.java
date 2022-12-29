package com.hexanome.splendor.assets;

/**
 * Dummy.
 */
public class BuyCardMove implements Move {
  private final Player player;
  private Card cardBought;

  public BuyCardMove(Player player, Card cardbought) {
    this.player = player;
    this.cardBought = cardbought;
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }
}
