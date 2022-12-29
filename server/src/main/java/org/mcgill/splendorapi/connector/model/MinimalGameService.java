package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Description of the Minimal game service object.
 */
@Getter
@RequiredArgsConstructor
public class MinimalGameService {
  @JsonProperty("name")
  private final String name;
  @JsonProperty("displayName")
  private final String displayName;
}