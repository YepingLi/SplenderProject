package org.mcgill.splendorapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * Gameboard representation.
 */
@Jacksonized
@Builder(buildMethodName = "build_")
public class GameBoard {
  @JsonProperty("currentPlayer")
  private GamePlayer curPlayer;
  @JsonProperty("players")
  private List<GamePlayer> players;
  @JsonIgnore
  private final List<Card> deck;
  // Mirrors of the deck
  @JsonIgnore
  private final List<Card> levelOne = new ArrayList<>();
  @JsonIgnore
  private final List<Card> levelTwo = new ArrayList<>();
  @JsonIgnore
  private final List<Card> levelThree = new ArrayList<>();
  @JsonIgnore
  private final Map<Bonus.BonusType, Bonus> bonuses;
  @JsonProperty("availableGems")
  private Map<Gem.Type, Integer> availableGems;
  @JsonIgnore
  private final List<Gem> gems;

  private List<Card> filterCards(List<Card> cards) {
    return cards.stream()
               .filter(card -> card.getTurned() && card.getState().equals(Card.State.FREE))
               .collect(Collectors.toList());
  }

  @JsonProperty("levelOneCards")
  @JsonCreator
  public List<Card> levelOneCards() {
    return filterCards(levelOne);
  }

  @JsonProperty("levelTwoCards")
  @JsonCreator
  public List<Card> levelTwoCards() {
    return filterCards(levelTwo);
  }

  @JsonProperty("levelThreeCards")
  @JsonCreator
  public List<Card> levelThreeCards() {
    return filterCards(levelThree);
  }

  /**
   * Builds the game board.
   */
  public static class GameBoardBuilder {
    /**
     * Class build function.
     *
     * @return The game board built.
     */
    public GameBoard build() {
      return build_().init();
    }
  }

  /**
   * Gets the card or raising on exception.
   *
   * @param id The id of the card
   * @return The card
   * @throws GameBoardException Game board is in an invalid state
   */
  public Card getCard(int id) throws GameBoardException {
    List<Card> cards = deck.stream()
        .filter(card -> card.getId() == id)
        .collect(Collectors.toList());

    if (cards.size() > 1) {
      throw new GameBoardException("Multiple cards with the same id");
    }

    return cards.remove(0);
  }

  /**
   * Ensure it is the players turn during their move.
   *
   * @param user The user sending the move
   * @return The game board if it is their turn
   * @throws IllegalMoveException If they are trying to perform a move while it is not their turn
   */
  public GameBoard checkIsTurn(String user) throws IllegalMoveException {
    if (! curPlayer.getName().equals(user)) {
      throw new IllegalMoveException();
    }

    return this;
  }

  /**
   * Initialize the board with the proper values.
   *
   * @return The gameboard object
   */
  public GameBoard init() {
    deck.forEach(card -> {
      switch (card.getLevel()) {
        case 3:
          levelThree.add(card);
          break;
        case 2:
          levelTwo.add(card);
          break;
        default:
          levelOne.add(card);
      }
    });

    return this;
  }
}
