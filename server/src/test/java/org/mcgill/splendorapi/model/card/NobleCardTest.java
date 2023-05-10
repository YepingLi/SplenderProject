package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NobleCardTest {
  private CardMeta meta;
  private int position;
  private int prestigePoints;
  private Map<Bonus, Integer> price;

  @BeforeEach
  public void setUp() {
    meta = new CardMeta((short) 1, CardType.NOBLE);
    position = 1;
    prestigePoints = 3;
    price = new HashMap<>();
  }

  @Test
  public void testConstructor() throws InvalidCardType {
    NobleCard nobleCard = new NobleCard(meta, position, prestigePoints, price);
    assertEquals(meta, nobleCard.getMeta());
    assertEquals(-1, nobleCard.getPosition());
    assertEquals(prestigePoints, nobleCard.getPrestigePoints());
    assertEquals(price, nobleCard.getPrice());
  }

  @Test
  public void testCopyConstructor() throws InvalidCardType {
    NobleCard original = new NobleCard(meta, position, prestigePoints, price);
    NobleCard copy = new NobleCard(original);
    assertEquals(original.getMeta(), copy.getMeta());
    assertEquals(original.getPosition(), copy.getPosition());
    assertEquals(original.getPrestigePoints(), copy.getPrestigePoints());
    assertEquals(original.getPrice(), copy.getPrice());
  }

  @Test
  public void testChangeState() throws InvalidCardType, IllegalMoveException {
    NobleCard nobleCard = new NobleCard(meta, position, prestigePoints, price);
    CardState newState = CardState.RESERVED;
    nobleCard.changeState(newState);
    assertEquals(newState, nobleCard.getState());
  }

  @Test
  public void testIllegalMoveException() throws InvalidCardType {
    NobleCard nobleCard = new NobleCard(meta, position, prestigePoints, price);
  }

  @Test
  public void testSetPosition() throws InvalidCardType {
    NobleCard nobleCard = new NobleCard(meta, position, prestigePoints, price);
    int newPosition = 5;
    nobleCard.setPosition(newPosition);
    assertEquals(newPosition, nobleCard.getPosition());
  }

  @Test
  public void testClone() throws InvalidCardType {
    NobleCard nobleCard = new NobleCard(meta, position, prestigePoints, price);
    NobleCard cloned = nobleCard.clone();
    assertEquals(nobleCard.getMeta(), cloned.getMeta());
    assertEquals(nobleCard.getPosition(), cloned.getPosition());
    assertEquals(nobleCard.getPrestigePoints(), cloned.getPrestigePoints());
    assertEquals(nobleCard.getPrice(), cloned.getPrice());
  }
}

