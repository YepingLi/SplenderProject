package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

class GamePlayerTest {
  Bonus testBonus1 = new Bonus(Bonus.BonusType.DIAMOND, 1);
  Bonus testBonus2 = new Bonus(Bonus.BonusType.EMERALD, 1);
  Map<Gem.Type, Integer> testMap1 = new HashMap<Gem.Type, Integer>(1);
  Map<Gem.Type, Integer> testMap2 = new HashMap<Gem.Type, Integer>(2);
  Card testCard1 = new Card(1, 123, testBonus1, 3,"123", testMap1);
  Card testCard2 = new Card(2, 234, testBonus2, 4,"234", testMap2);
  GamePlayer testGamePlayer = GamePlayer.builder().name("abc").build();
  Gem testGem = new Gem("asd","sadsad",true, Gem.Type.DIAMOND);
//  @Test
//  void reserveCard() {
//    testGamePlayer.reserveCard(testCard1);
//    assertEquals(Card.State.RESERVED, testCard1.getState());
//    assertEquals(testCard1, testGamePlayer.reservedCard.remove(0));
//  }
//
//  @Test
//  void buyCard() {//need to redo
//    List<Gem> a = new ArrayList<>();
//    assertEquals(a, testGamePlayer.buyCard(testCard1));
//  }


  @Test
  void getPlayerPrestige() {
    assertEquals(0, testGamePlayer.getPlayerPrestige());
  }

  @Test
  void addGem() {
    testGamePlayer.addGem(testGem);
    assertSame( testGamePlayer.gems.get(Gem.Type.DIAMOND).get(0), testGem);

  }

  @Test
  void builder() {
    GamePlayer testGamePlayer =
        GamePlayer.builder().name("abc").build();
    assertEquals("abc", testGamePlayer.getName());

  }
}