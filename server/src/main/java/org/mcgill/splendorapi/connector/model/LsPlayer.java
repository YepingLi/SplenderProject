package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Helps deserialize LS input.
 */
@Builder
@Jacksonized
@Getter
public class LsPlayer {

  @JsonProperty("name")
  private final String name;

  @JsonProperty("color")
  private final String color;

  public LsPlayer(String name, String color) {
    this.name = name;
    this.color = color;
  }
}
