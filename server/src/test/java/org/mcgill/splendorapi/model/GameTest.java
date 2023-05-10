package org.mcgill.splendorapi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.api.assetloader.ProducerService;
import org.mcgill.splendorapi.model.board.CityGameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.board.TradingPostsOrientBoard;
import org.mcgill.splendorapi.model.card.CardMeta;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.card.OrientPower;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameTest {
  ArrayList<Player> players = new ArrayList<>();
  private List<DevelopmentCard> Deck;
  private Map<GemType, Integer> gems;
  private List<City> cities;
  private List<NobleCard> nobles;
  private CityGameBoard cityGameBoard;
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

  City c1;
  City c2;
  City c3;
  City c4;
  @BeforeEach
  void setUp() throws InvalidCardType {
    Deck = new ArrayList<>();
    gems = new HashMap<>();
    cities = new ArrayList<>();
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
    c1 = new City(new CardMeta((short) 1, CardType.CITY), 2, new HashMap<>(), 2);
    c2 = new City(new CardMeta((short) 2, CardType.CITY), 2, new HashMap<>(), 2);
    c3 = new City(new CardMeta((short) 3, CardType.CITY), 2, new HashMap<>(), 2);
    c4 = new City(new CardMeta((short) 4, CardType.CITY), 2, new HashMap<>(), 2);
    cities.add(c1);
    cities.add(c2);
    cities.add(c3);
    cities.add(c4);
    gems.put(GemType.RUBY, 5);
    gems.put(GemType.DIAMOND, 5);
    gems.put(GemType.SAPPHIRE, 5);
    gems.put(GemType.SAPPHIRE, 5);
    cityGameBoard = CityGameBoard.builder().deck(Deck).gems(gems).nobles(nobles).cities(cities)
                                 .build();
  }


  public GameTest() {
  }

  private List<DevelopmentCard> produceCard() {
    return IntStream.range(0, 21).mapToObj(i -> {
      int level =  (i % 3)+1;
      try {
        return new DevelopmentCard(
          new DevelopmentCardMeta((short) i, (short) level, CardType.DEVELOPMENT),
          Bonus.EMERALD,
          0,
          Map.of());
      } catch (InvalidCardType e) {
        return null;
      }
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }
  private List<DevelopmentCard> produceOrientCards() {
    return IntStream.range(0, 21).mapToObj(i -> {
      int level =  (i % 3)+1;
      try {
        return new OrientDevelopmentCard(
          new DevelopmentCardMeta((short) i, (short) level, CardType.ORIENT),
          Bonus.EMERALD,
          OrientPower.PAIRING,
          0,
          Map.of(),
        1, false, CardState.FREE);
      } catch (InvalidCardType e) {
        return null;
      }
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }
  private List<NobleCard> produceNobles() {
    return IntStream.range(0, 9).mapToObj(i -> {
      try {
        return new NobleCard(
          new CardMeta((short) i, CardType.NOBLE),
          -1,
          0,
          Map.of());
      } catch (InvalidCardType e) {
        return null;
      }
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }

  private List<City> produceCities() throws InvalidCardType {
    CardMeta meta = new CardMeta((short) 1, CardType.CITY);
    int prestigePoints = 5;
    int sameRequirement = 2;
    City city;
    city = new City(meta, prestigePoints, new HashMap<>(), sameRequirement);
    List<City> tempCities = new ArrayList<>();
    tempCities.add(city);
    return tempCities;
  }

  private List<TradingPost> produceTradingPosts() {
    return IntStream.range(0, 5).mapToObj(i -> {
        return new TradingPost(
          new CardMeta((short) i, CardType.NOBLE),
          PowerType.DOUBLE_GOLD,
          new HashMap<>());
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }

  @Test
  public void testAddGems(@Mock ProducerService loader) throws IOException, InvalidCardType {
//    players.add(new Player.PlayerBuilder().name("p1").build());
//    players.add(new Player.PlayerBuilder().name("p2").build());
//    players.add(new Player.PlayerBuilder().name("p3").build());
//
//    CardMeta meta = new CardMeta((short) 1, CardType.CITY);
//    int prestigePoints = 5;
//    int sameRequirement = 2;
//    City city = new City(meta, prestigePoints, new HashMap<>(), sameRequirement);
//
//    Game game1 = Game.builder().players(players).board(cityGameBoard).type(GameType.CITIES_ORIENT). build();
//    when(loader.getNobleCards()).thenAnswer((i) -> produceNobles());
//    when(loader.getCities()).thenAnswer((i) -> produceCities());
//
//    game1.init(loader);

//    assert(game1.getBoard().getGems().get(GemType.GOLD).equals(5));
//    assert(game1.getBoard().getGems().get(GemType.DIAMOND).equals(5));
//    assert(game1.getBoard().getGems().get(GemType.EMERALD).equals(5));
//    assert(game1.getBoard().getGems().get(GemType.ONYX).equals(5));
//    assert(game1.getBoard().getGems().get(GemType.SAPPHIRE).equals(5));
//    assert(game1.getBoard().getGems().get(GemType.RUBY).equals(5));
//
//    players.add(new Player.PlayerBuilder().name("p4").build());
//    Game game2 = Game.builder().board(cityGameBoard).players(players).type(GameType.CITIES_ORIENT).build();
//    game2.init(loader);
//    assert(game2.getBoard().getGems().get(GemType.GOLD).equals(5));
//    assert(game2.getBoard().getGems().get(GemType.DIAMOND).equals(7));
//    assert(game2.getBoard().getGems().get(GemType.EMERALD).equals(7));
//    assert(game2.getBoard().getGems().get(GemType.ONYX).equals(7));
//    assert(game2.getBoard().getGems().get(GemType.SAPPHIRE).equals(7));
//    assert(game2.getBoard().getGems().get(GemType.RUBY).equals(7));
  }

  @Test
  public void testChooseNoble(@Mock ProducerService loader) throws IOException {
    Game game = Game.builder().build();
    when(loader.getNobleCards()).thenAnswer((i) -> produceNobles());
    List<NobleCard> chosenNobles = game.chooseNobles(4, loader.getNobleCards());
    assertEquals(4, chosenNobles.size());
    assert(chosenNobles.get(0).getPosition().equals(0));
    assert(chosenNobles.get(1).getPosition().equals(1));
  }

  @Test
  public void testInitBase(@Mock ProducerService loader) throws IOException {
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    Game game = Game.builder().type(GameType.BASE).players(players).build();
    when(loader.getNobleCards()).thenAnswer((i) -> produceNobles());
    when(loader.getCards()).thenAnswer((i) -> produceCard());
    game.init(loader);
    assertEquals (7,game.getBoard().getLevelOne().size());
    assertEquals (7, game.getBoard().getLevelTwo().size());
    assertEquals (7,game.getBoard().getLevelThree().size());
    assertEquals (players.size() + 1, game.getBoard().getNobles().size());
  }

  @Test
  public void testInitOrientGame(@Mock ProducerService loader) throws IOException {
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    Game game = Game.builder().type(GameType.ORIENT).players(players).build();
    when(loader.getNobleCards()).thenAnswer((i) -> produceNobles());
    when(loader.getCards()).thenAnswer((i) -> produceCard());
    when(loader.getOrientCards()).thenAnswer((i) -> produceOrientCards());
    game.init(loader);
    OrientGameBoard board = (OrientGameBoard) game.getBoard();
    assertEquals (7,board.getLevelOne().size());
    assertEquals (7,board.getLevelTwo().size());
    assertEquals (7,board.getLevelThree().size());
    assertEquals (7,board.getL1OrientCards().size());
    assertEquals (7, board.getL2OrientCards().size());
    assertEquals (7,board.getL3OrientCards().size());
    assertEquals (players.size()+1,game.getBoard().getNobles().size());
  }


  @Test
  public void testInitOrientTradingPosts(@Mock ProducerService loader) throws IOException {
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    Game game = Game.builder().type(GameType.ORIENT_TRADING_POST).players(players).build();
    when(loader.getNobleCards()).thenAnswer((i) -> produceNobles());
    when(loader.getCards()).thenAnswer((i) -> produceCard());
    when(loader.getOrientCards()).thenAnswer((i) -> produceOrientCards());
    when(loader.getTradingPosts()).thenAnswer((i) -> produceTradingPosts());
    game.init(loader);
    TradingPostsOrientBoard board = (TradingPostsOrientBoard) game.getBoard();
    assertEquals (7,board.getLevelOne().size());
    assertEquals (7,board.getLevelTwo().size());
    assertEquals (7,board.getLevelThree().size());
    assertEquals (7,board.getL1OrientCards().size());
    assertEquals (7, board.getL2OrientCards().size());
    assertEquals (7,board.getL3OrientCards().size());
    assertEquals (players.size()+1,game.getBoard().getNobles().size());
    assertEquals(5,board.getTradingPosts().size());
  }

  @Test
  public void initPlayerColor(@Mock ProducerService loader) throws IOException {
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    when(loader.getNobleCards()).thenAnswer((i) -> produceNobles());
    when(loader.getCards()).thenAnswer((i) -> produceCard());
    Game game = Game.builder().type(GameType.BASE).players(players).build();
    game.init(loader);
    assert (game.getPlayers().get(0).getColour().equals(Color.RED));
    assert (game.getPlayers().get(1).getColour().equals(Color.YELLOW));
    assert (game.getPlayers().get(2).getColour().equals(Color.GREEN));

  }

  @Test
  public void initPlayerOrder(@Mock ProducerService loader) throws IOException {
    players.add(new Player.PlayerBuilder().name("first").build());
    players.add(new Player.PlayerBuilder().build());
    players.add(new Player.PlayerBuilder().build());
    when(loader.getNobleCards()).thenAnswer((i) -> produceNobles());
    when(loader.getCards()).thenAnswer((i) -> produceCard());
    Game game = Game.builder().type(GameType.BASE).players(players).build();
    game.init(loader);
    assert (game.getCurPlayer().getName().equals("first"));
  }

}


