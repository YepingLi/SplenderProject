package org.mcgill.splendorapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.model.exceptions.ConversionException;


/**
 * c.
 */
@RequiredArgsConstructor
@Getter
public enum GameType {
  BASE("base"),
  TRADING_POSTS("tradingPosts"),
  ORIENT("orient"),
  ORIENT_TRADING_POST("orientTradingPosts"),
  CITIES_ORIENT("citiesOrient");
  private final String value;

  private static final Map<String, GameType> mapping = getMapping();

  /**
   * Serializer.
   */
  public static class GameTypeSerializer extends StdSerializer<GameType> {
    /**
     * Default constructor.
     */
    public GameTypeSerializer() {
      this(null);
    }

    /**
     * Constructor for serializer.
     *
     * @param t the class type
     *
     */
    protected GameTypeSerializer(Class<GameType> t) {
      super(t);
    }


    @Override
    public void serialize(GameType s, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeString(s.value);
    }
  }

  /**
   * mapping.
   */
  private static Map<String, GameType> getMapping() {
    return Arrays.stream(GameType.values()).collect(Collectors.toMap(GameType::getValue, v -> v));
  }

  /**
   * c.
   */
  @JsonCreator
  public static GameType fromString(String name) throws ConversionException {
    return Optional.ofNullable(mapping.get(name))
                   .orElseThrow(() -> new ConversionException(
                     String.format("Could not find a mapping for %s",
                                   name)
                   ));
  }
}
