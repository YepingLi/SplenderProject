package org.mcgill.splendorapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Base player type.
 */
@RequiredArgsConstructor
@Getter
public class BasePlayer {
  private final String name;
}
