package org.mcgill.splendorapi.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.mcgill.splendorapi.api.assetloader.ProducerService;
import org.mcgill.splendorapi.model.board.CityGameBoard;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.board.TradingPostsGameBoard;
import org.mcgill.splendorapi.model.board.TradingPostsOrientBoard;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * Game object.
 */
@Jacksonized
@Builder
@Getter
public class Game {
  @Setter
  private List<Player> players;
  @Setter
  private String gameName;
  private final String creator;
  private final String gameServer;
  @JsonSerialize(using = GameType.GameTypeSerializer.class)
  private final GameType type;
  // Only two non-final fields
  private Player curPlayer;
  private GameBoard board;
  // Final object which is started or not (but cannot be changed once it has been started).
  private boolean launched;
  private boolean terminating = false;
  private boolean over = false;

  /**
   * Check if the user has access to this game.
   *
   * @param username The username trying to load the game
   * @return The game
   * @throws IllegalAccessException If the user does not have access to this game.
   */
  public Game checkUserRights(String username) throws IllegalAccessException {
    if (players.stream().filter(player -> player.getName().equals(username)).count() != 1) {
      throw new IllegalAccessException(
        "You do not have sufficient rights to connect with this game");
    }
    return this;
  }

  public void setIsTerminating() {
    this.terminating = true;
  }

  public void setIsOver() {
    this.over = true;
  }


  /**
   * Start the game.
   *
   * @param theBoard The board attached to the game
   */

  public void startGame(GameBoard theBoard) {
    if (launched) {
      throw new IllegalStateException("LauncherInfo cannot be launched and making"
                                        + " a new game with it");
    }

    board = theBoard;
  }

  private GameBoard getTradingPostOrExtended(ProducerService service,
                                             List<NobleCard> chosenNobles,
                                             boolean tradingAndOrient) {
    List<DevelopmentCard> deck = service.getCards();
    if (tradingAndOrient) {
      deck.addAll(service.getOrientCards());
      return TradingPostsOrientBoard.builder()
                                    .deck(deck)
                                    .gems(addGems(players.size()))
                                    .nobles(chosenNobles)
                                    .tradingPosts(service.getTradingPosts())
                                    .build()
                                    .init();
    }
    deck.addAll(service.getOrientCards());
    // Since only extension of tradingPost is tradingPost+Orient, instead of tradingPost+Base
    return TradingPostsGameBoard.builder()
                                .deck(deck)
                                .gems(addGems(players.size()))
                                .nobles(chosenNobles)
                                .tradingPosts(service.getTradingPosts())
                                .build()
                                .init();
  }

  private GameBoard getCityOrientExtended(ProducerService service,
                                          List<City> chooseCities, List<NobleCard> chosenNobles) {
    List<DevelopmentCard> deck = service.getCards();
    deck.addAll(service.getOrientCards());
    return CityGameBoard.builder()
                        .deck(deck)
                        .gems(addGems(players.size()))
                        .nobles(chosenNobles).cities(chooseCities).build()
                        .init();
  }

  /**
   * Initializes the Current Player, Player Colour and Game Board.
   *
   * @param producerService All Cards, nobles and TradingPosts,
   */
  public void init(ProducerService producerService) {
    this.curPlayer = this.players.get(0);
    initializePlayer(this.players);
    List<NobleCard> chosenNobles = chooseNobles(players.size() + 1,
                                                producerService.getNobleCards());
    if (type.equals(GameType.BASE)) {
      board = GameBoard.builder()
                       .deck(producerService.getCards())
                       .nobles(chosenNobles)
                       .gems(addGems(players.size()))
                       .build()
                       .init();
    } else if (type.equals(GameType.ORIENT)) {
      List<DevelopmentCard> deck = producerService.getCards();
      deck.addAll(producerService.getOrientCards());
      board = OrientGameBoard.builder()
                             .deck(deck)
                             .nobles(chosenNobles)
                             .gems(addGems(players.size()))
                             .build()
                             .init();
    } else if (type.equals(GameType.ORIENT_TRADING_POST) || type.equals(GameType.TRADING_POSTS)) {
      board = getTradingPostOrExtended(producerService, chosenNobles,
                                       type.equals(GameType.ORIENT_TRADING_POST));
    } else if (type.equals(GameType.CITIES_ORIENT)) { //TODO: add Cities.
      List<City> chosenCities = chooseCities(producerService.getCities());
      board = getCityOrientExtended(producerService, chosenCities, chosenNobles);
    }

  }

  private void initializePlayer(List<Player> players) {

    int index = 0;
    for (Player player : players) {
      Map<GemType, Integer> tokens = Arrays.stream(GemType.values())
                                           .collect(Collectors.toMap(Function.identity(),
                                                                     x -> 0));
      Map<Bonus, Integer> bonuses = Arrays.stream(Bonus.values())
                                          .collect(Collectors.toMap(Function.identity(),
                                                                    x -> 0));
      List<Color> colors = getColors();
      player.setGems(tokens);
      player.setBonuses(bonuses);
      player.setColor(colors.get(index));
      index++;
    }
  }

  private List<Color> getColors() {
    return Arrays.stream(Color.values()).collect(Collectors.toList());
  }

  /**
   * Selects a specified amount of nobles from the possibleNobles.
   *
   * @param numNobles num.
   * @param allNobles all.
   * @return selectedNobles.
   */
  public List<NobleCard> chooseNobles(int numNobles, List<NobleCard> allNobles) {
    Random rand = new Random();
    return IntStream.range(0, numNobles)
                    .mapToObj(index -> {
                      NobleCard noble = allNobles.remove(rand.nextInt(allNobles.size()));
                      noble.setPosition(index);
                      return noble;
                    })
                    .collect(Collectors.toList());
  }

  /**
   * Selects a specified amount of nobles from the possibleNobles.
   * numCities num must be 3 and no matter how many player attending the game
   *
   * @param allCities all.
   * @return selectedCities.
   */
  public List<City> chooseCities(List<City> allCities) {
    Random rand = new Random();
    return IntStream.range(0, 3)
                    .mapToObj(index -> {
                      City city = allCities.remove(rand.nextInt(allCities.size()));
                      city.setPosition(index);
                      return city;
                    })
                    .collect(Collectors.toList());
  }

  /**
   * Add the gems based on the number of players.
   *
   * @param numPlayers The number of players in the game
   * @return The map of gem type to number of gems
   */
  private Map<GemType, Integer> addGems(int numPlayers) {
    return Arrays.stream(GemType.values()).collect(Collectors.toMap(Function.identity(), type -> {
      if (type.equals(GemType.GOLD)) {
        return 5;
      }
      switch (numPlayers) {
        case 2:
          return 4;
        case 3:
          return 5;
        default:
          return 7;
      }
    }));
  }

  /**
   * Ensure it is the players turn during their move.
   *
   * @param user The user sending the move
   * @return The game board if it is their turn
   * @throws IllegalMoveException If they are trying to perform a move while it is not their turn
   */
  public Game checkIsTurn(String user) throws IllegalMoveException {
    assert user != null;
    if (!curPlayer.getName().equals(user)) {
      throw new IllegalMoveException();
    }
    return this;
  }

  //Updates the current player to the next player.  Always keeps the same order.

  /**
   * change the player of the game.
   */
  public void changePlayer() {
    int curIndex = this.players.indexOf(this.curPlayer);
    int newIndex = (curIndex + 1) % this.players.size();
    this.curPlayer = players.get(newIndex);
  }

  /**
   * Removes the specified player from the game.
   *
   * @param player The player to be removed
   */
  public void kickOut(Player player) {
    players.remove(player);
  }
}
