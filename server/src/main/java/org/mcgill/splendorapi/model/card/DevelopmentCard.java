package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;


/**
 * Dev Card.
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DevelopmentCard extends AbstractCard {
  protected final Map<GemType, Integer> price;
  protected boolean turned = false;

  protected final Bonus bonus;


  /**
   * Builds the card without starting position.
   *
   * @param meta          The meta information
   * @param bonus          The bonus received
   * @param prestigePoints The number of prestige points
   * @param price          The price
   * @throws InvalidCardType If the type of card is invalid when building the object
   */
  public DevelopmentCard(DevelopmentCardMeta meta,
                         Bonus bonus,
                         int prestigePoints,
                         Map<GemType, Integer>  price) throws InvalidCardType {
    super(meta, prestigePoints);
    this.price = price;
    this.bonus = bonus;
  }

  /**
   * Builds the card with a starting position.
   * NOTE: Used when loading a game from memory
   */
  @JsonCreator
  public DevelopmentCard(@JsonProperty("meta") DevelopmentCardMeta meta,
                         @JsonProperty("bonus") Bonus bonus,
                         @JsonProperty("prestigePoints") int prestigePoints,
                         @JsonProperty("price") Map<GemType, Integer>  price,
                         @JsonProperty("position") int position,
                         @JsonProperty("turned") boolean isTurned,
                         @JsonProperty("state") CardState state) throws InvalidCardType {
    super(meta, prestigePoints, position, state);
    this.price = price;
    this.turned = isTurned;
    this.bonus = bonus;
  }

  /**
   * Copy constructor.
   *
   * @param card Card to copy
   * @throws InvalidCardType Error never thrown
   */
  public DevelopmentCard(DevelopmentCard card) throws InvalidCardType {
    this((DevelopmentCardMeta) card.meta, card.bonus,
         card.prestigePoints, card.price, card.position, card.turned, card.state);
  }

  public void setState(CardState state) {
    this.state = state;
  }

  /**
   * Turn the card over.
   *
   * @throws IllegalStateException Thrown when card is already turned
   */
  public void turnOver(int cardPosition) throws IllegalStateException {
    if (turned) {
      throw new IllegalStateException("Cannot turn over card which is already turned over");
    }
    position = cardPosition;
    turned = true;
  }

  /**
   * update the newState of the card.
   *
   * @param newState newState of the object
   */
  public void changeState(CardState newState) throws IllegalMoveException {
    if (!turned) {
      throw new IllegalMoveException("Cannot change state of card which is not visible");
    }

    super.changeState(newState);
  }

  @Override
  public DevelopmentCardMeta getMeta() {
    return (DevelopmentCardMeta) super.getMeta();
  }

  /**
   * Cards are cloneable and performing clone here.
   *
   * @return The clone of the card
   */
  @Override
  public DevelopmentCard clone()  {
    try {
      return (DevelopmentCard) super.clone();
    } catch (CloneNotSupportedException e) {
      try {
        return new DevelopmentCard(
          (DevelopmentCardMeta) meta, bonus,
          prestigePoints, price, position, isTurned(), state
        );
      } catch (InvalidCardType ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  public boolean isFree() {
    return (this.state.equals(CardState.FREE));
  }
}
