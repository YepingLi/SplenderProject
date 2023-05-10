package org.mcgill.splendorapi.model.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.exceptions.NoGameFoundException;

public class NoGameFoundExceptionTest {
  @Test
  public void exceptionTest() throws NoGameFoundException {
    NoGameFoundException theException = new NoGameFoundException("test");
    try {
      throw theException;
    }catch (NoGameFoundException e){
      assertEquals(String.format("Failed to load game with id %s", "test"), e.getMessage());
      assertTrue(true);
    }
  }
}
