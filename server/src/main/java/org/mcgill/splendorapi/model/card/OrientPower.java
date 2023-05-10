package org.mcgill.splendorapi.model.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for the possible powers when purchasing an orient card.
 */
@RequiredArgsConstructor
@Getter
public enum OrientPower {
  //LEVEL ONE POWERS:
  DOUBLE_GOLD, //Player collect a double gold card.
  PAIRING,    //A level one pairing Card.

  //LEVEL TWO POWERS:
  PAIRING_CASCADE, //A level Two Pairing Card.
  DOUBLE_BONUS,    //Worth Two Bonuses.
  RESERVE_NOBLE,   //The Player gets to select a noble.

  //LEVELTHREEPOWERS
  DISCARD_DIAMOND, //A level Three orient Card which requires discarding bonuses as payment
  DISCARD_EMERALD,
  DISCARD_ONYX,
  DISCARD_SAPPHIRE,
  DISCARD_RUBY,
  LEVEL_THREE_CASCADE;
}
