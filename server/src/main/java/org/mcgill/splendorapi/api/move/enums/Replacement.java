package org.mcgill.splendorapi.api.move.enums;

import lombok.RequiredArgsConstructor;

/**
 * Replacement of typed strings to extract value.
 */
@RequiredArgsConstructor
public enum Replacement {
  DISCARD("^DISCARD_");
  private final String replacement;
  /**
   * removes the old value and adds empty string.
   *
   * @param toReplace The replacement
   *
   * @return The new string
   */

  public String replace(String toReplace) {
    return toReplace.replaceFirst(replacement, "");
  }

  public boolean startsWith(String toReplace) {
    return toReplace.startsWith(name().toUpperCase());
  }
}
