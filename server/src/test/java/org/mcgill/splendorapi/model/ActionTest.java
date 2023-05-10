package org.mcgill.splendorapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
  private Action<String> action;

  @BeforeEach
  void setUp() {
    action = new Action<>(ActionType.BUY_CARD, "cardId");
  }

  @Test
  void getType() {
    assertEquals(ActionType.BUY_CARD, action.getType(), "Expected ActionType to be PLAY_CARD");
  }

  @Test
  void getValue() {
    assertEquals("cardId", action.getValue(), "Expected value to be 'cardId'");
  }

  @Test
  void testConstructor() {
    Action<Integer> action2 = new Action<>(ActionType.BURN_TOKEN, 42);
    assertEquals(ActionType.BURN_TOKEN, action2.getType(), "Expected ActionType to be BUY_CARD");
    assertEquals(Integer.valueOf(42), action2.getValue(), "Expected value to be 42");
  }
}
