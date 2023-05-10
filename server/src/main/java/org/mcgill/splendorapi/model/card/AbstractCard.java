package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;
import lombok.Getter;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

/**
 * Card object.
 */
@Getter
public abstract class AbstractCard implements Card {
  protected final CardMeta meta;
  protected int prestigePoints;
  protected CardState state = CardState.FREE;
  protected Integer position = -1;

  /**
   * Builds an abstract card.
   *
   * @param meta           meta information about the card
   * @param prestigePoints The number prestige points the card gives.
   * @throws InvalidCardType If the card type is not supported;
   */
  public AbstractCard(CardMeta meta, int prestigePoints) throws InvalidCardType {
    this.meta = meta;
    this.prestigePoints = prestigePoints;
  }


  /**
   * Abstract card with a start position.
   *
   * @param meta           The associated meta information
   * @param prestigePoints The number prestige points the card gives.
   * @param startPosition  The position the card is starting at on the board
   * @throws InvalidCardType If the card type is not supported;
   */
  public AbstractCard(CardMeta meta, int prestigePoints,
                      Integer startPosition, CardState state) throws InvalidCardType {
    this(meta, prestigePoints);
    position = startPosition;
    this.state = Optional.ofNullable(state).orElse(CardState.FREE);
  }


  /**
   * update the newState of the card.
   *
   * @param newState newState of the object
   */
  public void changeState(CardState newState) throws IllegalMoveException {
    if (newState.equals(CardState.PURCHASED)
        && (state.equals(CardState.RESERVED) || state.equals(CardState.FREE))) {
      state = newState;
    } else if (newState.equals(CardState.RESERVED) && !state.equals(CardState.RESERVED)
        && state.equals(CardState.FREE)) {
      state = newState;
    }
  }

  /**
   * Checks the type of the card.
   *
   * @return If the card is a development card or not.
   */
  @Override
  @JsonIgnore
  public boolean isDevelopment() {
    return getMeta().getType().equals(CardType.DEVELOPMENT);
  }

  /**
   * Checks the type of the card.
   *
   * @return If the card is a development card or not.
   */

  @Override
  @JsonIgnore
  public boolean isOrientCard() {
    return getMeta().getType().equals(CardType.ORIENT);
  }

  /**
   * Checks the type of the card.
   *
   * @return If the card is a development card or not.
   */

  @Override
  @JsonIgnore
  public boolean isNobleCard() {
    return getMeta().getType().equals(CardType.NOBLE);
  }

  /**
   * Checks the type of the card.
   *
   * @return If the card is a development card or not.
   */

  @Override
  @JsonIgnore
  public boolean isTradingPost() {
    return getMeta().getType().equals(CardType.TRADING_POST);
  }

  @Override
  @JsonIgnore
  public boolean isCity() {
    return getMeta().getType().equals(CardType.CITY);
  }

}
