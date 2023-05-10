package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

/**
 * City card.
 */
@Getter
public class City extends AbstractCard {
  private final Map<Bonus, Integer> requirements;
  private final int sameRequirement;

  /**
   * Building city object.
   *
   * @param meta            Meta information
   * @param prestigePoints  the points
   * @param price           the price
   * @param sameRequirement if it is the same requirement
   * @throws InvalidCardType If the type of the card is invalid
   */
  @JsonCreator
  public City(@JsonProperty("meta") CardMeta meta,
              @JsonProperty("prestigePoints") int prestigePoints,
              @JsonProperty("price") Map<Bonus, Integer> price,
              @JsonProperty("sameRequirement") int sameRequirement) throws InvalidCardType {
    super(meta, prestigePoints);
    this.requirements = price;
    this.sameRequirement = sameRequirement;
  }
  /**
   * Copy constructor.
   *
   * @param card Card to copy
   * @throws InvalidCardType never thrown
   */

  public City(City card) throws InvalidCardType {
    this(card.meta,
         card.prestigePoints,
         card.requirements,
         card.sameRequirement);
  }

  /**
   * Sets the new state of the object.
   *
   * @param newState newState of the object
   * @throws IllegalMoveException y
   */
  @Override
  public void changeState(CardState newState) throws IllegalMoveException {
    assert newState != null && newState != CardState.RESERVED;
    super.changeState(newState);
  }

  /**
   * Adding new position.
   *
   * @param position new position
   */
  public void setPosition(int position) {
    assert position > 0;
    this.position = position;
  }
}
