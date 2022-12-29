package org.mcgill.splendorapi.connector.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestAuthority {
  @Test
  public void testAuthority() {
    Authority auth = Authority.builder().authority("something").build();
    assertEquals("something", auth.getAuthority());
  }
}
