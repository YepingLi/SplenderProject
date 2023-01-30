package org.mcgill.splendorapi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CardTest {
  Bonus testBonus1 = new Bonus(Bonus.BonusType.DIAMOND, 1);
  Bonus testBonus2 = new Bonus(Bonus.BonusType.EMERALD, 1);
  Map<Gem.Type, Integer> testMap1 = new HashMap<>(1);
  Map<Gem.Type, Integer> testMap2 = new HashMap<>(2);
  Card testCard = new Card(1, 123, testBonus1, 3,"123", testMap1);

  @Test
  public void getLevelTest(){
    assertEquals(1,testCard.getLevel());
    assertNotEquals(2,testCard.getLevel());
  }
  @Test
  public void getIdTest(){
    assertEquals(123,testCard.getId());
    assertNotEquals(213,testCard.getId());
  }

  @Test
  public void getBonusTest(){
    assertSame(testBonus1,testCard.getBonus());
    assertNotSame(testBonus2,testCard.getBonus());
  }
  @Test
  public void getprestiagePointsTest(){
    assertEquals(3,testCard.getPrestigePoints());
    assertNotEquals(6,testCard.getPrestigePoints());
  }

  @Test
  public void geturiTest(){
    assertEquals("123",testCard.getUri());
    assertNotEquals("sad",testCard.getUri());
  }
  public void getpriceTest(){
    assertSame(testMap1,testCard.getPrice());
    assertNotSame(testMap2,testCard.getPrice());
  }
  @Test
  public void getStateTest(){
    assertEquals(Card.State.FREE,testCard.getState());
  }
//  @Test
//  public void changeStateTestIfbranch(){
//    assertEquals(Card.State.FREE,testCard.getState());
//    testCard.changeState(Card.State.PURCHASED);
//    assertEquals(Card.State.PURCHASED,testCard.getState());
//
//    testCard.changeState(Card.State.FREE);        //unchange => no branch
//    assertEquals(Card.State.PURCHASED,testCard.getState());
//  }
//  @Test
//  public void changeStateTestElseifbranch(){
//    //second Elseif branch one case
//    assertEquals(Card.State.FREE, testCard.getState());
//    testCard.changeState(Card.State.RESERVED);
//    assertEquals(Card.State.RESERVED, testCard.getState());
//  }
}
