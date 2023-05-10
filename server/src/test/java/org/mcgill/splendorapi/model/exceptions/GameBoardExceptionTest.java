package org.mcgill.splendorapi.model.exceptions;

import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import static org.junit.jupiter.api.Assertions.*;

public class GameBoardExceptionTest {

  @Test
  public void exceptionTest() throws GameBoardException {
    GameBoardException theException = new GameBoardException("test");
    try {
      throw theException;
    }catch (GameBoardException e){
      assertEquals("test", e.getMessage());
      assertTrue(true);
    }
  }
}
