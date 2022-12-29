package org.mcgill.splendorapi.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Credentials for connecting to the lobby service.
 */
@RequiredArgsConstructor
@Getter
public class OpenIdAuth2 {
  private final String name;
  private final String password;
  private final String token;
}
