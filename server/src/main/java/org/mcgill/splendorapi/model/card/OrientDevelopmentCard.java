package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

/**
 * Represents the Orient Development Cards.  Each carries a power.
 */
@Getter
public class OrientDevelopmentCard extends DevelopmentCard {
  private final OrientPower power;
  /**
   * Orient card class.
   *
   * @param meta Card meta data
   * @param abonus The bonus received
   * @param prestigePoints The prestige points of the card
   * @param aprice The price
   * @param position The position of the card on the board
   * @param isTurned If the card has been turned
   * @param state The state of the card
   * @throws InvalidCardType c
   */

  @JsonCreator
  public OrientDevelopmentCard(@JsonProperty("meta") DevelopmentCardMeta meta,
                               @JsonProperty("bonus") Bonus abonus,
                               @JsonProperty("power") OrientPower apower,
                               @JsonProperty("prestigePoints") int prestigePoints,
                               @JsonProperty("price") Map<GemType, Integer>  aprice,
                               @JsonProperty("position") int position,
                               @JsonProperty("turned") boolean isTurned,
                               @JsonProperty("state") CardState state) throws InvalidCardType {
    super(meta, abonus, prestigePoints, aprice, position, isTurned, state);
    power = apower;
  }

  /**
   * Copy constructor.
   *
   * @param card Card to copy
   * @throws InvalidCardType Error never thrown
   */
  public OrientDevelopmentCard(OrientDevelopmentCard card) throws InvalidCardType {
    this((DevelopmentCardMeta) card.meta, card.bonus, card.power,
         card.prestigePoints, card.price, card.position, card.turned, card.state);
  }


  /**
   * Overrides the meta to give the Id and level of this Orient Card.
   *
   * @return the DevelopmentCard Card meta.
   */
  @Override
  public DevelopmentCardMeta getMeta() {
    return (DevelopmentCardMeta) super.getMeta();
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

  @Override
  public OrientDevelopmentCard clone()  {
    return (OrientDevelopmentCard) super.clone();
  }

}
