package com.hexanome.splendor.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dummy.
 */
public class GameBoard {
  private Player curPlayer;
  ArrayList<Player> waitingPlayers;
  //private final Map<Integer, Card> availableCards = new HashMap<>();
  private ArrayList<Card> availableCards = new ArrayList<>();
  private final Map<Bonus.BonusType, Bonus> bonuses = new HashMap<>();
  private final List<Card> reserved = new ArrayList<>();
  private final Map<Integer, Gem> availableGems = new HashMap<>();
  private Map<Gem.Type, Gem> yourGems = new HashMap<>();
  private ArrayList<Gem> availableGold = new ArrayList<>();
  private ArrayList<Gem> availableDiamond = new ArrayList<>();
  private ArrayList<Gem> availableEmerald = new ArrayList<>();
  private ArrayList<Gem> availableOnyx = new ArrayList<>();
  private ArrayList<Gem> availableRuby = new ArrayList<>();
  private ArrayList<Gem> availableSapphire = new ArrayList<>();

  /**
   * Dummy.
   */
  public GameBoard(Player curPlayer, ArrayList<Player> waitingPlayers,
                   ArrayList<Card> availableCards) {
    this.curPlayer = curPlayer;
    this.waitingPlayers = waitingPlayers;
    this.availableCards = availableCards;
  }

  /**
   * Constructor serve for test
   * @param curPlayer
   * @param availableCards
   * @param myGold
   * @param myDiamond
   * @param myEmerald
   * @param myOnyx
   * @param myRuby
   * @param mySapphire
   */
  public GameBoard(Player curPlayer, ArrayList<Card> availableCards, ArrayList<Gem> myGold, ArrayList<Gem> myDiamond,
                ArrayList<Gem> myEmerald, ArrayList<Gem> myOnyx, ArrayList<Gem> myRuby,
                ArrayList<Gem> mySapphire) {
    this.curPlayer = curPlayer;
    this.waitingPlayers = waitingPlayers;
    this.availableCards = availableCards;
    this.availableGold = myGold;
    this.availableDiamond = myDiamond;
    this.availableEmerald = myEmerald;
    this.availableOnyx = myOnyx;
    this.availableRuby = myRuby;
    this.availableSapphire = mySapphire;
  }

  public ArrayList<Card> getAvailableCards() {
    return this.availableCards;
  }

  public Player getCurPlayer() {
    return this.curPlayer;
  }

  public ArrayList<Gem> getAvailableGold() {
    return this.availableGold;
  }

  public ArrayList<Gem> getAvailableDiamond() {
    return this.availableDiamond;
  }

  public ArrayList<Gem> getAvailableEmerald() {
    return this.availableEmerald;
  }

  public ArrayList<Gem> getAvailableOnyx() {
    return this.availableOnyx;
  }

  public ArrayList<Gem> getAvailableSapphire() {
    return this.availableSapphire;
  }

  public ArrayList<Gem> getAvailableRuby() {
    return this.availableRuby;
  }

}
