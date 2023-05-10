package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class JsonTestUtils {

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
}
