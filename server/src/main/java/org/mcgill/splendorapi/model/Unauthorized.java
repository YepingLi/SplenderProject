package org.mcgill.splendorapi.model;

import lombok.Builder;
import lombok.Value;

/**
 * Unauthorized object.
 */
@Builder
@Value
public class Unauthorized {
  String error;
  String description;
}
