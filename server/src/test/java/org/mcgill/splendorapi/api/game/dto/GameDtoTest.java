package org.mcgill.splendorapi.api.game.dto;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.Player;

@ExtendWith(MockitoExtension.class)
class GameDtoTest {

  @Test
  void testGameDtoBuilder() {
    Player p1 = Player.builder().name("bcd").playerPrestige(15).build();
    Player p2 = Player.builder().name("bcd").playerPrestige(15).build();
    List<Player> players = Arrays.asList(p1, p2);
    List<DevelopmentCard> faceUpCards = new ArrayList<>();
    Map<GemType, Integer> availableGems = new HashMap<>();
    List<NobleCard> nobles = new ArrayList<>();
    BaseGameBoardDto board = new BaseGameBoardDto(1, 2, 3, faceUpCards, availableGems, nobles);
    String gameName = "testGame";
    String creator = "testCreator";
    String gameServer = "testServer";
    GameType type = GameType.CITIES_ORIENT;
    boolean launched = true;
    Player curPlayer = Player.builder().name("curPlayer").playerPrestige(15).build();
    boolean isOver = false;

    GameDto gameDto = GameDto.builder()
                             .players(players)
                             .board(board)
                             .gameName(gameName)
                             .creator(creator)
                             .gameServer(gameServer)
                             .type(type)
                             .launched(launched)
                             .curPlayer(curPlayer)
                             .isOver(isOver)
                             .build();

    assertNotNull(gameDto);
    assertEquals(players, gameDto.getPlayers());
    assertEquals(board, gameDto.getBoard());
    assertEquals(gameName, gameDto.getGameName());
    assertEquals(creator, gameDto.getCreator());
    assertEquals(gameServer, gameDto.getGameServer());
    assertEquals(type, gameDto.getType());
    assertTrue(gameDto.isLaunched());
    assertEquals(curPlayer, gameDto.getCurPlayer());
    assertFalse(gameDto.isOver());
  }

  @Test
  void testGameDtoGetterSetter() {
    Player p1 = Player.builder().name("bcd").playerPrestige(15).build();
    Player p2 = Player.builder().name("bcd").playerPrestige(15).build();
    List<Player> players = Arrays.asList(p1, p2);
    List<DevelopmentCard> faceUpCards = new ArrayList<>();
    Map<GemType, Integer> availableGems = new HashMap<>();
    List<NobleCard> nobles = new ArrayList<>();
    BaseGameBoardDto board = new BaseGameBoardDto(1, 2, 3, faceUpCards, availableGems, nobles);
    String gameName = "testGame";
    String creator = "testCreator";
    String gameServer = "testServer";
    GameType type = GameType.CITIES_ORIENT;
    boolean launched = true;
    Player curPlayer = Player.builder().name("bcd").playerPrestige(15).build();
    boolean isOver = false;

    GameDto gameDto = new GameDto(players, gameName, board, creator, gameServer, type, launched, curPlayer, isOver);
    String newGameName = "newTestGame";
    String newCreator = "newTestCreator";
    String newGameServer = "newTestServer";
    GameType newType = GameType.BASE;
    boolean newLaunched = false;
    Player newCurPlayer = Player.builder().name("bcd").playerPrestige(15).build();
    boolean newIsOver = true;

    assertNotEquals(newGameName, gameDto.getGameName());
    assertNotEquals(newCreator, gameDto.getCreator());
    assertNotEquals(newGameServer, gameDto.getGameServer());
    assertNotEquals(newType, gameDto.getType());
    assertTrue(gameDto.isLaunched());
    assertNotEquals(newCurPlayer, gameDto.getCurPlayer());
    assertFalse(gameDto.isOver());
  }

  @Test
  void testGameDtoToString() {
    Player p1 = Player.builder().name("bcd").playerPrestige(15).build();
    Player p2 = Player.builder().name("bcd").playerPrestige(15).build();
    List<Player> players = Arrays.asList(p1, p2);
    List<DevelopmentCard> faceUpCards = new ArrayList<>();
    Map<GemType, Integer> availableGems = new HashMap<>();
    List<NobleCard> nobles = new ArrayList<>();
    BaseGameBoardDto board = new BaseGameBoardDto(1, 2, 3, faceUpCards, availableGems, nobles);
    String gameName = "testGame";
    String creator = "testCreator";
    String gameServer = "testServer";
    GameType type = GameType.CITIES_ORIENT;
    boolean launched = true;
    Player curPlayer = Player.builder().build();
    boolean isOver = false;
    GameDto gameDto = new GameDto(players, gameName, board, creator, gameServer, type, launched, curPlayer, isOver);

    String expected = "GameDto(players=[Player(name=player1), Player(name=player2)], gameName=testGame, board=BaseGameBoardDto(players=[null, null, null, null, null], nobles=null, decks=null, gems=null), creator=testCreator, gameServer=testServer, type=STANDARD, launched=true, curPlayer=Player(name=player1), isOver=false)";
    assertNotEquals(expected, gameDto.toString());
  }
}