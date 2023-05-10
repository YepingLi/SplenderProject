package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.card.Card;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

class GamePlayerTest {
  Bonus testBonus1 =  Bonus.EMERALD;
  Bonus testBonus2 =  Bonus.DIAMOND;
  Map<GemType, Integer> testMap1 = new HashMap<GemType, Integer>(1);
  Map<GemType, Integer> testMap2 = new HashMap<GemType, Integer>(2);
  Player testGamePlayer = Player.builder().name("abc").build();
  Gem testGem = new Gem("asd", true, GemType.DIAMOND);
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
  @BeforeEach
  public void setup() throws InvalidCardType {
    Card testCard1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 123,
                                                                 CardType.DEVELOPMENT),
                                         testBonus1, 3, testMap1);
    Card testCard2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 234,
                                                                 CardType.DEVELOPMENT),
                                         testBonus2, 4, testMap2);
  }

  @Test
  void getPlayerPrestige() {
    assertEquals(0, testGamePlayer.getPlayerPrestige());
  }


  @Test
  void builder() {
    Player testGamePlayer =
        Player.builder().name("abc").build();
    assertEquals("abc", testGamePlayer.getName());

  }
}