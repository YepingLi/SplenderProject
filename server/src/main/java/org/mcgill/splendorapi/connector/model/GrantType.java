package org.mcgill.splendorapi.connector.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Grant Type Objects.
 */
@RequiredArgsConstructor
@Getter
public enum GrantType {
  PASSWORD("password"), TOKEN("token");

  private final String value;
}
