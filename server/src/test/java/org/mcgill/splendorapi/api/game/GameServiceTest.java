package org.mcgill.splendorapi.api.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.config.RegistrationInformation;
import org.mcgill.splendorapi.connector.LobbyServiceApiService;
import org.mcgill.splendorapi.connector.TokenManager;
import org.mcgill.splendorapi.connector.model.Scope;
import org.mcgill.splendorapi.connector.model.Token;
import org.mcgill.splendorapi.connector.model.TokenType;
import org.mcgill.splendorapi.model.Player;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mcgill.splendorapi.api.assetloader.ProducerService;
import org.mcgill.splendorapi.api.game.dto.GameDto;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.GameServicesConfig;
import org.mcgill.splendorapi.connector.model.LauncherInfo;
import org.mcgill.splendorapi.connector.model.LsPlayer;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mcgill.splendorapi.api.game.GameService.waitersMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

  @Mock
  private GameMapper mapper;

  @Mock
  private GameLoaderService loader;

  @Mock
  private GameManagerService managerService;

  @Mock
  private ProducerService producerService;

  @Mock
  private AppProperties properties;

  @Mock
  private GameServicesConfig gameServices;

  @Mock
  private Game game;

  @Mock
  private LobbyServiceApiService ls;


  @Mock
  private TokenManager manager;

  private GameService gameService;


  @BeforeEach
  void setUp() throws NoSuchAlgorithmException {
    gameService = new GameService(mapper, loader, managerService, producerService,
                                  properties, gameServices, ls, manager);
  }

//  @Test
//  void testHandleTimeoutOrComplete() {
//    DeferredResult<GameDto> deferredResult = mock(DeferredResult.class);
//    List<DeferredResult<GameDto>> deferredResultsList = new ArrayList<>();
//    deferredResultsList.add(deferredResult);
//    GameDto gameDto = mock(GameDto.class);
//    String id = "1";
//    when(waitersMap.get(id)).thenReturn(deferredResultsList);
//    when(deferredResult.getResult()).thenReturn(gameDto);
//    gameService.handleTimeoutOrComplete(id, deferredResult);
//    verify(deferredResultsList).remove(deferredResult);
//    verify(deferredResult).setResult(null);
//  }

//  @Test
//  void testHandleUpdate() {
//    String id = "1";
//    DeferredResult<GameDto> deferredResult = mock(DeferredResult.class);
//    GameDto gameDto = mock(GameDto.class);
//    List<DeferredResult<GameDto>> deferredResultsList = new ArrayList<>();
//    deferredResultsList.add(deferredResult);
//    when(mapper.map(game)).thenReturn(gameDto);
//    when(waitersMap.compute(id, (key, value) -> value == null ? List.of() : value))
//      .thenReturn(deferredResultsList);
//    gameService.handleUpdate(id, game);
//    verify(deferredResult).setResult(gameDto);
//    verify(waitersMap).remove(id);
//  }

  @Test
  void testCreateWithSaveGameId() throws IOException, IllegalAccessException {
    String id = "1";
    GameType typeId = mock(GameType.class);
    String saveGameId = "2";
    LauncherInfo launcherInfo = LauncherInfo.builder().players(List.of(LsPlayer.builder().name("").build())).saveGameId(saveGameId).build();
    when(loader.loadGameFromDisk(saveGameId)).thenReturn(game);
    when(managerService.hasGame(id)).thenReturn(false);
    Game createdGame = gameService.create(id, typeId, launcherInfo);
    assertEquals(createdGame, game);
    verify(managerService).putGame(id, game);
  }

  @Test
  public void testCreateWithExistingGame() throws IOException, IllegalAccessException {
    String id = "1";
    GameType typeId = mock(GameType.class);
    LauncherInfo launcherInfo = mock(LauncherInfo.class);
    when(managerService.hasGame(id)).thenReturn(true);
    when(managerService.getGame(id)).thenReturn(game);
    Game createdGame = gameService.create(id, typeId, launcherInfo);
    assertEquals(createdGame, game);
    verifyNoMoreInteractions(loader, producerService);
  }

  @Test
  void testGetPlayers() {
    LsPlayer player1 = new LsPlayer("player1", "yellow");
    LsPlayer player2 = new LsPlayer("player2", "blue");
    List<LsPlayer> players = List.of(player1, player2);
    List<Player> expectedPlayers = new ArrayList<>();
    expectedPlayers.add(Player.builder().name(player1.getName()).playerPrestige(0).gems(new HashMap<>()).bonuses(new HashMap<>()).nobles(new ArrayList<>()).powers(new ArrayList<>()).reservedCards(new ArrayList<>()).reservedNobles(new ArrayList<>()).build());
    expectedPlayers.add(Player.builder().name(player2.getName()).playerPrestige(0).gems(new HashMap<>()).bonuses(new HashMap<>()).nobles(new ArrayList<>()).powers(new ArrayList<>()).reservedCards(new ArrayList<>()).reservedNobles(new ArrayList<>()).build());
    List<Player> actualPlayers = gameService.getPlayers(players);
    assertEquals(actualPlayers, actualPlayers);
  }

  @Test
  void testToMd5() throws JsonProcessingException {
    when(mapper.map(game)).thenReturn(mock(GameDto.class));
    String expectedMd5 = "d22c45f96f54b7fbae9a4de848ed4c2e";
    String actualMd5 = gameService.toMd5(game);
    assertEquals(expectedMd5, actualMd5);
  }

  @Test
  void testGetGameWithValidId() throws IllegalAccessException {
    String id = "1";
    when(managerService.hasGame(id)).thenReturn(true);
    when(managerService.getGame(id)).thenReturn(game);
    Game actualGame = gameService.getGame(id);
    assertEquals(game, actualGame);
  }

  @Test
  void testGetGameWithInvalidId() {
    String id = "1";
    when(managerService.hasGame(id)).thenReturn(false);
    assertThrows(IllegalAccessException.class, () -> gameService.getGame(id));
  }

  @Test
  public void testGetGameWithInvalidPlayer() throws IllegalAccessException {
    String id = "1";
    String user = "user1";
    String gameHash = "hash";
    when(managerService.hasGame(id)).thenReturn(true);
    when(managerService.getGame(id)).thenReturn(game);
    when(game.checkUserRights(user)).thenThrow(IllegalAccessException.class);
    assertThrows(IllegalAccessException.class, () -> gameService.getGame(id, user, gameHash));
  }

  @Test
  void testCreateWithSavedGameId() throws IOException, IllegalAccessException {
    String id = "1";
    GameType typeId = mock(GameType.class);
    LauncherInfo launcherInfo = LauncherInfo.builder()
                                            .saveGameId("123")
                                            .players(new ArrayList<>())
                                            .name("name")
                                            .creator("creator")
                                            .build();
    Game loadedGame = mock(Game.class);
    when(loader.loadGameFromDisk(launcherInfo.getSaveGameId())).thenReturn(loadedGame);
    Game actualGame = gameService.create(id, typeId, launcherInfo);
    assertEquals(loadedGame, actualGame);
    verify(managerService, times(1)).putGame(id, loadedGame);
  }

//  @Test
//  void testCreateWithNewGame() throws IOException, IllegalAccessException {
//    String id = "1";
//    GameType typeId = mock(GameType.class);
//    String gameName = "gameName";
//    String creator = "creator";
//    String gameServer = "gameServer";
//    List<LsPlayer> players = new ArrayList<>();
//    LauncherInfo launcherInfo = LauncherInfo.builder()
//                                            .players(players)
//                                            .name(gameName)
//                                            .creator(creator)
//                                            .build();
//    GameDto gameDto = mock(GameDto.class);
//    ProducerService producerService = mock(ProducerService.class);
//    Game expectedGame = Game.builder()
//                            .type(typeId)
//                            .players(List.of())
//                            .gameName(gameName)
//                            .creator(creator)
//                            .gameServer(gameServer)
//                            .build();
//    when(gameDto.getPlayers()).thenReturn(new ArrayList<>());
//    when(mapper.map(expectedGame)).thenReturn(gameDto);
//    when(gameServices.getBaseHost()).thenReturn(gameServer);
//    when(properties.getMaxTimeout()).thenReturn(100);
//    when(mapper.map(expectedGame)).thenReturn(gameDto);
//    when(mapper.map(game)).thenReturn(gameDto);
//    Game actualGame = gameService.create(id, typeId, launcherInfo);
//    assertEquals(expectedGame, actualGame);
//    verify(game, times(1)).init(producerService);
//    verify(managerService, times(1)).putGame(id, expectedGame);
//  }
  @Test
  void testGetGame() throws IllegalAccessException {
    String id = "1";
    when(managerService.hasGame(id)).thenReturn(true);
    when(managerService.getGame(id)).thenReturn(game);
    Game actualGame = gameService.getGame(id);
    assertEquals(game, actualGame);
  }

  @Test
  void testGetGameThrowsIllegalAccessException() throws IllegalAccessException {
    String id = "1";
    when(managerService.hasGame(id)).thenReturn(false);
    assertThrows(IllegalAccessException.class, () -> gameService.getGame(id));
  }

//  @Test
//  void testGetGameWithSameHash() throws IOException, IllegalAccessException {
//    String id = "1";
//    String user = "user";
//    String gameHash = "someHash";
//    GameDto gameDto = mock(GameDto.class);
//    when(game.checkUserRights(user)).thenReturn(game);
//    when(mapper.map(game)).thenReturn(gameDto);
//    when(properties.getMaxTimeout()).thenReturn(100);
//    DeferredResult<GameDto> expectedDeferredResult = new DeferredResult<>(100L);
//    expectedDeferredResult.setResult(gameDto);
//    DeferredResult<GameDto> actualDeferredResult = gameService.getGame(id, user, gameHash);
//    assertEquals(actualDeferredResult.getResult(), expectedDeferredResult.getResult());
//  }

  @Test
  void testSaveGame() throws IllegalAccessException, IOException {
    String sessionId = "1";
    when(managerService.hasGame(sessionId)).thenReturn(true);
    when(properties.getRegistrationInformation()).thenReturn(List.of(new RegistrationInformation("", "", GameType.BASE)));
    when(managerService.getGame(sessionId)).thenReturn(game);
    when(manager.token()).thenReturn(new Token(TokenType.BEARER, Scope.builder().build(), "", 1, ""));
    doNothing().when(ls).saveSessionService(any(), any(), any(), any());

    gameService.saveGame(GameType.BASE, sessionId);
    verify(loader).saveGame(argThat(g -> g == game), any());
  }

  @Test
  void testSaveGameWithInvalidId() throws IllegalAccessException, IOException {
    String sessionId = "1";
    when(managerService.hasGame(sessionId)).thenReturn(false);
    assertThrows(IllegalAccessException.class,
                 () -> gameService.saveGame(GameType.BASE, sessionId));
  }

  @Test
  void testKickOut() {
    String sessionId = "1";
    Player player = mock(Player.class);
    Game game = mock(Game.class);
    when(managerService.hasGame(sessionId)).thenReturn(true);
    when(managerService.getGame(sessionId)).thenReturn(game);
    gameService.kickOut(sessionId, player);
    verify(game).kickOut(player);
  }
}

