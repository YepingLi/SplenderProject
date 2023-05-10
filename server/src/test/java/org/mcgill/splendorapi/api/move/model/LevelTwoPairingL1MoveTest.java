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
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

class LevelTwoPairingL1MoveTest {
  private LevelTwoPairingL1Move move;
  private Game game;
  private Player player;
  private DevelopmentCard card;
  private DevelopmentCard lvlOne;
  private Bonus pairing;
  private Bonus lvlOnePairing;

  @BeforeEach
  void setUp() throws InvalidCardType {
    card = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                           CardType.DEVELOPMENT),
                                   Bonus.SAPPHIRE, 3,
                                   new HashMap<>());
    lvlOne = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                         CardType.DEVELOPMENT),
                                 Bonus.EMERALD, 3,
                                 new HashMap<>());
    pairing = Bonus.EMERALD;
    lvlOnePairing = Bonus.SAPPHIRE;

    move = new LevelTwoPairingL1Move(card, lvlOne, pairing, lvlOnePairing);
    game = Game.builder().build();
    player = Player.builder().name("player1").build();
  }

  @Test
  void copyTest() {
    LevelTwoPairingL1Move copiedMove = (LevelTwoPairingL1Move) move.copy();
    assertEquals(move.getCard(), copiedMove.getCard());
    assertEquals(move.getLevelOneCard(), copiedMove.getLevelOneCard());
    assertEquals(move.getLevelOnePairing(), copiedMove.getLevelOnePairing());
    assertEquals(move.getLevelOnePairing(), copiedMove.getLevelOnePairing());
  }

}
