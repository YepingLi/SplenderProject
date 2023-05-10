package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;

/**
 * Scope class for tokens.
 */
@Builder
@Getter
public class Scope {

  /**
   * JsonDeserializer.
   */
  public static class ScopeDeserializer extends JsonDeserializer<Scope> {

    /**
     * JsonDeserialization for scopes.
     *
     * @param jsonParser             The parser
     * @param deserializationContext the deserialization context
     * @return The scope
     * @throws IOException When not parsable
     */
    @Override
    public Scope deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
          throws IOException {
      JsonNode node = jsonParser.readValueAsTree();
      String text = node.asText();
      Stream<String> list = Arrays.stream(text.split(" "));
      return Scope.builder()
                  .scopes(list.collect(Collectors.toSet()))
                  .build();
    }
  }

  final Set<String> scopes;

}