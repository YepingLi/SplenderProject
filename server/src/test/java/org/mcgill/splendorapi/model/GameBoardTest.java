package org.mcgill.splendorapi.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;



import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

  GamePlayer aCurPlayer = new GamePlayer("abc");
  List<GamePlayer> aPlayers = new ArrayList<>();
  List<Card> aDeck = new ArrayList<>();
  Map<Bonus.BonusType, Bonus> aBonuses = new HashMap<>();
  Map<Gem.Type, Integer> aAvailableGems = new HashMap<>();
  List<Gem> aGems = new ArrayList<>();
  GameBoard aBoard = new GameBoard(aCurPlayer, aPlayers, aDeck, aBonuses, aAvailableGems, aGems);

  Card testCard1 = new Card(1, 123, new Bonus(Bonus.BonusType.EMERALD, 1), 3,"123", new HashMap<>());
  Card testCard2 = new Card(2, 4, new Bonus(Bonus.BonusType.EMERALD, 1), 3,"123", new HashMap<>());
  Card testCard3 = new Card(3, 5, new Bonus(Bonus.BonusType.EMERALD, 1), 3,"123", new HashMap<>());
  Card testCard4 = new Card(1, 6, new Bonus(Bonus.BonusType.EMERALD, 1), 3,"123", new HashMap<>());

  @Test
  void levelOneCards() {
    List<Card> Deck = new ArrayList<>();
    testCard1.turnOver();
    Deck.add(testCard1);
    Deck.add(testCard2);
    Deck.add(testCard3);
    GameBoard aBoard = new GameBoard(aCurPlayer, aPlayers, Deck, aBonuses, aAvailableGems, aGems);
    aBoard.init();
    List<Card> levelOneCards = aBoard.levelOneCards();
    assertEquals(1, levelOneCards.size());
    assertEquals(1, levelOneCards.get(0).getLevel());
  }

  @Test
  void levelTwoCards() {
    List<Card> Deck = new ArrayList<>();
    testCard2.turnOver();
    Deck.add(testCard1);
    Deck.add(testCard2);
    Deck.add(testCard3);
    GameBoard aBoard = new GameBoard(aCurPlayer, aPlayers, Deck, aBonuses, aAvailableGems, aGems);
    aBoard.init();
    List<Card> levelTwoCards = aBoard.levelTwoCards();
    assertEquals(1, levelTwoCards.size());
    assertEquals(2, levelTwoCards.get(0).getLevel());
  }

  @Test
  void levelThreeCards() {
    List<Card> Deck = new ArrayList<>();
    testCard3.turnOver();
    Deck.add(testCard1);
    Deck.add(testCard2);
    Deck.add(testCard3);
    GameBoard aBoard = new GameBoard(aCurPlayer, aPlayers, Deck, aBonuses, aAvailableGems, aGems);
    aBoard.init();
    List<Card> levelThreeCards = aBoard.levelThreeCards();
    assertEquals(1, levelThreeCards.size());
    assertEquals(3, levelThreeCards.get(0).getLevel());
  }

  @Test
  void getCard() throws GameBoardException {
    List<Card> Deck = new ArrayList<>();
    Deck.add(testCard1);
    Deck.add(testCard2);
    Deck.add(testCard3);
    GameBoard aBoard = new GameBoard(aCurPlayer, aPlayers, Deck, aBonuses, aAvailableGems, aGems);
    aBoard.init();
    Card card = aBoard.getCard(123);
    assertEquals(123, card.getId());
  }

  @Test
  void checkIsTurn() {
    assertThrows(IllegalMoveException.class, () -> aBoard.checkIsTurn("bac"));
  }

}