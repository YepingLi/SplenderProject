package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenTypeTest {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  static <T> T fromJson(String jsonString, Class<T> clazz) {
    try {
      return objectMapper.readValue(jsonString, clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error parsing JSON", e);
    }
  }
  static <T> String toJson(T object, Class<T> clazz) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error converting object to JSON", e);
    }
  }
  @Test
  void getValue() {
    assertEquals("bearer", TokenType.BEARER.getValue());
  }

  @Test
  void fromString_validValue() {
    assertEquals(TokenType.BEARER, TokenType.fromString("bearer"));
  }


  @Test
  void testJsonValueAnnotation() {
    String jsonString = "\"bearer\"";

    TokenType tokenType = fromJson(jsonString, TokenType.class);
    assertEquals(TokenType.BEARER, tokenType);
  }
//  Removed assertEquals(tokenType, tokenType) doesn't make sense
//  @Test
//  void testJsonCreatorAnnotation() {
//    String jsonString = "\"bearer\"";
//    TokenType tokenType = TokenType.valueOf(toJson(TokenType.BEARER, TokenType.class));
//    assertEquals(tokenType, tokenType);
//  }

}
