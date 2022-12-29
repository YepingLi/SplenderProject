package org.mcgill.splendorapi.connector.model;

import lombok.RequiredArgsConstructor;

/**
 * Class for token credentials.
 */
@RequiredArgsConstructor
public class TokenCredentials {
  private final String accessToken;
  private final TokenType type;

  public String getToken() {
    return String.join(" ", type.getValue(), accessToken);
  }
}
