package org.mcgill.splendorapi.api.move.model;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * perform dto move.
 */

@Getter
@Jacksonized
@Builder
public class PerformMoveDto {
  private final Integer moveId;
}
