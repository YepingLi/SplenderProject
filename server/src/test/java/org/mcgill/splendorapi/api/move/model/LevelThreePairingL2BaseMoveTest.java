package org.mcgill.splendorapi.api.move.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;

class LevelThreePairingL2BaseMoveTest {
  private LevelThreePairingL2BaseMove move;
  private DevelopmentCard card;
  private DevelopmentCard cascade;

  @BeforeEach
  void setUp() {
    card = mock(DevelopmentCard.class);
    cascade = mock(DevelopmentCard.class);
    move = new LevelThreePairingL2BaseMove(card, cascade);
  }

  @Test
  void testSimulateMove() {
    Player player = mock(Player.class);
    when(card.getBonus()).thenReturn(Bonus.SAPPHIRE);
    when(cascade.getBonus()).thenReturn(Bonus.RUBY);

    Map<Bonus, Integer> result = move.simulateMove(player);

    assertEquals(1, result.get(Bonus.SAPPHIRE));
    assertEquals(1, result.get(Bonus.RUBY));
  }

  @Test
  void testSimulateMove_OrientDevelopmentCard() {
    Player player = mock(Player.class);
    OrientDevelopmentCard orientCascade = mock(OrientDevelopmentCard.class);

    when(card.getBonus()).thenReturn(Bonus.SAPPHIRE);
    when(orientCascade.getBonus()).thenReturn(Bonus.RUBY);

    LevelThreePairingL2BaseMove orientMove = new LevelThreePairingL2BaseMove(card, orientCascade);

    Map<Bonus, Integer> result = orientMove.simulateMove(player);

    assertEquals(1, result.get(Bonus.SAPPHIRE));
    assertEquals(2, result.get(Bonus.RUBY));
  }


  @Test
  void testCopy() {
    LevelThreePairingL2BaseMove copy = move.copy();

    assertEquals(move.getCard(), copy.getCard());
    assertEquals(move.getLevelTwoCascade(), copy.getLevelTwoCascade());
  }
}
