package org.mcgill.splendorapi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.mcgill.splendorapi.model.card.*;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

public class CardTest {
  Bonus testBonus1 = Bonus.DIAMOND;
  Bonus testBonus2 = Bonus.EMERALD;
  Map<GemType, Integer> testMap1 = new HashMap<>(1);
  Map<GemType, Integer> testMap2 = new HashMap<>(2);
  DevelopmentCard testCard;

  public CardTest() throws InvalidCardType {
    testCard = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 123,
                                                           CardType.DEVELOPMENT),
                                   testBonus1, 1, testMap1);
  }
//TODO:
  /*
  @Test
  public void getLevelTest(){
    assertEquals(1,testCard.getMeta().getLevel());
    assertNotEquals(2,testCard.getMeta().getLevel());
  }
  @Test
  public void getIdTest(){
    assertEquals(123,testCard.getMeta().getId());
    assertNotEquals(213,testCard.getMeta().getId());
  }
*/
  @Test
  public void getBonusTest(){
    assertSame(testBonus1,testCard.getBonus());
    assertNotSame(testBonus2,testCard.getBonus());
  }
  /*
  @Test
  public void getprestiagePointsTest(){
    assertEquals(3,testCard.getPrestigePoints());
    assertNotEquals(6,testCard.getPrestigePoints());
  }

  public void getpriceTest(){
    assertSame(testMap1,testCard.getPrice());
    assertNotSame(testMap2,testCard.getPrice());
  }

   */
  @Test
  public void getStateTest(){
    assertEquals(CardState.FREE, testCard.getState());
  }
  //TODO:
  /*
  @Test
  public void changeStateTestIfbranch() throws IllegalMoveException {
    assertEquals(CardState.FREE,testCard.getState());
    testCard.changeState(CardState.PURCHASED);
    assertEquals(CardState.PURCHASED,testCard.getState());

    testCard.changeState(CardState.FREE);        //unchange => no branch
    assertEquals(CardState.PURCHASED,testCard.getState());
  }
  @Test
  public void changeStateTestElseifbranch() throws IllegalMoveException {
    //second Elseif branch one case
    assertEquals(CardState.FREE, testCard.getState());
    testCard.changeState(CardState.RESERVED);
    assertEquals(CardState.RESERVED, testCard.getState());
  }

   */
  //Test toString is removed b/c toString method is removed in development Card

}
