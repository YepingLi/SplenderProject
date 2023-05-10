package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;


/**
 * c.
 */
@Getter
public class NobleCard extends AbstractCard {
  private final Map<Bonus, Integer> price;

  /**
   *Constructs the Noble which can be initialized based on the json file.
   */
  @JsonCreator
  public NobleCard(@JsonProperty("meta") CardMeta meta,
                   @JsonProperty("position") int position,
                   @JsonProperty("prestigePoints") int prestigePoints,
                   @JsonProperty("price") Map<Bonus, Integer>  price) throws InvalidCardType {
    super(meta, prestigePoints);
    this.price = price;
  }

  /**
   * Copy constructor.
   *
   * @param card Card to copy
   * @throws InvalidCardType never thrown
   */
  public NobleCard(NobleCard card) throws InvalidCardType {
    this(card.meta, card.position, card.prestigePoints, card.price);
  }

  /**
   * Sets the new state of the object.
   *
   * @param newState newState of the object
   * @throws IllegalMoveException y
   */
  @Override
  public void changeState(CardState newState) throws IllegalMoveException {
    super.changeState(newState);
  }

  /**
   * Gets the state.
   *
   * @return state of the card
   */
  @Override
  public CardState getState() {
    return state;
  }

  /**
   * Gets the meta information.
   *
   * @return meta information of the card
   */
  @Override
  public CardMeta getMeta() {
    return meta;
  }

  public void setPosition(int pos) {
    this.position = pos;
  }

  @Override
  public NobleCard clone() {
    try {
      return (NobleCard) super.clone();
    } catch (CloneNotSupportedException e) {
      try {
        return new NobleCard(meta, position, prestigePoints, price);
      } catch (InvalidCardType ex) {
        throw new RuntimeException(ex);
      }
    }
  }
}
