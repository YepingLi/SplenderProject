package org.mcgill.splendorapi.api.move.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PerformMoveDtoTest {

  @Test
  public void testConstructorAndGetters() {
    Integer moveId = 666;
    PerformMoveDto performMoveDto = new PerformMoveDto(moveId);

    assertNotNull(performMoveDto);
    assertEquals(moveId, performMoveDto.getMoveId());
  }

  @Test
  public void testBuilder() {
    Integer moveId = 666;
    PerformMoveDto performMoveDto = PerformMoveDto.builder()
                                                  .moveId(moveId)
                                                  .build();

    assertNotNull(performMoveDto);
    assertEquals(moveId, performMoveDto.getMoveId());
  }
}
