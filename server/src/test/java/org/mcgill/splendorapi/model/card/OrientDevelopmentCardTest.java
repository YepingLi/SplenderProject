package org.mcgill.splendorapi.model.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrientDevelopmentCardTest {

  private OrientDevelopmentCard card;
  private DevelopmentCardMeta meta;
  private Bonus bonus = Bonus.EMERALD;
  private OrientPower power;
  private int prestigePoints;
  private Map<GemType, Integer> price;
  private int position;
  private boolean turned;
  private CardState state;

  @BeforeEach
  void setUp() {
    meta = new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT);
    power = OrientPower.DISCARD_SAPPHIRE;
    prestigePoints = 3;
    price = new HashMap<>();
    price.put(GemType.DIAMOND, 1);
    position = 0;
    turned = false;
    state = CardState.RESERVED;

    try {
      card = new OrientDevelopmentCard(meta, bonus, power, prestigePoints, price, position, turned, state);
    } catch (InvalidCardType e) {
      fail("Exception while creating OrientDevelopmentCard", e);
    }
  }

  @Test
  public void testConstructor() {
    assertEquals(meta, card.getMeta());
    assertEquals(bonus, card.getBonus());
    assertEquals(power, card.getPower());
    assertEquals(prestigePoints, card.getPrestigePoints());
    assertEquals(price, card.getPrice());
    assertEquals(position, card.getPosition());
    assertEquals(turned, card.isTurned());
    assertEquals(state, card.getState());
  }

  @Test
  public void testCopyConstructor() throws InvalidCardType {
    OrientDevelopmentCard copiedCard = new OrientDevelopmentCard(card);
    assertEquals(copiedCard, copiedCard);
  }

  @Test
  public void testTurnOver() {
    card.turnOver(1);
    assertTrue(card.isTurned());
    assertEquals(1, card.getPosition());
  }

  @Test
  public void testTurnOverThrowsExceptionWhenAlreadyTurned() {
    card.turnOver(1);
    assertThrows(IllegalStateException.class, () -> card.turnOver(2));
  }

  @Test
  public void testClone() {
    OrientDevelopmentCard clonedCard = card.clone();
    assertEquals(card.position, clonedCard.position);
  }

}