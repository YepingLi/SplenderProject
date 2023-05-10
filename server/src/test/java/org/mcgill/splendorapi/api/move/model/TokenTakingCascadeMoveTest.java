package org.mcgill.splendorapi.api.move.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

class TokenTakingCascadeMoveTest {
  Player testPlayer1;

  public void setup() throws InvalidCardType {
    Map<GemType, Integer> playMap = new HashMap<>();
    playMap.put(GemType.RUBY, 0);
    playMap.put(GemType.SAPPHIRE, 0);
    playMap.put(GemType.ONYX, 0);
    playMap.put(GemType.DIAMOND, 2);
    testPlayer1 = Player.builder().gems(playMap).build();
  }

  @Test
  void applyMove() throws InvalidCardType, IllegalMoveException, GameBoardException {
    setup();
    Map<GemType, Integer> boardGems = new HashMap<>();
    boardGems.put(GemType.GOLD, 0);
    boardGems.put(GemType.SAPPHIRE, 1);
    boardGems.put(GemType.ONYX, 1);
    boardGems.put(GemType.DIAMOND, 0);
    boardGems.put(GemType.RUBY, 1);
    boardGems.put(GemType.EMERALD, 0);

    GameBoard testBoard = GameBoard.builder().gems(boardGems).build();
    Game game = Game.builder().curPlayer(testPlayer1).board(testBoard).build();
    Move testMove = new TokenTakingCascadeMove(List.of(GemType.ONYX, GemType.SAPPHIRE, GemType.RUBY),
                                               List.of(GemType.DIAMOND, GemType.DIAMOND));
    testMove.applyMove(game);

    assertEquals(1, testPlayer1.getGems().get(GemType.SAPPHIRE));
    assertEquals(1, testPlayer1.getGems().get(GemType.ONYX));
    assertEquals(1, testPlayer1.getGems().get(GemType.RUBY));
    assertEquals(0, testPlayer1.getGems().get(GemType.DIAMOND));


    assertEquals(0, testBoard.getGems().get(GemType.RUBY));
    assertEquals(0, testBoard.getGems().get(GemType.SAPPHIRE));
    assertEquals(0, testBoard.getGems().get(GemType.ONYX));
    assertEquals(2, testBoard.getGems().get(GemType.DIAMOND));

  }

  @Test
  void testToString() throws InvalidCardType {
    setup();
    Map<GemType, Integer> boardGems = new HashMap<>();
    boardGems.put(GemType.GOLD, 0);
    boardGems.put(GemType.SAPPHIRE, 1);
    boardGems.put(GemType.ONYX, 1);
    boardGems.put(GemType.DIAMOND, 0);
    boardGems.put(GemType.RUBY, 1);
    boardGems.put(GemType.EMERALD, 0);
    GameBoard testBoard = GameBoard.builder().gems(boardGems).build();
    Game game = Game.builder().curPlayer(testPlayer1).board(testBoard).build();
    Move testMove = new TokenTakingCascadeMove(List.of(GemType.ONYX, GemType.SAPPHIRE, GemType.RUBY),
                                               List.of(GemType.DIAMOND, GemType.DIAMOND));
    assertEquals("TokenTakingCascadeMove{aMove=[ONYX, SAPPHIRE, RUBY][DIAMOND, DIAMOND]}",
                 testMove.toString());
  }

  @Test
  void getGemTypeListToBurn() throws InvalidCardType {
    setup();
    Map<GemType, Integer> boardGems = new HashMap<>();
    boardGems.put(GemType.GOLD, 0);
    boardGems.put(GemType.SAPPHIRE, 1);
    boardGems.put(GemType.ONYX, 1);
    boardGems.put(GemType.DIAMOND, 0);
    boardGems.put(GemType.RUBY, 1);
    boardGems.put(GemType.EMERALD, 0);

    GameBoard testBoard = GameBoard.builder().gems(boardGems).build();
    Game game = Game.builder().curPlayer(testPlayer1).board(testBoard).build();
    Move testMove = new TokenTakingCascadeMove(List.of(GemType.ONYX, GemType.SAPPHIRE, GemType.RUBY),
                                               List.of(GemType.DIAMOND, GemType.DIAMOND));
    TokenTakingCascadeMove move = (TokenTakingCascadeMove) testMove;
    assertEquals("[DIAMOND, DIAMOND]", move.getGemTypeListToBurn().toString());
  }
}