package org.mcgill.splendorapi.api.move.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Action;
import org.mcgill.splendorapi.model.ActionType;

public class MoveDtoTest {
  private Action<String> action1 = new Action<>(ActionType.BUY_CARD, "cardId");
  private Action<String> action2 = new Action<>(ActionType.BUY_CARD, "cardId");
  @Test
  public void testGetActions() {
    // Arrange
    List<Action<?>> actions = Arrays.asList(
      action1,
      action2
    );
    MoveDto moveDto = new MoveDto(actions);

    // Act
    List<Action<?>> result = moveDto.getActions();

    // Assert
    assertEquals(actions, result);
  }
}
