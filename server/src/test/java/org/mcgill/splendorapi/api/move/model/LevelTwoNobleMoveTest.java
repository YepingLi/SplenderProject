package org.mcgill.splendorapi.api.move.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.card.*;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

public class LevelTwoNobleMoveTest {

  private LevelTwoNobleMove levelTwoNobleMove;
  private DevelopmentCard developmentCard;
  private NobleCard nobleCard;
  private Game game;
  private Player player;

  @BeforeEach
  public void setUp() throws InvalidCardType {
    developmentCard = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 2,
                                                                         CardType.DEVELOPMENT),
                                                 Bonus.EMERALD, 3,
                                                 new HashMap<>());
    nobleCard = new NobleCard(new CardMeta((short)1, CardType.NOBLE), 5, 5, new HashMap<>());
    levelTwoNobleMove = new LevelTwoNobleMove(developmentCard, nobleCard);
    game = Game.builder().build();
    player = Player.builder().build();
  }


  @Test
  public void testCopy() {
    LevelTwoNobleMove copiedMove = (LevelTwoNobleMove) levelTwoNobleMove.copy();
    assertEquals(levelTwoNobleMove.getReservedNoble(), copiedMove.getReservedNoble());
    assertEquals(levelTwoNobleMove.getCard(), copiedMove.getCard());
  }
}
