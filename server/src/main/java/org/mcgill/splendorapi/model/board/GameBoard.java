package org.mcgill.splendorapi.model.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.mcgill.splendorapi.api.move.model.Payment;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.card.Card;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;

/**
 * Gameboard representation.
 */
@Jacksonized
@Getter
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = GameBoard.class, name = "base"),
  @JsonSubTypes.Type(value = OrientGameBoard.class, name = "orient"),
  @JsonSubTypes.Type(value = CityGameBoard.class, name = "citiesOrient"),
  @JsonSubTypes.Type(value = TradingPostsOrientBoard.class, name = "orientTradingPosts"),
  @JsonSubTypes.Type(value = TradingPostsGameBoard.class, name = "tradingPosts")
})
public class GameBoard {
  protected final List<DevelopmentCard> deck;
  protected final Map<GemType, Integer> gems;
  protected final List<NobleCard> nobles;
  @JsonSerialize(using = GameType.GameTypeSerializer.class)
  private GameType type;

  public GameType fixType(GameType t) {
    return Optional.ofNullable(t).orElse(GameType.BASE);
  }

  @JsonIgnore
  public List<DevelopmentCard> getLevelOne() {
    return getDevelopmentByLevel(1);
  }

  @JsonIgnore
  public List<DevelopmentCard> getLevelTwo() {
    return getDevelopmentByLevel(2);
  }

  @JsonIgnore
  public List<DevelopmentCard> getLevelThree() {
    return getDevelopmentByLevel(3);
  }

  protected List<DevelopmentCard> getByLevel(int level, Function<DevelopmentCard, Boolean> type) {
    return deck.stream()
               .filter(card -> type.apply(card) && card.getMeta().getLevel() == level)
               .collect(
                 Collectors.toList());
  }

  private List<DevelopmentCard> getDevelopmentByLevel(int level) {
    return getByLevel(level, Card::isDevelopment);
  }

  public Integer getAvailableGemType(GemType gemType) {
    return gems.getOrDefault(gemType, 0);
  }

  /**
   * Initializes the base Game Board.
   *
   * @return the initialized gameBoard.
   */
  public GameBoard init() {
    this.type = fixType(this.type);
    Random random = new Random();
    // Deal 4 cards at random.
    for (int i = 0; i < 4; i++) {
      for (int j = 1; j < 4; j++) {
        List<DevelopmentCard> faceUp = getHiddenCards(j, Card::isDevelopment);
        faceUp.get(random.nextInt(faceUp.size())).turnOver(i);
      }
    }
    return this;
  }


  /**
   * Filter the Deck corresponding to a specific level to only give you the hidden Cards.
   *
   * @param lvl  level
   * @param type type
   * @return the hidden cards for the level nd type
   */
  public List<DevelopmentCard> getHiddenCards(int lvl, Function<Card, Boolean> type) {
    return deck
      .stream()
      .filter(
        c -> type.apply(c) && !c.isTurned()
          && c.getState().equals(CardState.FREE) && c.getMeta().getLevel() == lvl
      )
      .collect(Collectors.toList());
  }

  /**
   * Filters the cards to return the id of the face up cards.
   *
   * @param cards cards
   * @return Id's of face up cards in the list.
   */
  public List<Short> getAvailableCards(List<DevelopmentCard> cards) {
    return cards.stream()
                .filter(c -> c.isTurned() && c.getState().equals(CardState.FREE))
                .map(c -> c.getMeta().getId()).collect(Collectors.toList());
  }

  protected List<DevelopmentCard> produceHiddenChoices(DevelopmentCardMeta meta) {
    return getHiddenCards(meta.getLevel(), Card::isDevelopment);
  }

  /**
   * Deals a new Card at Random from the list of face down cards for the specified level.
   *
   * @param meta Meta of the previous card.
   */
  public void dealCard(DevelopmentCardMeta meta) {
    List<DevelopmentCard> hiddenCards = produceHiddenChoices(meta);
    if (hiddenCards.size() < 1) {
      return;
    }
    Random rand = new Random();
    int index = rand.nextInt(hiddenCards.size());
    DevelopmentCard selectedCard = hiddenCards.get(index);
    selectedCard.turnOver(selectedCard.getPosition());
  }


  /**
   * Returns only the faceUp cards.
   *
   * @param cards DevCard (Orient or Base)
   * @return The list of face up cards.
   */
  public static List<DevelopmentCard> getFaceUpCards(List<DevelopmentCard> cards) {
    return cards.stream()
                .filter(card -> card.isTurned() && card.getState().equals(CardState.FREE))
                .collect(Collectors.toList());
  }

  /**
   * Gets the card or raising on exception.
   *
   * @param meta The meta information of the card
   * @return The card
   * @throws GameBoardException LauncherInfo board is in an invalid state
   */
  public Card getCard(DevelopmentCardMeta meta) throws GameBoardException {
    List<Card> cards = deck.stream()
                           .filter(card -> compareCards(card.getMeta(), meta))
                           .collect(Collectors.toList());
    if (cards.size() > 1) {
      throw new GameBoardException("Multiple cards with the same id");
    }
    if (cards.size() == 0) {
      return null;
    }
    return cards.remove(0);
  }

  private boolean compareCards(DevelopmentCardMeta meta, DevelopmentCardMeta meta1) {
    return meta.getId() == meta1.getId()
      && meta.getLevel() == meta1.getLevel()
      && meta.getType().toString().equals(meta1.getType().toString());
  }

  /**
   * Adss the Players payment to the board.
   *
   * @param payment list of gems the player used to buy a card.
   */
  public void addGems(Payment payment) {
    Optional.ofNullable(payment).ifPresent(pay -> {
      pay.getGems()
          .forEach((gem, num) -> gems.merge(gem, num, Integer::sum));
    });
  }
}
