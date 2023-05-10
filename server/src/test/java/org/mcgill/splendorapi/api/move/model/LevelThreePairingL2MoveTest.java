package org.mcgill.splendorapi.api.move.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

public class LevelThreePairingL2MoveTest {
  private LevelThreePairingL2Move move;
  private DevelopmentCard card;
  private DevelopmentCard lvlOne;
  private Bonus pairing;
  private DevelopmentCard cascade;

  @BeforeEach
  public void setUp() {
    card = mock(DevelopmentCard.class);
    lvlOne = mock(DevelopmentCard.class);
    pairing = mock(Bonus.class);
    cascade = mock(DevelopmentCard.class);
    move = new LevelThreePairingL2Move(card, lvlOne, pairing, cascade);
  }

  @Test
  public void testSimulateMove() {
    Player player = mock(Player.class);
    Map<Bonus, Integer> result = move.simulateMove(player);
    assertEquals(move.simulateMove(player), result, "SimulateMove should return the correct result");
  }
  @Test
  public void testCopy() {
    LevelThreePairingL2Move copiedMove = (LevelThreePairingL2Move) move.copy();
    assertEquals(move.getCard(), copiedMove.getCard(), "Copied move should have the same card");
    assertEquals(move.getLevelOneCard(), copiedMove.getLevelOneCard(), "Copied move should have the same lvlOne");
    assertEquals(move.getLevelTwoCascade(), copiedMove.getLevelTwoCascade(), "Copied move should have the same levelTwoCascade");
  }
}
