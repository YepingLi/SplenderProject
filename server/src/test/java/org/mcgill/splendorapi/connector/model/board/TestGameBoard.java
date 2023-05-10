package org.mcgill.splendorapi.connector.model.board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.card.*;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGameBoard {

  private final List<DevelopmentCard> deck = new ArrayList<>();
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
  NobleCard NC1;
  NobleCard NC2;

  protected final Map<GemType, Integer> gems = new HashMap<>();

  protected final List<NobleCard> nobles = new ArrayList<>();
  @BeforeEach
  public void setUp() throws InvalidCardType  {
    L1C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L1C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 1,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L1C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 1,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L1C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 1,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L1C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 1,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L2C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 2,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L2C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 2,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L2C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 2,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L2C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 2,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L2C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 2,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L3C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 3,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L3C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 3,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L3C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 3,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L3C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 3,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    L3C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 3,
                                                       CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3,
                               new HashMap<>());
    NC1 = new NobleCard(new CardMeta((short)1, CardType.NOBLE), 5, 5, new HashMap<>());
    NC2 = new NobleCard(new CardMeta((short)2,CardType.NOBLE),5, 5, new HashMap<>());
    deck.add(L1C1);
    deck.add(L1C2);
    deck.add(L1C3);
    deck.add(L1C4);
    deck.add(L1C5);
    deck.add(L2C1);
    deck.add(L2C2);
    deck.add(L2C3);
    deck.add(L2C4);
    deck.add(L2C5);
    deck.add(L3C1);
    deck.add(L3C2);
    deck.add(L3C3);
    deck.add(L3C4);
    deck.add(L3C5);
    nobles.add(NC1);
    nobles.add(NC2);
  }
  @Test
  public void testGameBoard(){
    GameBoard gameBoard = GameBoard.builder().gems(gems).nobles(nobles).deck(deck).build();
    assertEquals(15, gameBoard.getDeck().size());
    assertEquals(2, gameBoard.getNobles().size());
  }
  @Test
  public void testInit(){
    GameBoard gameBoard =  GameBoard.builder().gems(gems).nobles(nobles).deck(deck).build();
    gameBoard = gameBoard.init();
    assertEquals(5, gameBoard.getLevelOne().size());
    assertEquals(5, gameBoard.getLevelTwo().size());
    assertEquals(5, gameBoard.getLevelThree().size());
    assertEquals(2, gameBoard.getNobles().size());
  }

}
