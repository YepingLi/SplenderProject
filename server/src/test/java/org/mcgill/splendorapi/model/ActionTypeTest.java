package org.mcgill.splendorapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionTypeTest {

  @Test
  public void testGetOrder() {
    Assertions.assertEquals(0, ActionType.BUY_CARD.getOrder());
    Assertions.assertEquals(1, ActionType.PAYMENT.getOrder());
    Assertions.assertEquals(2, ActionType.TAKE_LEVEL_TWO.getOrder());
    Assertions.assertEquals(3, ActionType.RESERVE_NOBLE.getOrder());
    Assertions.assertEquals(4, ActionType.LEVEL_TWO_PAIR.getOrder());
    Assertions.assertEquals(5, ActionType.TAKE_LEVEL_ONE.getOrder());
    Assertions.assertEquals(6, ActionType.LEVEL_ONE_PAIR.getOrder());
    Assertions.assertEquals(7, ActionType.CLAIM_NOBLE.getOrder());
    Assertions.assertEquals(8, ActionType.RESERVE_CARD.getOrder());
    Assertions.assertEquals(9, ActionType.TAKE_TOKEN.getOrder());
    Assertions.assertEquals(10, ActionType.BURN_TOKEN.getOrder());
  }
}
