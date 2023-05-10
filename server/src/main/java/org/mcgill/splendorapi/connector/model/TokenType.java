package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * The token types.
 */
@RequiredArgsConstructor
@Getter
public enum TokenType {
  BEARER("bearer");

  @JsonValue
  private final String value;
  private static final Map<String, TokenType> map = getMapping();

  /**
   * Get the  possible mappings.
   *
   * @return the mapping from string to value
   */
  private static Map<String, TokenType> getMapping() {
    return Arrays.stream(TokenType.values())
                 .collect(Collectors.toMap(TokenType::getValue, value -> value));
  }

  /**
   * Create the json type from the function. Throws an error if we cannot find the value
   *
   * @param s the string input
   * @return The token type associated
   */
  @JsonCreator
  public static TokenType fromString(String s) {
    return Optional.of(map.get(s))
                   .orElseThrow(
                     () -> new IllegalArgumentException(String.format("No value of %s present", s))
                   );
  }
}
