package org.mcgill.splendorapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represent the possible action types.
 */
@Getter
@RequiredArgsConstructor
public enum ActionType {


  BUY_CARD(0),
  PAYMENT(1), //If the player has at least one gold and must specify how they want to pay.

  TAKE_LEVEL_TWO(2), //If the user just purchased a level three cascading card.

  RESERVE_NOBLE(3), //Reserves a noble.
  LEVEL_TWO_PAIR(4),

  TAKE_LEVEL_ONE(5), //If the user just purchased/took a level two cascading card
  LEVEL_ONE_PAIR(6), //PAIR's a newly purchased orient card with a bonus previously acquired
  CLAIM_NOBLE(7),  //If after purchasing a card, a player meets the requirement for >1 noble.
  RESERVE_CARD(8),
  TAKE_TOKEN(9),
  BURN_TOKEN(10);
  private final int order;
}
