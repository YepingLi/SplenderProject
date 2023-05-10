package org.mcgill.splendorapi.api.move.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.card.DevelopmentCard;

class LevelTwoPairingMoveTest {
  private LevelTwoPairingMove move;
  private DevelopmentCard levelTwoCard;
  private DevelopmentCard levelOneCard;
  private Bonus levelTwoPairing;
  private GameBoard gameBoard;
  private Player player;

  @BeforeEach
  void setUp() {
    Map<GemType, Integer> playMap = new HashMap<>();
    playMap.put(GemType.RUBY, 0);
    playMap.put(GemType.SAPPHIRE, 0);
    playMap.put(GemType.ONYX, 0);
    levelTwoCard = mock(DevelopmentCard.class);
    levelOneCard = mock(DevelopmentCard.class);
    levelTwoPairing = mock(Bonus.class);
    player = Player.builder().gems(playMap).build();
    gameBoard = GameBoard.builder().build();
    move = new LevelTwoPairingMove(levelTwoCard, levelOneCard, levelTwoPairing);
  }

  @Test
  void testSimulateMove() {
    Player player1 = mock(Player.class);
    Map<Bonus, Integer> simulatedMove = move.simulateMove(player1);

    assertEquals(simulatedMove, move.simulateMove(player1));
  }

//  @Test
//  void testApplyMove() throws Exception {
//
//    Map<GemType, Integer> boardGems = new HashMap<>();
//    boardGems.put(GemType.GOLD, 0);
//    boardGems.put(GemType.SAPPHIRE, 1);
//    boardGems.put(GemType.ONYX, 1);
//    boardGems.put(GemType.DIAMOND, 0);
//    boardGems.put(GemType.RUBY, 1);
//    boardGems.put(GemType.EMERALD, 0);
//    Player player = Player.builder().gems(boardGems).build();
//    GameBoard testBoard = GameBoard.builder().gems(boardGems).build();
//    Game game = Game.builder().curPlayer(player).board(testBoard).build();
//
//    move.applyMove(game, player);
//
//    verify(player).addBonus(levelTwoPairing);
//    verify(player).addFreeCard(levelOneCard);
//  }

  @Test
  void testCopy() {
    LevelTwoPairingMove copiedMove = (LevelTwoPairingMove) move.copy();

    assertEquals(move.getLevelOneCard(), copiedMove.getLevelOneCard());
    assertEquals(move.getLevelTwoPairing(), copiedMove.getLevelTwoPairing());
  }
}
