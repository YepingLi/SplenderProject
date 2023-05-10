package org.mcgill.splendorapi.model.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

public class InvalidCardTypeTest {
  @Test
  public void exceptionTest() throws InvalidCardType {
    InvalidCardType theException = new InvalidCardType("test");
    try {
      throw theException;
    }catch (InvalidCardType e){
      assertEquals("test", e.getMessage());
      assertTrue(true);
    }
  }
}
