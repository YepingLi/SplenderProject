package org.mcgill.splendorapi.api.move.model;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.mcgill.splendorapi.model.GemType;

/**
 * The payment object.
 */
@Getter
@Builder
public class Payment {
  private final Map<GemType, Integer> gems;
  private final int numDoubleGold;
}
