package org.mcgill.splendorapi.api.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.api.game.dto.BaseGameBoardDto;
import org.mcgill.splendorapi.api.game.dto.GameDto;
import org.mcgill.splendorapi.api.game.dto.OrientGameBoardDto;
import org.mcgill.splendorapi.api.game.dto.TradingPostBoardDto;
import org.mcgill.splendorapi.api.game.dto.TradingPostOrientBoardDto;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.PowerType;
import org.mcgill.splendorapi.model.TradingPost;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.board.TradingPostsGameBoard;
import org.mcgill.splendorapi.model.card.CardMeta;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.card.OrientPower;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

import static org.junit.Assert.assertNull;

public class TestGameMapper {
  ArrayList<Player> players = new ArrayList<>();
  List<NobleCard> Nobles = new ArrayList<>();
  GameMapper mapper = new GameMapper();
  private List<DevelopmentCard> Deck;
  private Map<GemType, Integer> gems;
  private List<NobleCard> nobles;
  private List<TradingPost> tradingPosts = new ArrayList<>();
  private OrientGameBoard orientGameBoard;
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
    t1 = new TradingPost(new CardMeta((short) 1, CardType.CITY), PowerType.FREE_POINTS, new HashMap<>());
    t2 = new TradingPost(new CardMeta((short) 2, CardType.CITY), PowerType.TWO_AND_ONE, new HashMap<>());
    t3 = new TradingPost(new CardMeta((short) 3, CardType.CITY), PowerType.POINTS_PER_MARKER, new HashMap<>());
    t4 = new TradingPost(new CardMeta((short) 4, CardType.CITY), PowerType.FREE_GEM_ON_PURCHASE, new HashMap<>());
    tradingPosts.add(t1);
    tradingPosts.add(t2);
    tradingPosts.add(t3);
    tradingPosts.add(t4);
    gems.put(GemType.RUBY, 5);
    gems.put(GemType.DIAMOND, 5);
    gems.put(GemType.SAPPHIRE, 5);
    gems.put(GemType.SAPPHIRE, 5);
    orientGameBoard = OrientGameBoard.builder().gems(gems).nobles(nobles).deck(Deck).build();
  }


  @Test
  public void testMapBaseGameBoard(){
    Game baseBoard = Game.builder()
                         .type(GameType.BASE)
                         .board(GameBoard.builder()
                                          .deck(Deck)
                                          .nobles(Nobles)
                                          .gems(gems)
                                          .build())
                         .build();
    baseBoard.getBoard().init();
    BaseGameBoardDto boardDto = mapper.map(baseBoard).getBoard();
    assert(boardDto.getRemainingLevelOneCards().equals(1));
    assert(boardDto.getRemainingLevelTwoCards().equals(1));
    assert(boardDto.getRemainingLevelTwoCards().equals(1));
    assert(boardDto.getNobles().size()==Nobles.size());
  }

  @Test
  public void testMapOrientBoard() throws InvalidCardType {
    Game game = Game.builder()
                    .type(GameType.ORIENT)
                    .board(OrientGameBoard.builder().gems(gems).nobles(Nobles).deck(Deck).build())
                    .build();
    game.getBoard().init();
    OrientGameBoardDto orientDto = (OrientGameBoardDto) mapper.map(game).getBoard();
    assert(orientDto.getRemainingLevelOneCards().equals(1));
    assert(orientDto.getRemainingLevelTwoCards().equals(1));
    assert(orientDto.getRemainingLevelThreeCards().equals(1));
    assert(orientDto.getFaceUpCards().size()==12);
    assert(orientDto.getNobles().size()==Nobles.size());
  }

  @Test
  public void testMapOrientTradingPostBoard() throws InvalidCardType {
    List<TradingPost> tradingPosts = new ArrayList<>();
    TradingPost post1 = new TradingPost(new CardMeta((short) 1, CardType.TRADING_POST), PowerType.DOUBLE_GOLD, new HashMap<>());
    TradingPost post2 = new TradingPost(new CardMeta((short) 2, CardType.TRADING_POST), PowerType.FREE_GEM_ON_PURCHASE, new HashMap<>());
    TradingPost post3 = new TradingPost(new CardMeta((short) 3, CardType.TRADING_POST), PowerType.FREE_POINTS, new HashMap<>());
    TradingPost post4 = new TradingPost(new CardMeta((short) 4, CardType.TRADING_POST), PowerType.TWO_AND_ONE, new HashMap<>());
    TradingPost post5 = new TradingPost(new CardMeta((short) 5, CardType.TRADING_POST), PowerType.POINTS_PER_MARKER, new HashMap<>());
    tradingPosts.add(post1);
    tradingPosts.add(post2);
    tradingPosts.add(post3);
    tradingPosts.add(post4);
    tradingPosts.add(post5);
    OrientDevelopmentCard OC1 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),1, false, CardState.FREE);
    OrientDevelopmentCard OC2 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),2, false, CardState.FREE);
    OrientDevelopmentCard OC3 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),3, false, CardState.FREE);
    OrientDevelopmentCard OC4 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),4, false, CardState.FREE);
    Deck.add(OC1);
    Deck.add(OC2);
    Deck.add(OC3);
    Deck.add(OC4);
    Game game = Game.builder().type(GameType.TRADING_POSTS)
                    .board(TradingPostsGameBoard.builder().gems(gems)
                                                .nobles(Nobles)
                                                .deck(Deck).tradingPosts(tradingPosts).build())
                    .build();
    game.getBoard().init();
    TradingPostBoardDto tradingPostOrientDto = (TradingPostBoardDto) mapper.map(game).getBoard();
    assert(tradingPostOrientDto.getRemainingLevelOneCards().equals(1));
    assert(tradingPostOrientDto.getRemainingLevelTwoCards().equals(1));
    assert(tradingPostOrientDto.getRemainingLevelThreeCards().equals(1));
    assert(tradingPostOrientDto.getNobles().size()==Nobles.size());
    assert(tradingPostOrientDto.getTradingPosts().size()==5);
  }

  @Test
  public void testmapBaseGame(){
    GameBoard baseBoard = GameBoard.builder().deck(Deck).nobles(Nobles).gems(gems).build();
    baseBoard.init();
    Game game = Game.builder().board(baseBoard).players(players).gameName("testGame").creator("p1").type(
      GameType.BASE).build();
    GameDto gameDto = mapper.map(game);
    assert(gameDto.getGameName().equals("testGame"));
    assert(gameDto.getCreator().equals("p1"));
    assert(gameDto.getType().toString().equals("BASE"));
    assert(gameDto.getBoard().getRemainingLevelOneCards()==1);
    assert(gameDto.getBoard().getRemainingLevelTwoCards()==1);
    assert(gameDto.getBoard().getRemainingLevelThreeCards()==1);
    assert(gameDto.getBoard().getFaceUpCards().size()==12);
  }

  @Test
  public void testMapOrientGame() throws InvalidCardType {
    OrientDevelopmentCard OC1 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),1, false, CardState.FREE);
    OrientDevelopmentCard OC2 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),2, false, CardState.FREE);
    OrientDevelopmentCard OC3 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),3, false, CardState.FREE);
    OrientDevelopmentCard OC4 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),4, false, CardState.FREE);
    Deck.add(OC1);
    Deck.add(OC2);
    Deck.add(OC3);
    Deck.add(OC4);
    OrientGameBoard orientBoard = OrientGameBoard.builder().gems(gems).nobles(Nobles).deck(Deck).build();
    orientBoard.init();
    Game game = Game.builder()
                    .board(orientBoard)
                    .players(players)
                    .gameName("testGame")
                    .creator("p1")
                    .type(GameType.ORIENT).build();
    GameDto gameDto = mapper.map(game);
    assert(gameDto.getGameName().equals("testGame"));
    assert(gameDto.getCreator().equals("p1"));
    assert(gameDto.getBoard().getRemainingLevelOneCards()==1);
    assert(gameDto.getBoard().getRemainingLevelTwoCards()==1);
    assert(gameDto.getBoard().getRemainingLevelThreeCards()==1);
    assert(gameDto.getBoard().getFaceUpCards().size()==12);
  }

  @Test
  public void testMapTradingPostOrientGame() throws InvalidCardType {
    List<TradingPost> tradingPosts = new ArrayList<>();
    TradingPost post1 = new TradingPost(new CardMeta((short) 1, CardType.TRADING_POST), PowerType.DOUBLE_GOLD, new HashMap<>());
    TradingPost post2 = new TradingPost(new CardMeta((short) 2, CardType.TRADING_POST), PowerType.FREE_GEM_ON_PURCHASE, new HashMap<>());
    TradingPost post3 = new TradingPost(new CardMeta((short) 3, CardType.TRADING_POST), PowerType.FREE_POINTS, new HashMap<>());
    TradingPost post4 = new TradingPost(new CardMeta((short) 4, CardType.TRADING_POST), PowerType.TWO_AND_ONE, new HashMap<>());
    TradingPost post5 = new TradingPost(new CardMeta((short) 5, CardType.TRADING_POST), PowerType.POINTS_PER_MARKER, new HashMap<>());
    tradingPosts.add(post1);
    tradingPosts.add(post2);
    tradingPosts.add(post3);
    tradingPosts.add(post4);
    tradingPosts.add(post5);
    OrientDevelopmentCard OC1 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),1, false, CardState.FREE);
    OrientDevelopmentCard OC2 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),2, false, CardState.FREE);
    OrientDevelopmentCard OC3 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 3, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),3, false, CardState.FREE);
    OrientDevelopmentCard OC4 = new OrientDevelopmentCard(new DevelopmentCardMeta((short) 4, (short) 1,
                                                                                  CardType.ORIENT),
                                                          Bonus.EMERALD, OrientPower.PAIRING, 3,
                                                          new HashMap<>(),4, false, CardState.FREE);
    Deck.add(OC1);
    Deck.add(OC2);
    Deck.add(OC3);
    Deck.add(OC4);
    TradingPostsGameBoard
      tradingPostsOrientBoard = TradingPostsGameBoard.builder().gems(gems).nobles(Nobles)
                                                     .deck(Deck)
                                                     .tradingPosts(tradingPosts).build();
    tradingPostsOrientBoard.init();
    Game game = Game.builder().board(tradingPostsOrientBoard).players(players).gameName("testGame").creator("p1").type(
      GameType.TRADING_POSTS).build();
    GameDto gameDto = mapper.map(game);
    assert(gameDto.getGameName().equals("testGame"));
    assert(gameDto.getCreator().equals("p1"));
    assert(gameDto.getBoard().getRemainingLevelOneCards()==1);
    assert(gameDto.getBoard().getRemainingLevelTwoCards()==1);
    assert(gameDto.getBoard().getRemainingLevelThreeCards()==1);
    assert(gameDto.getBoard().getFaceUpCards().size()==12);
  }

  @Test
  void testMapTradingPostsGameBoard() {
    GameMapper gameMapper = new GameMapper();
    TradingPostsGameBoard board = TradingPostsGameBoard.builder()
                                                       .gems(gems)
                                                       .nobles(nobles)
                                                       .deck(Deck)
                                                       .tradingPosts(tradingPosts).build();
    BaseGameBoardDto result = gameMapper.map(board);
    assertNull(result);
  }
}
