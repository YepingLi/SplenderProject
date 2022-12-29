package org.mcgill.splendorapi.model.exceptions;

import lombok.Builder;
import org.mcgill.splendorapi.model.Card;

/**
 * Card exception class.
 */
public class CardException extends GameBoardException {
  private static final String formattable = "Card is not able to be %s since it is already %s";

  /**
   * Building the card exception.
   *
   * @param card The card causing the exception
   * @param newState the new state
   */
  @Builder
  public CardException(Card card, Card.State newState) {
    super(String.format(formattable, newState.name(), card.getState().name()));
  }
}