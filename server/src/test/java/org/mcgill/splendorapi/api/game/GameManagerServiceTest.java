package org.mcgill.splendorapi.api.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

class GameManagerServiceTest {

  Player testPlayer1;

  public void setup() throws InvalidCardType {
    Map<GemType, Integer> playMap = new HashMap<>();
    playMap.put(GemType.RUBY, 0);
    playMap.put(GemType.SAPPHIRE, 0);
    playMap.put(GemType.ONYX, 0);
    testPlayer1 = Player.builder().gems(playMap).build();
  }

  @Test
  void putGame() throws InvalidCardType {
    setup();
    Map<GemType, Integer> gemMap1 = new HashMap();
    gemMap1.put(GemType.RUBY, 1);
    gemMap1.put(GemType.SAPPHIRE, 1);
    gemMap1.put(GemType.ONYX, 1);

    Map<GemType, Integer> boardGems = new HashMap<>();
    boardGems.put(GemType.GOLD, 0);
    boardGems.put(GemType.SAPPHIRE, 1);
    boardGems.put(GemType.ONYX, 1);
    boardGems.put(GemType.DIAMOND, 0);
    boardGems.put(GemType.RUBY, 1);
    boardGems.put(GemType.EMERALD, 0);

    GameBoard testBoard = GameBoard.builder().gems(boardGems).build();
    Game game = Game.builder().curPlayer(testPlayer1).board(testBoard).build();

    GameManagerService GameManagerServiceTest = GameManagerService.builder().gameMapping(new HashMap<>()).build();
    GameManagerServiceTest.putGame("test", game);

    assertSame(game, GameManagerServiceTest.getGameMapping().get("test"));
  }

  @Test
  void getAndRemove() throws InvalidCardType {
    setup();
    Map<GemType, Integer> gemMap1 = new HashMap();
    gemMap1.put(GemType.RUBY, 1);
    gemMap1.put(GemType.SAPPHIRE, 1);
    gemMap1.put(GemType.ONYX, 1);

    Map<GemType, Integer> boardGems = new HashMap<>();
    boardGems.put(GemType.GOLD, 0);
    boardGems.put(GemType.SAPPHIRE, 1);
    boardGems.put(GemType.ONYX, 1);
    boardGems.put(GemType.DIAMOND, 0);
    boardGems.put(GemType.RUBY, 1);
    boardGems.put(GemType.EMERALD, 0);

    GameBoard testBoard = GameBoard.builder().gems(boardGems).build();
    Game game = Game.builder().curPlayer(testPlayer1).board(testBoard).build();

    GameManagerService GameManagerServiceTest = GameManagerService.builder().gameMapping(new HashMap<>()).build();
    GameManagerServiceTest.putGame("test", game);
    assertSame(game, GameManagerServiceTest.getAndRemove("test"));
    assertEquals(0, GameManagerServiceTest.getGameMapping().size());

  }
}