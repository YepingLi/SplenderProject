package org.mcgill.splendorapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Bonus representation.
 */
@RequiredArgsConstructor
@Getter
public enum Bonus implements AbstractBonus {
  DIAMOND("DIAMOND"),
  EMERALD("EMERALD"),
  ONYX("ONYX"),
  RUBY("RUBY"),
  SAPPHIRE("SAPPHIRE");


  private final String value;
  private static final Map<String, Bonus> mapping = getMapping();

  /**
   * Map the object to a String to Object mapping (deserializer).
   *
   * @return The map
   */
  private static Map<String, Bonus> getMapping() {
    return Arrays.stream(Bonus.values())
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

  public static Bonus fromGem(GemType gem) {
    return (Bonus) Bonus.fromString(gem.getValue());
  }
}
