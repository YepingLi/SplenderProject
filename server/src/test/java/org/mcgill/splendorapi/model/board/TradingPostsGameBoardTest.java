package org.mcgill.splendorapi.model.board;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.TradingPost;
import org.mcgill.splendorapi.model.card.CardMeta;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;


class TradingPostsGameBoardTest {


  private List<DevelopmentCard> Deck;
  private Map<GemType, Integer> gems;
  private List<NobleCard> nobles;
  private List<TradingPost> tradingPosts;
  private TradingPostsGameBoard tradingPostsGameBoard;
  NobleCard n1;
  NobleCard n2;
  NobleCard n3;
  NobleCard n4;
  DevelopmentCard L1C1;
  DevelopmentCard L1C2;
  DevelopmentCard L1C3;
  DevelopmentCard L1C4;
  DevelopmentCard L1C5;
  DevelopmentCard OL1C1;
  DevelopmentCard OL1C2;
  DevelopmentCard OL1C3;
  DevelopmentCard OL1C4;
  DevelopmentCard OL2C1;
  DevelopmentCard OL2C2;
  DevelopmentCard OL2C3;
  DevelopmentCard OL2C4;
  DevelopmentCard OL3C1;
  DevelopmentCard OL3C2;
  DevelopmentCard OL3C3;
  DevelopmentCard OL3C4;


  @BeforeEach
  void setUp() throws InvalidCardType {
    Deck = new ArrayList<>();
    gems = new HashMap<>();
    gems.put(GemType.DIAMOND, 7);
    gems.put(GemType.SAPPHIRE, 7);
    gems.put(GemType.EMERALD, 7);
    gems.put(GemType.RUBY, 7);
    gems.put(GemType.ONYX, 7);
    nobles = new ArrayList<>();
    tradingPosts = Arrays.asList(new TradingPost[] {}); // populate with test data
    n1 = new NobleCard(new CardMeta((short) 1, CardType.NOBLE), 1, 1, new HashMap<>());
    n2 = new NobleCard(new CardMeta((short) 2, CardType.NOBLE), 2, 2, new HashMap<>());
    n3 = new NobleCard(new CardMeta((short) 3, CardType.NOBLE), 3, 3, new HashMap<>());
    n4 = new NobleCard(new CardMeta((short) 4, CardType.NOBLE), 4, 4, new HashMap<>());
    nobles.add(n1);
    nobles.add(n2);
    nobles.add(n3);
    nobles.add(n4);
    L1C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3, new HashMap<>());
    L1C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 1, CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3, new HashMap<>());
    L1C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 1, CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3, new HashMap<>());
    L1C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 1, CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3, new HashMap<>());
    L1C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 1, CardType.DEVELOPMENT),
                               Bonus.EMERALD, 3, new HashMap<>());
    OL1C1 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 1, (short) 1, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL1C2 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 2, (short) 1, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL1C3 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 3, (short) 1, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL1C4 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 4, (short) 1, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C1 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 1, (short) 2, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C2 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 2, (short) 2, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C3 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 3, (short) 2, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL2C4 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 4, (short) 2, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C1 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 1, (short) 3, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C2 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 2, (short) 3, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C3 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 3, (short) 3, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    OL3C4 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 4, (short) 3, CardType.ORIENT)),
                                Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE);
    Deck.add(L1C1);
    Deck.add(L1C2);
    Deck.add(L1C3);
    Deck.add(L1C4);
    Deck.add(L1C5);
    Deck.add(OL1C1);
    Deck.add(OL1C2);
    Deck.add(OL1C3);
    Deck.add(OL1C4);
    Deck.add(OL2C1);
    Deck.add(OL2C2);
    Deck.add(OL2C3);
    Deck.add(OL2C4);
    Deck.add(OL3C1);
    Deck.add(OL3C2);
    Deck.add(OL3C3);
    Deck.add(OL3C4);
    Deck.addAll(
      List.of(
        new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 3, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 4, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 3, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 4, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 2, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 4, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 3, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 4, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 2, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 4, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 3, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 4, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 2, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 4, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 1, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 4, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 2, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 3, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 3, new HashMap<>()),
        new DevelopmentCard(new DevelopmentCardMeta((short) 6, (short) 1, CardType.DEVELOPMENT),
                            Bonus.EMERALD, 3, new HashMap<>())));
    tradingPostsGameBoard = TradingPostsGameBoard.builder()
                                                 .tradingPosts(tradingPosts)
                                                 .deck(Deck)
                                                 .gems(gems)
                                                 .nobles(nobles)
                                                 .build();

  }


  @Test
  void testConstructor() {
    assertNotNull(tradingPostsGameBoard);
    assertEquals(Deck, tradingPostsGameBoard.getDeck());
    assertEquals(gems, tradingPostsGameBoard.getGems());
    assertEquals(nobles, tradingPostsGameBoard.getNobles());
    assertEquals(tradingPosts, tradingPostsGameBoard.getTradingPosts());
  }

  @Test
  void testGetType() {
    tradingPostsGameBoard.init();
    assertEquals(GameType.TRADING_POSTS, tradingPostsGameBoard.getType());
  }
}

