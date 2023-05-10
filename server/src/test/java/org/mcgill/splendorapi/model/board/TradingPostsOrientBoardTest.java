package org.mcgill.splendorapi.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.PowerType;
import org.mcgill.splendorapi.model.TradingPost;
import org.mcgill.splendorapi.model.card.CardMeta;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

public class TradingPostsOrientBoardTest {

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

  TradingPost t1;
  TradingPost t2;
  TradingPost t3;
  TradingPost t4;

  @BeforeEach
  void setUp() throws InvalidCardType {
    Deck = new ArrayList<>();
    gems = new HashMap<>();
    tradingPosts = new ArrayList<>();
    nobles = new ArrayList<>();
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
    L2C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 2, CardType.DEVELOPMENT),
                               Bonus.SAPPHIRE, 3, new HashMap<>());
    L2C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 2, CardType.DEVELOPMENT),
                               Bonus.SAPPHIRE, 3, new HashMap<>());
    L2C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 2, CardType.DEVELOPMENT),
                               Bonus.SAPPHIRE, 3, new HashMap<>());
    L2C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 2, CardType.DEVELOPMENT),
                               Bonus.SAPPHIRE, 3, new HashMap<>());
    L2C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 2, CardType.DEVELOPMENT),
                               Bonus.SAPPHIRE, 3, new HashMap<>());
    L3C1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 3, CardType.DEVELOPMENT),
                               Bonus.RUBY, 3, new HashMap<>());
    L3C2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 3, CardType.DEVELOPMENT),
                               Bonus.RUBY, 3, new HashMap<>());
    L3C3 = new DevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 3, CardType.DEVELOPMENT),
                               Bonus.RUBY, 3, new HashMap<>());
    L3C4 = new DevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 3, CardType.DEVELOPMENT),
                               Bonus.RUBY, 3, new HashMap<>());
    L3C5 = new DevelopmentCard(new DevelopmentCardMeta((short) 5, (short) 3, CardType.DEVELOPMENT),
                               Bonus.RUBY, 3, new HashMap<>());
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
    OL1C5 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 5, (short) 1, CardType.ORIENT)),
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
    OL2C5 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 5, (short) 2, CardType.ORIENT)),
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
    OL3C5 =
      new OrientDevelopmentCard((new DevelopmentCardMeta((short) 5, (short) 3, CardType.ORIENT)),
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
    t1 = new TradingPost(new CardMeta((short) 1, CardType.CITY), PowerType.FREE_POINTS,
                         new HashMap<>());
    t2 = new TradingPost(new CardMeta((short) 2, CardType.CITY), PowerType.TWO_AND_ONE,
                         new HashMap<>());
    t3 = new TradingPost(new CardMeta((short) 3, CardType.CITY), PowerType.POINTS_PER_MARKER,
                         new HashMap<>());
    t4 = new TradingPost(new CardMeta((short) 4, CardType.CITY), PowerType.FREE_GEM_ON_PURCHASE,
                         new HashMap<>());
    tradingPosts.add(t1);
    tradingPosts.add(t2);
    tradingPosts.add(t3);
    tradingPosts.add(t4);
    gems.put(GemType.RUBY, 5);
    gems.put(GemType.DIAMOND, 5);
    gems.put(GemType.SAPPHIRE, 5);
    gems.put(GemType.EMERALD, 5);
    tradingPostsGameBoard = (TradingPostsGameBoard) TradingPostsGameBoard.builder()
                                                 .tradingPosts(tradingPosts)
                                                 .deck(Deck)
                                                 .gems(gems)
                                                 .nobles(nobles)
                                                 .build().init();
  }

  @Test
  public void testGetType() {
    GameType result = tradingPostsGameBoard.getType();

    assertEquals(GameType.TRADING_POSTS, result);
  }


}
