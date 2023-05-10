package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * Card object.
 */
@JsonTypeInfo(
      use = JsonTypeInfo.Id.NONE
)
@JsonTypeResolver(CardResolver.class)
public interface Card extends Cloneable {

  /**
   * update the newState of the card.
   *
   * @param newState newState of the object
   */
  void changeState(CardState newState) throws IllegalMoveException;

  CardState getState();

  boolean isDevelopment();
  
  boolean isOrientCard();

  boolean isNobleCard();

  boolean isTradingPost();

  boolean isCity();

  CardMeta getMeta();
}
