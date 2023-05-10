package org.mcgill.splendorapi.model.exceptions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

public class IllegalMoveExceptionTest {

  @Test
  public void exceptionTest() throws IllegalMoveException{
    IllegalMoveException testException = new IllegalMoveException();
    try {
      throw testException;
    }catch (IllegalMoveException e){
      assertEquals( "The provided move is illegal!", e.getMessage());
      assertTrue(true);
    }
  }
}
