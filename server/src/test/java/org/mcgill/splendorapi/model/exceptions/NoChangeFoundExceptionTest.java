package org.mcgill.splendorapi.model.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.exceptions.NoChangeFoundException;

public class NoChangeFoundExceptionTest {
  @Test
  public void exceptionTest1() throws NoChangeFoundException {
    NoChangeFoundException theException = new NoChangeFoundException("test");
    try {
      throw theException;
    }catch (NoChangeFoundException e){
      assertEquals(String.format("No change was found for %s", "test"), e.getMessage());
      assertTrue(true);
    }
  }

  @Test
  public void exceptionTest2() throws NoChangeFoundException {
    NoChangeFoundException theException = NoChangeFoundException.builder().id("test").build();
    try {
      throw theException;
    }catch (NoChangeFoundException e){
      assertEquals(String.format("No change was found for %s", "test"), e.getMessage());
      assertTrue(true);
    }
  }
}
