package org.mcgill.splendorapi.api.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.api.assetloader.ProducerService;
import org.mcgill.splendorapi.api.game.dto.GameDto;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.GameServicesConfig;
import org.mcgill.splendorapi.connector.LobbyServiceApiService;
import org.mcgill.splendorapi.connector.TokenManager;
import org.mcgill.splendorapi.connector.model.LauncherInfo;
import org.mcgill.splendorapi.connector.model.LsPlayer;
import org.mcgill.splendorapi.connector.model.SaveGameRequest;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.Player;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * GameService.
 */
@Slf4j
@Service
public class GameService {
  private static final ObjectMapper stringMapper = new ObjectMapper();
  private final GameMapper mapper;
  private final GameLoaderService loader;
  private final GameManagerService managerService;
  private final ProducerService producerService;
  private final AppProperties properties;
  private final GameServicesConfig gameServices;
  private final MessageDigest mdDigester;
  private final LobbyServiceApiService lobbyServiceApiService;

  private final TokenManager tokenManager;
  static final Map<String, List<DeferredResult<GameDto>>> waitersMap = new HashMap<>();


  /**
   * The GameService for the whole game.
   *
   * @param mapper          the GameMapper
   * @param loader          the GameLoaderService
   * @param managerService  the GameManagerService
   * @param producerService the ProducerService
   * @param properties      the AppProperties
   * @param gameServices    the GameServicesConfig
   * @throws NoSuchAlgorithmException the exception of NoSuchAlgorithmException
   */

  public GameService(GameMapper mapper, GameLoaderService loader,
                     GameManagerService managerService,
                     ProducerService producerService,
                     AppProperties properties,
                     GameServicesConfig gameServices,
                     LobbyServiceApiService lobbyServiceApiService,
                     TokenManager tokenManager) throws NoSuchAlgorithmException {
    this.mapper = mapper;
    this.loader = loader;
    this.managerService = managerService;
    this.producerService = producerService;
    this.properties = properties;
    this.gameServices = gameServices;
    mdDigester = MessageDigest.getInstance("MD5");
    this.lobbyServiceApiService = lobbyServiceApiService;
    this.tokenManager = tokenManager;
  }


  /**
   * Create the runnable for the on complete and on timeout actions.
   *
   * @param id        The id of the game
   * @param waitingOn The deferred object we are waiting on
   */
  public synchronized void handleTimeoutOrComplete(String id, DeferredResult<GameDto> waitingOn) {
    Optional.of(waitersMap.get(id))
            .ifPresent((list) -> {
              list.remove(waitingOn);
              if (list.isEmpty()) {
                waitersMap.remove(id);
              }
            });
  }

  /**
   * Handle the game change.
   *
   * @param id      The id of the game
   * @param updated the newly updated game
   */
  public synchronized void handleUpdate(String id, Game updated) {
    GameDto mapped = mapper.map(updated);
    waitersMap.compute(id, (key, value) -> value == null ? List.of() : value)
              .forEach(res -> res.setResult(mapped));
    waitersMap.remove(id);
  }

  /**
   * Create the game from either a configuration in the past or new game entirely.
   *
   * @param id           The session id
   * @param launcherInfo The information received during launch
   * @return the Game
   * @throws IOException            If we cant find the game
   * @throws IllegalAccessException If we cant find the game
   */
  public Game create(String id, GameType typeId,
                     LauncherInfo launcherInfo) throws IOException, IllegalAccessException {
    synchronized (managerService) {
      if (managerService.hasGame(id)) {
        return managerService.getGame(id);
      }
    }

    if (launcherInfo.hasId()) {
      Game loaded = loader.loadGameFromDisk(launcherInfo.getSaveGameId());
      managerService.putGame(id, loaded);
      Set<String> gameUsernames = loaded.getPlayers()
                                        .stream()
                                        .map(Player::getName)
                                        .collect(Collectors.toSet());
      gameUsernames.removeAll(launcherInfo.getPlayers()
                                          .stream()
                                          .map(LsPlayer::getName)
                                          .collect(Collectors.toList()));
      if (gameUsernames.isEmpty()) {
        return loaded;
      }
      List<Player> players = getPlayers(launcherInfo.getPlayers());
      loaded.setPlayers(players);
      return loaded;
    }

    List<Player> players = getPlayers(launcherInfo.getPlayers());
    Game game = Game.builder()
                    .type(typeId)
                    .players(players)
                    .gameName(launcherInfo.getName())
                    .creator(launcherInfo.getCreator())
                    .gameServer(gameServices.getBaseHost())
                    .build();
    game.init(producerService);
    managerService.putGame(id, game);
    return game;
  }

  /**
   * Create the players from the objects in the list of player names.
   *
   * @param players The player names to start the game
   * @return The player objects built for the start of the game
   */
  public List<Player> getPlayers(List<LsPlayer> players) {
    return players.stream()
                  .map(player -> Player.builder()
                                       .name(player.getName())
                                       .playerPrestige(0)
                                       .gems(new HashMap<>())
                                       .bonuses(new HashMap<>())
                                       .nobles(new ArrayList<>())
                                       .powers(new ArrayList<>())
                                       .reservedCards(new ArrayList<>())
                                       .reservedNobles(new ArrayList<>())
                                       .build())
                  .collect(Collectors.toList());
  }

  String toMd5(Game game) throws JsonProcessingException {
    byte[] stringOfBytes = stringMapper.writeValueAsString(mapper.map(game))
                                       .getBytes(StandardCharsets.UTF_8);
    StringBuilder hash = new StringBuilder(
        new BigInteger(1, mdDigester.digest(stringOfBytes)).toString(16)
    );
    while (hash.length() < 32) {
      hash.insert(0, "0");
    }
    return hash.toString();
  }

  /**
   * gets the Game with the corresponding id.
   */
  public Game getGame(String id) throws IllegalAccessException {
    synchronized (managerService) {
      if (managerService.hasGame(id)) {
        return managerService.getGame(id);
      }
    }
    throw new IllegalAccessException(String.format("No game with session id of %s", id));
  }

  /**
   * Get the game board based on the current users game hash.
   *
   * @param id       The id of the game
   * @param user     The user requesting
   * @param gameHash The game hash
   * @return The game board if there was an update
   * @throws IOException            Loading the game
   * @throws IllegalAccessException Player cannot access the game
   */
  public synchronized DeferredResult<GameDto> getGame(String id, String user, String gameHash)
      throws IOException, IllegalAccessException {
    Game game = getGame(id).checkUserRights(user);
    DeferredResult<GameDto> gameResult = new DeferredResult<>(properties.getMaxTimeout()
                                                                        .longValue());
    if (gameHash.equals("")) {
      gameResult.setResult(mapper.map(game));
      return gameResult;
    }
    // Synchronize over this and start the waiter. Add a cleanup on timeout
    synchronized (this) {
      String md5 = toMd5(game);

      if (!gameHash.equals(md5)) {
        gameResult.setResult(mapper.map(game));
      }

      if (!gameResult.hasResult()) {
        Optional.ofNullable(waitersMap.putIfAbsent(id, new ArrayList<>()))
                .orElse(waitersMap.get(id))
                .add(gameResult);
        gameResult.onTimeout(() -> handleTimeoutOrComplete(id, gameResult));
      }
      return gameResult;
    }
  }


  /**
   * Saves the game.
   */
  public void saveGame(GameType type, String sessionId) throws IllegalAccessException, IOException {
    synchronized (managerService) {
      if (!managerService.hasGame(sessionId)) {
        throw new IllegalAccessException("Invalid id!");
      }
      String gameType = properties.getRegistrationInformation()
                                  .stream()
                                  .filter(x -> x.getEndpoint()
                                                .equals(type))
                                  .findFirst()
                                  .orElseThrow()
                                  .getName();
      String randomId = UUID.randomUUID()
                            .toString()
                            .substring(0, properties.getGameIdLength())
                            .replace("-", "");
      Game game = managerService.getGame(sessionId);
      game.setGameName(randomId);
      try {
        SaveGameRequest body = SaveGameRequest.builder()
                                              .gameName(gameType)
                                              .players(game
                                                         .getPlayers()
                                                         .stream()
                                                         .map(Player::getName)
                                                         .collect(Collectors.toList()))
                                              .saveGameId(randomId).build();
        lobbyServiceApiService.saveSessionService(gameType, tokenManager.token().getToken(),
                                                  randomId, body);
        loader.saveGame(managerService.getGame(sessionId), randomId);
      } catch (FeignException e) {
        log.error("", e);
        throw e;
      }
    }
  }

  /**
   * kick out player from session.
   */
  public synchronized void kickOut(String sessionId, Player player) {
    synchronized (managerService) {
      if (managerService.hasGame(sessionId)) {
        managerService.getGame(sessionId).kickOut(player);
      }
    }
  }
}