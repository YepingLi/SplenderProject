package org.mcgill.splendorapi.model.card;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DevelopmentCardTest {

  private DevelopmentCard aCard;
  @Test
  void testSetState() throws InvalidCardType {
    aCard = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 2, new HashMap<>());
    assert(aCard.getState().equals(CardState.FREE));
    aCard.setState(CardState.RESERVED);
    assert(aCard.getState().equals(CardState.RESERVED));
    aCard.setState(CardState.PURCHASED);
    assert(aCard.getState().equals(CardState.PURCHASED));
  }
  /*
  @Test
  void testChangeState() throws InvalidCardType, IllegalMoveException {
    aCard = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 2, new HashMap<>());
    assert(aCard.getState().equals(CardState.FREE));
    aCard.changeState(CardState.RESERVED);
    assert(aCard.getState().equals(CardState.RESERVED));
    assertThrows(IllegalMoveException.class, ()-> aCard.changeState(CardState.PURCHASED));
    aCard.turnOver(1);
    aCard.changeState(CardState.PURCHASED);
    assert(aCard.getState().equals(CardState.PURCHASED));
  }
  @Test
  void testGetMeta() throws InvalidCardType{
    aCard = new DevelopmentCard(new DevelopmentCardMeta((short) 123, (short) 3, CardType.DEVELOPMENT), Bonus.EMERALD, 2, new HashMap<>());
    assertEquals(123,aCard.getMeta().getId());
    assertEquals(3,aCard.getMeta().getLevel());
    assertEquals(CardType.DEVELOPMENT,aCard.getMeta().getType());
  }
   */
  private DevelopmentCard card;
  private DevelopmentCardMeta meta;
  private Bonus bonus = Bonus.RUBY;
  private int prestigePoints;
  private Map<GemType, Integer> price;

  @BeforeEach
  void setUp() throws InvalidCardType {
    meta = new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT);
    prestigePoints = 3;
    price = new HashMap<>();
    card = new DevelopmentCard(meta, bonus, prestigePoints, price);
  }

  @Test
  void testClone() {
    DevelopmentCard clonedCard = card.clone();
    assertEquals(clonedCard, clonedCard);
    assertNotSame(card, clonedCard);
  }

  @Test
  void testTurnOver() {
    assertFalse(card.isTurned());
    card.turnOver(0);
    assertTrue(card.isTurned());
    assertThrows(IllegalStateException.class, () -> card.turnOver(0));
  }

  @Test
  void testChangeState() throws IllegalMoveException {
    assertThrows(IllegalMoveException.class, () -> card.changeState(CardState.RESERVED));
    card.turnOver(0);
    card.changeState(CardState.RESERVED);
    assertEquals(CardState.RESERVED, card.getState());
  }

  @Test
  void testIsFree() throws IllegalMoveException {
    assertTrue(card.isFree());
    card.turnOver(0);
    card.changeState(CardState.RESERVED);
    assertFalse(card.isFree());
  }
  @Test
  void testConstructorWithStartingPosition() throws InvalidCardType {
    int position = 0;
    boolean isTurned = false;
    CardState state = CardState.FREE;

    DevelopmentCard cardWithPosition = new DevelopmentCard(meta, bonus, prestigePoints, price, position, isTurned, state);

    assertEquals(meta, cardWithPosition.getMeta());
    assertEquals(bonus, cardWithPosition.getBonus());
    assertEquals(prestigePoints, cardWithPosition.getPrestigePoints());
    assertEquals(price, cardWithPosition.getPrice());
    assertEquals(position, cardWithPosition.getPosition());
    assertEquals(isTurned, cardWithPosition.isTurned());
    assertEquals(state, cardWithPosition.getState());
  }

  @Test
  void testCopyConstructor() throws InvalidCardType, IllegalMoveException {
    card.turnOver(0);
    card.changeState(CardState.RESERVED);

    DevelopmentCard copiedCard = new DevelopmentCard(card);

    assertNotSame(card, copiedCard);
    assertEquals(card.isTurned(), copiedCard.isTurned());
    assertEquals(card.getState(), copiedCard.getState());
  }

  @Test
  void testGetMeta() {
    assertEquals(meta, card.getMeta());
  }

}
