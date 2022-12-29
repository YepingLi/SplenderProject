package org.mcgill.splendorapi.controller;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.config.LobbyServiceProperties;
import org.mcgill.splendorapi.connector.LobbyServiceAuthResource;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameBoard;
import org.mcgill.splendorapi.model.exceptions.NoChangeFoundException;
import org.mcgill.splendorapi.model.exceptions.NoGameFoundException;
import org.mcgill.splendorapi.service.GameLoaderService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Game controller.
 */
@RestController("Game controller")
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController extends InvalidRightsHandler {
  private final LobbyServiceProperties lsProperties;
  private final LobbyServiceAuthResource authResource;
  private final GameLoaderService loader;

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
   * @throws IOException If it cannot read the game path
   */
  @GetMapping("/{id}")
  public Game loadGame(@PathVariable("id") String gameId, HttpServletRequest request)
      throws InvalidPathException {
    testProperUrl(request);
    return loader.getGame(gameId);
  }

  /**
   * Save the game. Cannot take in the game, user tells us which game to save and
   * save the CURRENT game state that the server sees.
   *
   * @param gameId The id of the game
   * @param token The players token
   * @throws IOException If it cannot read the game path
   */
  @PostMapping("/{id}")
  public void saveGame(@PathVariable("id") String gameId,
                       @RequestParam("access_token") String token)
      throws IOException, NoGameFoundException, IllegalAccessException {
    String user = authResource.getUsername(token);
    loader.saveGame(gameId, user);
  }

  /**
   * Start the game (request from LS).
   *
   * @param gameId The id of the game
   * @param game The game the lobby service built.
   * @throws IOException fails to read the Game
   * @throws IllegalAccessException Player cannot load this game
   */
  @PutMapping("/{id}")
  public void startGame(@PathVariable("id") String gameId,
                        @RequestBody Game game,
                        HttpServletRequest request)
      throws IOException, IllegalAccessException {
    testProperUrl(request);
    loader.startOrLoadGame(gameId, game);
  }

  /**
   * Get the board.
   *
   * @param id The id of the game
   * @param gameHash The hash received from the player
   * @param token The player token
   * @return The gameboard required
   * @throws IOException Raised during the serialization of the game into string
   * @throws InterruptedException While waiting for an update
   * @throws IllegalAccessException Player does not have access
   */
  @GetMapping("/{id}/currentBoard")
  public GameBoard getBoard(@PathVariable("id") String id,
                                   @RequestParam("game_hash") String gameHash,
                                   @RequestParam("access_token") String token)
      throws IOException, InterruptedException, IllegalAccessException, NoChangeFoundException {
    return loader.getBoard(id, gameHash, authResource.getUsername(token));
  }

}
