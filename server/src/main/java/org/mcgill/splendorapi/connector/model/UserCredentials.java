package org.mcgill.splendorapi.connector.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Class for user credentials.
 */
@Builder
@Getter
public class UserCredentials {
  private String name;
  private String passcode;
}

