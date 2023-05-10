package org.mcgill.splendorapi.api.move.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.mcgill.splendorapi.api.move.enums.Replacement;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.PowerType;
import org.mcgill.splendorapi.model.TradingPost;
import org.mcgill.splendorapi.model.board.CityGameBoard;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.board.TradingPostsGameBoard;
import org.mcgill.splendorapi.model.board.TradingPostsOrientBoard;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.card.OrientPower;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * L1, L2, L3 base | L2 double bonus | L2 reserve noble when there is no noble | L1 double gold.
 */
@Getter
public class BuyCardMove implements Move {
  private final DevelopmentCard card;

  @Setter
  private Payment payment;

  @Setter
  private NobleCard nobleChoice;

  @Setter
  private GemType freeGemOnPurchase;


  public void setFreeGemOnPurchase(GemType type) {
    this.freeGemOnPurchase = type;
  }
  /**
   * Constructor if the Card does not require further action from the user.
   *
   * @param card    to buy
   * @param payment how we pay
   */

  public BuyCardMove(DevelopmentCard card, Payment payment, NobleCard nobleChoice) {
    this.card = card;
    this.payment = payment;
  }

  public BuyCardMove(BuyCardMove move) {
    card = move.card;
    payment = move.payment;
  }

  /**
   * Constructor if the Card does not require further action from the user.
   *
   * @param card to buy
   */
  public BuyCardMove(DevelopmentCard card) {
    this(card, null, null);
  }

  @Override
  public BuyCardMove copy() {
    return new BuyCardMove(this);
  }

  /**
   * Performs the BuyCardMove.
   *
   * @param game the curGame
   * @throws GameBoardException   Incorrect GameBoard.
   * @throws IllegalMoveException Not a valid move.
   */
  @Override
  public void applyMove(Game game) throws GameBoardException, IllegalMoveException {
    Player player = game.getCurPlayer();
    applyMove(game, player);

    nobleCheck(player, game);
    postMoveChecks(player, game);
  }

  void applyMove(Game game, Player player) throws GameBoardException, IllegalMoveException {

    GameBoard board = game.getBoard();


    //return paid gems to the gem stash
    board.addGems(player.buyCard(card, payment));


    //remove from reserved card area
    if (card.getState().equals(CardState.RESERVED)) {
      player.getReservedCards().remove(card);
    } else {
      board.dealCard(card.getMeta());
    }

    card.changeState(CardState.PURCHASED);

    //Adds Orient Checks to the player.
    if (card.isOrientCard()) {
      OrientDevelopmentCard orientCard = (OrientDevelopmentCard) this.card;
      if (player.getBonuses().get(null) != null) {
        player.getBonuses().remove(null);
      }
      //3.11: If it's a double bonus, add the extra bonus.
      if (orientCard.getPower().equals(OrientPower.DOUBLE_BONUS)) {
        player.addBonus(orientCard.getBonus());
        //player.addBonus(orientCard.getBonus());
      } else if (orientCard.getPower().equals(OrientPower.DOUBLE_GOLD)) {
        //3.12: If the card is a double Gold card, add the double gold to the player.
        player.addGoldCard();

      } else if (Replacement.DISCARD.startsWith(orientCard.getPower().name())) {
        Bonus bonusToDiscard = Bonus.valueOf(Replacement.DISCARD.replace(orientCard.getPower()
                                                                                   .name()));
        player.removeBonus(bonusToDiscard);
        player.removeBonus(bonusToDiscard);
      }
    }
  }

  protected void nobleCheck(Player player, Game game) throws IllegalMoveException {
    if (nobleChoice != null) {
      player.addNoble(nobleChoice);
    } else {
      List<NobleCard> nobles = game.getBoard()
                                   .getNobles()
                                   .stream()
                                   .filter(noble -> game.getCurPlayer().validateNobleVisit(noble))
                                   .collect(Collectors.toList());
      if (nobles.isEmpty()) {
        return;
      }
      if (nobles.size() > 1) {
        throw new IllegalMoveException("Needed to choose a noble");
      }
      player.addNoble(nobles.get(0));
    }
  }

  protected void postMoveChecks(Player player, Game game)
      throws GameBoardException, IllegalMoveException {
    GameBoard board = game.getBoard();
    //9. Check if the player now meets the requirements for a TradingPost.
    if (game.getType().equals(GameType.TRADING_POSTS)) {
      TradingPostsGameBoard tradingPostOrientBoard = (TradingPostsGameBoard) board;
      for (TradingPost post : tradingPostOrientBoard.getTradingPosts()) {
        //If the Player meets the requirements for a NEW trading Post then add
        // their coat and the corresponding power.
        if (!(post.getCoats().contains(player.getColour()))
            && player.meetsTradingPostRequirements(post.getRequirements())) {
          post.addCoat(player.getColour());
          player.addPower(post.getPower());
          if (post.getPower().equals(PowerType.FREE_POINTS)) {
            player.addPrestigePoints(5);
          } else if (post.getPower().equals(PowerType.POINTS_PER_MARKER)) {
            player.addPrestigePoints(player.getPowers().size());
          } // If the Player Lost a previously acquired tradingPost by burning gems this turn.
        } else if ((post.getCoats().contains(player.getColour()))
            && !player.meetsTradingPostRequirements(post.getRequirements())) {
          post.removeCoat(player.getColour());
          player.removePower(post.getPower());
          if (post.getPower().equals(PowerType.FREE_POINTS)) {
            player.addPrestigePoints(-5);
          } else if (post.getPower().equals(PowerType.POINTS_PER_MARKER)) {
            player.addPrestigePoints(-1 * player.getPowers().size());
          }
        }
      }
    }

    if (game.getType().equals(GameType.CITIES_ORIENT)) {
      CityGameBoard cityBoard = (CityGameBoard) board;
      List<City> possibleCities = new ArrayList<>();
      for (City city : cityBoard.getCities()) {
        if (player.meetsCityRequirements(city)) {
          possibleCities.add(city);
        }
      }
      //TODO: TAKE INTO ACCOUNT THE CASE WHERE THE PLAYER IS ELIGIBLE FOR MULTIPLE CITIES
      if (possibleCities.size() >= 1) {
        player.addCity(possibleCities.get(0));
        possibleCities.get(0).changeState(CardState.PURCHASED);
        game.setIsTerminating();
      }
    }


    //11. Sets the game as terminating if pLayer has 15 Victory Points
    if (player.getPlayerPrestige() >= 15) {
      game.setIsTerminating();
    }
  }

  /**
   * remove bonus from player.
   *
   * @param bonus   bonus to remove
   * @param bonuses bonuses of player
   */
  public static void removeBonusHelper(Bonus bonus, Map<Bonus, Integer> bonuses) {
    bonuses.compute(bonus, (bonus1, value) -> {
      if (value == null) {
        return 0;
      }
      return value - 1;
    });
  }

  /**
   * Helper method to add bonus to player.
   *
   * @param bonus   bonus to be added
   * @param bonuses bonuses of player
   */
  public static void addBonusHelper(Bonus bonus, Map<Bonus, Integer> bonuses) {
    bonuses.compute(bonus, (bonus1, value) -> {
      if (value == null) {
        return 1;
      }
      return value + 1;
    });
  }

  /**
   * Simulate a move's effect on player's bonuses.
   *
   * @param player to perform the simulation on
   * @return amount of bonus of player at the end of the move
   */
  public Map<Bonus, Integer> simulateMove(Player player) {
    Map<Bonus, Integer> bonusCopy = Player.copyMap(player.getBonuses());

    //First add the bonus from purchased card.
    addBonusHelper(card.getBonus(), bonusCopy);

    if (card.isOrientCard()) {
      OrientDevelopmentCard orientCard = (OrientDevelopmentCard) card;
      if (orientCard.getPower().equals(OrientPower.DOUBLE_BONUS)) {
        addBonusHelper(card.getBonus(), bonusCopy);
      } else if (Replacement.DISCARD.startsWith(orientCard.getPower().name())) {
        Bonus bonusToDiscard = Bonus.valueOf(Replacement.DISCARD.replace(orientCard.getPower()
                                                                                   .name()));
        removeBonusHelper(bonusToDiscard, bonusCopy);
      }
    }
    return bonusCopy;
  }

  /**
   * Check if able to claim noble after purchased card.
   *
   * @param noble  Noble may be claimed.
   * @param player player to move
   * @return if able to claim noble after purchased card
   */
  public boolean postPurchaseNobleCheck(Player player, NobleCard noble) {

    Map<Bonus, Integer> bonusAfterMove = simulateMove(player);


    //Basically a static version of Player.validateNobleVisit
    return (noble.getState().equals(CardState.FREE)
      ||
      (noble.getState().equals(CardState.RESERVED) && player.hasReservedCard(noble)))

      && noble.getPrice()
              .keySet()
              .stream()
              .allMatch(gemType -> noble.getPrice()
                                        .get(gemType)
                <=
                Optional.ofNullable(bonusAfterMove.get(gemType))
                        .orElse(0));
  }

  /**
   * Return the ID of the Card which was bought.
   *
   * @return the value of id.
   */
  public int getValue() {
    return this.card.getMeta().getId();
  }
}