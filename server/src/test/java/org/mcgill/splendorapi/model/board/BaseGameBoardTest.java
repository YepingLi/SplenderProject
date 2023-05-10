package org.mcgill.splendorapi.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.api.move.model.Payment;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.card.*;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BaseGameBoardTest {
  private List<DevelopmentCard> Deck;
  private Map<GemType, Integer> gems;
  private List<NobleCard> nobles;
  NobleCard n1;
  NobleCard n2;
  NobleCard n3;
  NobleCard n4;
  DevelopmentCard L1C1;
  DevelopmentCard L1C2;
  DevelopmentCard L1C3;
  DevelopmentCard L1C4;
  DevelopmentCard L1C5;
  DevelopmentCard L2C1;
  DevelopmentCard L2C2;
  DevelopmentCard L2C3;
  DevelopmentCard L2C4;
  DevelopmentCard L2C5;
  DevelopmentCard L3C1;
  DevelopmentCard L3C2;
  DevelopmentCard L3C3;
  DevelopmentCard L3C4;
  DevelopmentCard L3C5;
  DevelopmentCard OL1C1;
  DevelopmentCard OL1C2;
  DevelopmentCard OL1C3;
  DevelopmentCard OL1C4;
  DevelopmentCard OL1C5;
  DevelopmentCard OL2C1;
  DevelopmentCard OL2C2;
  DevelopmentCard OL2C3;
  DevelopmentCard OL2C4;
  DevelopmentCard OL2C5;
  DevelopmentCard OL3C1;
  DevelopmentCard OL3C2;
  DevelopmentCard OL3C3;
  DevelopmentCard OL3C4;
  DevelopmentCard OL3C5;
  private GameBoard aBoard;
  @BeforeEach
  void setUp() throws InvalidCardType {
    Deck = new ArrayList<>();
    gems = new HashMap<>();
    nobles = new ArrayList<>();
    n1 = new NobleCard(new CardMeta((short) 1, CardType.NOBLE), 1, 1, new HashMap<>());
    n2 = new NobleCard(new CardMeta((short) 2, CardType.NOBLE), 2, 2, new HashMap<>());
    n3 = new NobleCard(new CardMeta((short) 3, CardType.NOBLE), 3, 3, new HashMap<>());
    n4 = new NobleCard(new CardMeta((short) 4, CardType.NOBLE), 4, 4, new HashMap<>());
    nobles.add(n1);
    nobles.add(n2);
    nobles.add(n3);
    nobles.add(n4);
    L1C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 3, new HashMap<>());
    L1C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 3, new HashMap<>());
    L1C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 3, new HashMap<>());
    L1C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 3, new HashMap<>());
    L1C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 3, new HashMap<>());
    L2C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 2, CardType.DEVELOPMENT), Bonus.SAPPHIRE, 3, new HashMap<>());
    L2C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 2, CardType.DEVELOPMENT), Bonus.SAPPHIRE, 3, new HashMap<>());
    L2C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 2, CardType.DEVELOPMENT), Bonus.SAPPHIRE, 3, new HashMap<>());
    L2C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 2, CardType.DEVELOPMENT), Bonus.SAPPHIRE, 3, new HashMap<>());
    L2C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 2, CardType.DEVELOPMENT), Bonus.SAPPHIRE, 3, new HashMap<>());
    L3C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 3, CardType.DEVELOPMENT), Bonus.RUBY, 3, new HashMap<>());
    L3C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 3, CardType.DEVELOPMENT), Bonus.RUBY, 3, new HashMap<>());
    L3C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 3, CardType.DEVELOPMENT), Bonus.RUBY, 3, new HashMap<>());
    L3C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 3, CardType.DEVELOPMENT), Bonus.RUBY, 3, new HashMap<>());
    L3C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 3, CardType.DEVELOPMENT), Bonus.RUBY, 3, new HashMap<>());
    OL1C1 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 1, (short) 1, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL1C2 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 2, (short) 1, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL1C3 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 3, (short) 1, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL1C4 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 4, (short) 1, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL1C5 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 5, (short) 1, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C1 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 1, (short) 2, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C2 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 2, (short) 2, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C3 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 3, (short) 2, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C4 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 4, (short) 2, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C5 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 5, (short) 2, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C1 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 1, (short) 3, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C2 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 2, (short) 3, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C3 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 3, (short) 3, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C4 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 4, (short) 3, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C5 = new OrientDevelopmentCard((new DevelopmentCardMeta((short) 5, (short) 3, CardType.ORIENT)),
                                      Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    Deck.add(L1C1);
    Deck.add(L1C2);
    Deck.add(L1C3);
    Deck.add(L1C4);
    Deck.add(L1C5);
    Deck.add(L2C1);
    Deck.add(L2C2);
    Deck.add(L2C3);
    Deck.add(L2C4);
    Deck.add(L2C5);
    Deck.add(L3C1);
    Deck.add(L3C2);
    Deck.add(L3C3);
    Deck.add(L3C4);
    Deck.add(L3C5);
    Deck.add(OL1C1);
    Deck.add(OL1C2);
    Deck.add(OL1C3);
    Deck.add(OL1C4);
    Deck.add(OL1C5);
    Deck.add(OL2C1);
    Deck.add(OL2C2);
    Deck.add(OL2C3);
    Deck.add(OL2C4);
    Deck.add(OL2C5);
    Deck.add(OL3C1);
    Deck.add(OL3C2);
    Deck.add(OL3C3);
    Deck.add(OL3C4);
    Deck.add(OL3C5);
    gems.put(GemType.RUBY, 5);
    gems.put(GemType.DIAMOND, 5);
    gems.put(GemType.SAPPHIRE, 5);
    gems.put(GemType.SAPPHIRE, 5);
    aBoard = GameBoard.builder().deck(Deck).gems(gems).nobles(nobles).build();
  }


//  @Test
//  void testGetHiddenCards() {
//    List<DevelopmentCard> Deck = new ArrayList<>();
//    Deck.add(L1C1);
//    Deck.add(L1C2);
//    Deck.add(L1C3);
//    Deck.add(L1C4);
//    Deck.add(L1C5);
//    GameBoard aBoard = new GameBoard(Deck, aGems, null);
//    aBoard.init();
//    List<DevelopmentCard> hiddenCards = aBoard.getHiddenCards(Deck);
//    assertEquals(1, hiddenCards.size());
//  }

  @Test
  void levelOneCards() {
    List<DevelopmentCard> Deck = new ArrayList<>();
    aBoard.init();
    List<DevelopmentCard> levelOneCards = aBoard.getLevelOne();
    assertEquals(5, levelOneCards.size());
    assertEquals(1, levelOneCards.get(0).getMeta().getLevel());
  }

  @Test
  void levelTwoCards() {
    aBoard.init();
    List<DevelopmentCard> levelTwoCards = aBoard.getLevelTwo();
    assertEquals(5, levelTwoCards.size());
    assertEquals(2, levelTwoCards.get(0).getMeta().getLevel());
  }

  @Test
  void levelThreeCards() {
    aBoard.init();
    List<DevelopmentCard> levelThreeCards = aBoard.getLevelThree();
    assertEquals(5, levelThreeCards.size());
    assertEquals(3, levelThreeCards.get(0).getMeta().getLevel());
  }

//  @Test
//  public void testDealCardLevelOne() throws IllegalMoveException {
//    List<DevelopmentCard> Deck = new ArrayList<>();
//    Deck.add(L1C1);
//    Deck.add(L1C2);
//    Deck.add(L1C3);
//    Deck.add(L1C4);
//    Deck.add(L1C5);
//    GameBoard aBoard = new GameBoard(Deck, aGems, null);
//    aBoard.init();
//    List<DevelopmentCard> l1Cards = aBoard.levelOne;
//    List<DevelopmentCard> faceUpCards = aBoard.getFaceUpCards(l1Cards);
//    assertEquals(4,aBoard.getFaceUpCards(l1Cards).size());
//    assertEquals(1,aBoard.getHiddenCards(l1Cards).size());
    //TODO: Mysterious error when trying to change Card state.

//    faceUpCards.get(0).changeState(CardState.PURCHASED);
//    assertEquals(3,aBoard.filterCards(l1Cards).size());
//    assertEquals(1,aBoard.getHiddenCards(l1Cards).size());
//    aBoard.dealCard((short)1);
//    assertEquals(4,aBoard.filterCards(l1Cards).size());
//    assertEquals(0,aBoard.getHiddenCards(l1Cards).size());


//  }

//  @Test
//  public void testDealCardLevelTwo() throws IllegalMoveException {
//    List<DevelopmentCard> Deck = new ArrayList<>();
//    Deck.add(L2C1);
//    Deck.add(L2C2);
//    Deck.add(L2C3);
//    Deck.add(L2C4);
//    Deck.add(L2C5);
//    GameBoard aBoard = new GameBoard(Deck, aGems, null);
//    aBoard.init();
//    List<DevelopmentCard> l2Cards = aBoard.levelTwo;
//    List<DevelopmentCard> faceUpCards = aBoard.getFaceUpCards(l2Cards);
//    assertEquals(4,aBoard.getFaceUpCards(l2Cards).size());
//    assertEquals(1,aBoard.getHiddenCards(l2Cards).size());
    //TODO: Mysterious error when trying to change Card state.
    /*
    faceUpCards.get(0).changeState(CardState.PURCHASED);
    assertEquals(3,aBoard.filterCards(l2Cards).size());
    assertEquals(1,aBoard.getHiddenCards(l2Cards).size());
    aBoard.dealCard((short)2);
    assertEquals(4,aBoard.filterCards(l2Cards).size());
    assertEquals(0,aBoard.getHiddenCards(l2Cards).size());

     */
//  }
/*
  @Test
  public void testDealCardLevelThree() throws IllegalMoveException {
    List<DevelopmentCard> Deck = new ArrayList<>();
    Deck.add(L3C1);
    Deck.add(L3C2);
    Deck.add(L3C3);
    Deck.add(L3C4);
    Deck.add(L3C5);
    GameBoard aBoard = new GameBoard(Deck, aGems, null);
    aBoard.init();
    List<DevelopmentCard> l3Cards = aBoard.levelThree;
    List<DevelopmentCard> faceUpCards = aBoard.filterCards(l3Cards);
    assertEquals(4,aBoard.filterCards(l3Cards).size());
    assertEquals(1,aBoard.getHiddenCards(l3Cards).size());
    faceUpCards.get(0).changeState(CardState.PURCHASED);
    assertEquals(3,aBoard.filterCards(l3Cards).size());
    assertEquals(1,aBoard.getHiddenCards(l3Cards).size());
    aBoard.dealCard((short)3);
    assertEquals(4,aBoard.filterCards(l3Cards).size());
    assertEquals(0,aBoard.getHiddenCards(l3Cards).size());
  }

 */

  @Test
  void testGetCard() throws GameBoardException, GameBoardException {
    aBoard.init();
    Card card = aBoard.getCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                       CardType.DEVELOPMENT));
    assertEquals(1, card.getMeta().getId());
  }
  //TODO:
/*
  @Test
  void checkIsTurn() {
    assertThrows(IllegalMoveException.class, () -> aBoard.checkIsTurn("bac"));
  }
*/

  @Test
  void testGetAvailableGemType() {
    gems = new HashMap<>();
    gems.put(GemType.RUBY, 5);
    assertEquals(5, aBoard.getAvailableGemType(GemType.RUBY));
    assertEquals(5, aBoard.getAvailableGemType(GemType.SAPPHIRE));
  }

  @Test
  void testInit() {
    GameBoard initializedBoard = aBoard.init();
    assertNotNull(initializedBoard);
  }

  @Test
  public void testGetAvailableCards() {
    List<Short> availableCards = aBoard.getAvailableCards(Deck);
    assertTrue(availableCards.isEmpty());
  }

  @Test
  void testDealCard() {
    aBoard.init();
    aBoard.dealCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT));
    aBoard.dealCard(new DevelopmentCardMeta((short) 2, (short) 2, CardType.DEVELOPMENT));
    aBoard.dealCard(new DevelopmentCardMeta((short) 3, (short) 3, CardType.DEVELOPMENT));
  }

  @Test
  public void testGetFaceUpCards() throws InvalidCardType {
    List<DevelopmentCard> faceUpCards = aBoard.getFaceUpCards(Deck);
    assertTrue(faceUpCards.isEmpty());

    DevelopmentCardMeta meta = new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT);
    Bonus bonus = Bonus.RUBY;
    int prestigePoints = 3;
    Map<GemType, Integer> price = new HashMap<>();
    Card card = new DevelopmentCard(meta, bonus, prestigePoints, price);
    Deck.add((DevelopmentCard) card);

    faceUpCards = GameBoard.getFaceUpCards(Deck);
    assertEquals(0, faceUpCards.size());
  }

  @Test
  void testGetCard1() throws GameBoardException, InvalidCardType {
    DevelopmentCardMeta meta = new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT);
    Bonus bonus = Bonus.RUBY;
    int prestigePoints = 3;
    Map<GemType, Integer> price = new HashMap<>();
    Card card = new DevelopmentCard(meta, bonus, prestigePoints, price);
    Deck = new ArrayList<>();
    Deck.add((DevelopmentCard) card);

    Card retrievedCard = aBoard.getCard(meta);
    assertNotNull(retrievedCard);
  }

  @Test
  void testGetType() {
    aBoard.init();
    assertEquals(GameType.BASE, aBoard.getType());
  }

}