package org.mcgill.splendorapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unauthorized Test")
public class UnauthorizedTest {

  @Test
  @DisplayName("Test Unauthorized Object")
  void testUnauthorizedObject() {
    Unauthorized unauthorized = Unauthorized.builder()
                                            .error("unauthorized_error")
                                            .description("Unauthorized Description")
                                            .build();

    Assertions.assertNotNull(unauthorized, "Unauthorized object is null.");
    Assertions.assertEquals("unauthorized_error", unauthorized.getError(), "Error value is not equal.");
    Assertions.assertEquals("Unauthorized Description", unauthorized.getDescription(), "Description value is not equal.");
  }
}
