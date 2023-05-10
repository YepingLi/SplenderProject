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

class LevelThreePairingL2CascadeMoveTest {

  private DevelopmentCard card;
  private DevelopmentCard lvlOne;
  private DevelopmentCard lvlTwoCascade;
  private Bonus pairing;
  private Bonus lvlOnePairing;
  private Player player;
  private Game game;

  @BeforeEach
  void setUp() {
    card = mock(DevelopmentCard.class);
    lvlOne = mock(DevelopmentCard.class);
    lvlTwoCascade = mock(DevelopmentCard.class);
    pairing = mock(Bonus.class);
    lvlOnePairing = mock(Bonus.class);
    player = mock(Player.class);
    game = mock(Game.class);

    when(card.getBonus()).thenReturn(Bonus.SAPPHIRE);
    when(lvlOne.getBonus()).thenReturn(Bonus.RUBY);
    when(lvlTwoCascade.getBonus()).thenReturn(Bonus.ONYX);
  }

  @Test
  void testSimulateMove() {
    LevelThreePairingL2CascadeMove move = new LevelThreePairingL2CascadeMove(card, lvlOne, pairing, lvlOnePairing, lvlTwoCascade);
    Map<Bonus, Integer> result = move.simulateMove(player);

    assertEquals(5, result.size());
    assertEquals(1, result.get(Bonus.SAPPHIRE).intValue());
    assertEquals(1, result.get(Bonus.RUBY).intValue());
    assertEquals(1, result.get(Bonus.ONYX).intValue());
  }

  @Test
  void testCopy() {
    LevelThreePairingL2CascadeMove move = new LevelThreePairingL2CascadeMove(card, lvlOne, pairing, lvlOnePairing, lvlTwoCascade);
    LevelThreePairingL2CascadeMove copiedMove = (LevelThreePairingL2CascadeMove) move.copy();

    assertEquals(move.getCard(), copiedMove.getCard());
    assertEquals(move.getLevelOneCard(), copiedMove.getLevelOneCard());
    assertEquals(move.getLevelOnePairing(), copiedMove.getLevelOnePairing());
    assertEquals(move.getLevelTwoCascade(), copiedMove.getLevelTwoCascade());
  }
}
