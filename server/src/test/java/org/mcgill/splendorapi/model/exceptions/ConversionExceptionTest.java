package org.mcgill.splendorapi.model.exceptions;

import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.exceptions.ConversionException;
import static org.junit.jupiter.api.Assertions.*;

public class ConversionExceptionTest {
  @Test
  public void exceptionTest(){
    ConversionException testException = new ConversionException("test");
    try {
      throw testException;
    }catch (ConversionException e){
      assertEquals("test", e.getMessage());
      assertTrue(true);
    }
  }
}
