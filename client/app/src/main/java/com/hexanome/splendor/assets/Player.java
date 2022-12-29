package com.hexanome.splendor.assets;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummmy.
 */
public class Player {//I think we need to make it a flyweight later.
  private String username;
  private List<Gem> myGold;
  private List<Gem> myDiamond;
  private List<Gem> myEmerald;
  private List<Gem> myOnyx;
  private List<Gem> myRuby;
  private List<Gem> mySapphire;
  private List<Bonus> myBonuses;
  private List<Card> myReservedCards;
  private List<Noble> myNobles;

  /**
   * Dummy.
   */
  public Player(String username, List<Gem> myGold, List<Gem> myDiamond,
                List<Gem> myEmerald, List<Gem> myOnyx, List<Gem> myRuby,
                List<Gem> mySapphire, List<Bonus> bonuses, List<Card> reservedCards,
                List<Noble> nobles) {
    this.username = username;
    this.myGold = myGold;
    this.myDiamond = myDiamond;
    this.myEmerald = myEmerald;
    this.myOnyx = myOnyx;
    this.myRuby = myRuby;
    this.mySapphire = mySapphire;
    this.myBonuses = bonuses;
    this.myReservedCards = reservedCards;
    this.myNobles = nobles;
  }

  //Returns all of the gems
  public List<Gem> getMyGold() {
    return this.myGold;
  }

  public List<Gem> getMyDiamond() {
    return this.myDiamond;
  }

  public List<Gem> getMyEmerald() {
    return this.myEmerald;
  }

  public List<Gem> getMyOnyx() {
    return this.myOnyx;
  }

  public List<Gem> getMySapphire() {
    return this.mySapphire;
  }

  public List<Gem> getMyRuby() {
    return this.myRuby;
  }

}
