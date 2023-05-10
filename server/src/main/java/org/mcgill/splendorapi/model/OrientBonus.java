package org.mcgill.splendorapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


/**
 * c.
 */
@RequiredArgsConstructor
public enum OrientBonus implements AbstractBonus {
  FREE_LEVEL_1("FREE_LEVEL_1"),
  RESERVE_NOBLE("RESERVE_NOBLE"),
  CARD_PAIRING("CARD_PAIRING"),
  GOLD("GOLD");

  private static final Map<String, OrientBonus> mapping = getMapping();
  private final String value;

  /**
   * Map the object to a String to Object mapping (deserializer).
   *
   * @return The map
   */
  private static Map<String, OrientBonus>  getMapping() {
    return Arrays.stream(OrientBonus.values())
                 .collect(Collectors.toMap(x -> x.value, Function.identity()));
  }

  /**
   * Produce the object from the string name.
   *
   * @param name The key
   * @return The object associated to the key
   */
  @JsonCreator
  public static AbstractBonus fromString(String name) {
    return Optional.ofNullable(mapping.get(name)).orElseThrow(AbstractBonus.produceError(name));
  }
}
