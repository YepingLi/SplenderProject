package org.mcgill.splendorapi.model;

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
public enum GemType {
  ONYX("ONYX"),
  SAPPHIRE("SAPPHIRE"),
  DIAMOND("DIAMOND"),
  EMERALD("EMERALD"),
  RUBY("RUBY"),
  GOLD("GOLD");
  private static Map<String, GemType> map = Arrays.stream(GemType.values())
                                                  .collect(Collectors.toMap(GemType::getValue,
                                                                            Function.identity()));
  private final String value;

  public static Optional<GemType> fromBonus(Bonus bonus) {
    return Optional.ofNullable(map.get(bonus.getValue()));
  }
}