package org.mcgill.splendorapi.api.move;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.api.game.GameManagerService;
import org.mcgill.splendorapi.api.game.GameService;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

class MoveManagerServiceTest {

  @Test
  void buildMoves() {
  }

  @Test
  void testBuildMoves() {
  }

  @Test
  void performMove() {
  }

  @Test
  void endGame() {
  }

  @Test
  void isGameOver() throws InvalidCardType, NoSuchAlgorithmException, IllegalAccessException {

    GameBoard testBoard =  GameBoard.builder().gems(Map.of()).nobles(new ArrayList<>()).deck(new ArrayList<>()).build();
    Map<String, Game> gameMapping = new HashMap<>();
    List<Player> testPlayerList = new ArrayList<>();
    Player testPlayer1 = Player.builder().build();
    testPlayerList.add(testPlayer1);
    Game testGame = Game
      .builder()
      .board(testBoard)
      .curPlayer(testPlayer1)
      .players(testPlayerList)
      .terminating(true).build();
    gameMapping.put("1", testGame);
    GameManagerService
      testGameManagerService = GameManagerService.builder().gameMapping(gameMapping).build();
    GameService testGameService = new GameService(null, null, testGameManagerService,
                                                  null, null, null, null, null);
    MoveManagerService testMoveManagerService = new MoveManagerService(testGameService, null);
    assertTrue(testMoveManagerService.isGameOver(testMoveManagerService.getGameService().getGame("1")));

    // Game is not over case
    testPlayerList.add(testPlayer1);
    Game testGame1 = Game
      .builder()
      .board(testBoard)
      .curPlayer(testPlayer1)
      .players(testPlayerList)
      .terminating(false).build();
    gameMapping.put("1", testGame1);
    GameManagerService testGameManagerService1 = GameManagerService.builder().gameMapping(gameMapping).build();
    GameService testGameService1 = new GameService(null, null, testGameManagerService1,
                                                  null, null, null, null, null);
    MoveManagerService testMoveManagerService1 = new MoveManagerService(testGameService1,
                                                                        null);
    assertFalse(testMoveManagerService1.isGameOver(testMoveManagerService1.getGameService().getGame("1")));
  }
}