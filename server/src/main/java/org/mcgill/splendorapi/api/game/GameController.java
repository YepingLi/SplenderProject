package org.mcgill.splendorapi.api.game;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.api.InvalidRightsHandler;
import org.mcgill.splendorapi.api.game.dto.GameDto;
import org.mcgill.splendorapi.config.LobbyServiceProperties;
import org.mcgill.splendorapi.connector.LobbyServiceAuthResource;
import org.mcgill.splendorapi.connector.model.LauncherInfo;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.exceptions.ConversionException;
import org.mcgill.splendorapi.model.exceptions.NoChangeFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * LauncherInfo controller.
 */
@Slf4j
@RestController("LauncherInfo controller")
@RequiredArgsConstructor
public class GameController extends InvalidRightsHandler {
  private final LobbyServiceProperties lsProperties;
  private final LobbyServiceAuthResource authResource;
  private final GameService gameService;

  /**
   * Check the source of the request is proper, came from the lobby service.
   *
   * @param request Request received in its raw form
   */
  private void testProperUrl(HttpServletRequest request) {
    assert request.getHeader(HttpHeaders.ORIGIN).contains(lsProperties.getApi().getUrl());
  }

  /**
   * Get mapping to retrieve a game from the server.
   *
   * @param gameId The id of the game
   * @return A game if it can find one
   * @throws InvalidPathException If it cannot read the game path
   */
  @GetMapping("/{typeId}/games/{id}")
  public Game loadGame(@PathVariable("id") String gameId,
                       @PathVariable("typeId") String typeId,
                       HttpServletRequest request)
      throws IllegalAccessException {
    testProperUrl(request);
    Game g = gameService.getGame(gameId);
    log.debug(g.getBoard().toString());
    return g;
  }

  /**
   * Get the board.
   *
   * @param id       The id of the game
   * @param gameHash The hash received from the player
   * @param token    The player token
   * @return The gameboard required
   * @throws IOException            Raised during the serialization of the game into string
   * @throws IllegalAccessException Player does not have access
   */
  @GetMapping("/{gameType}/{id}/currentGame")
  public DeferredResult<GameDto> getGame(@PathVariable("id") String id,
                         @PathVariable("gameType") String gameType,
                         @RequestParam(
                                     value = "game_hash",
                                     required = false,
                                     defaultValue = "")
                                   String gameHash,
                         @RequestParam("access_token") String token)
      throws IOException, IllegalAccessException, ConversionException, NoSuchAlgorithmException {
    GameType.fromString(gameType);
    return gameService.getGame(id, authResource.getUsername(token), gameHash);
  }

  /**
   * Start the game (request from LS).
   *
   * @param gameId The id of the game
   * @param typeId The id of the type of game
   * @param info   The game the lobby service built.
   * @throws IOException            fails to read the LauncherInfo
   * @throws IllegalAccessException Player cannot load this game
   */
  @PutMapping("/{id}/api/games/{gameId}")
  public void startGame(@PathVariable("id") String typeId,
                        @PathVariable("gameId") String gameId,
                        @RequestBody LauncherInfo info,
                        HttpServletRequest request)
      throws IOException, IllegalAccessException, ConversionException, NoSuchAlgorithmException {
    testProperUrl(request);
    GameType type = GameType.fromString(typeId);
    gameService.create(gameId, type, info);
    log.debug(info.toString());
  }


  /**
   * Save the game. Cannot take in the game, user tells us which game to save and
   * save the CURRENT game state that the server sees.
   *
   * @param gameId The id of the game
   * @param typeId The id of the type of game
   * @param token  The players token
   * @throws IOException If it cannot read the game path
   */
  @PostMapping("/{id}/api/games/{gameId}")
  public void saveGame(@PathVariable("gameId") String gameId,
                       @PathVariable("id") String typeId,
                       @RequestParam("access_token") String token)
      throws IOException, IllegalAccessException, ConversionException {
    GameType type = GameType.fromString(typeId);
    String user = authResource.getUsername(token);
    log.debug("Saving a game");
    gameService.saveGame(type, gameId);
  }

}
