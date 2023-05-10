package org.mcgill.splendorapi.model.card;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

/**
 * Crd type enum.
 */
@RequiredArgsConstructor
@Getter
public enum CardType {
  DEVELOPMENT("DEVELOPMENT"), NOBLE("NOBLE"),
  ORIENT("ORIENT"), TRADING_POST("TRADING_POST"), CITY("CITY");
  private final String name;
  private static final Map<String, CardType> nameMapping = getMapping();

  /**
   * Builds the mapping of the enum.
   *
   * @return returns string to enum map
   */
  private static Map<String, CardType> getMapping() {
    return Arrays.stream(CardType.values())
                 .collect(Collectors.toMap(CardType::getName, type -> type));
  }

  /**
   * Gets the value from the provided string.
   *
   * @param name The name of the enum we are trying to get
   * @return The Cardtype or throw error
   * @throws InvalidCardType Card type does not exist
   */
  public static CardType fromValue(String name) throws InvalidCardType {
    return Optional.ofNullable(nameMapping.get(name))
                   .orElseThrow(() -> new InvalidCardType(
                     String.format("No key of value %s", name)));
  }
}
