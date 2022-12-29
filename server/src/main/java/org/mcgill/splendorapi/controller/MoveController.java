package org.mcgill.splendorapi.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.connector.LobbyServiceAuthResource;
import org.mcgill.splendorapi.model.GameBoard;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.PurchaseMove;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.service.GameService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Moves.
 */
@Slf4j
@RequiredArgsConstructor
@RestController("Move controller")
@RequestMapping("/games/{id}/move")
public class MoveController extends InvalidRightsHandler {

  private final LobbyServiceAuthResource authResource;
  private final GameService service;

  /**
   * Perform moves using this function.
   *
   * @param gameId The id of the game
   * @param move the move
   * @param token The token to be checked
   * @return The gameboard
   * @throws IllegalMoveException Cannot make this move
   * @throws IOException Cannot find game
   * @throws GameBoardException Unknown move
   * @throws IllegalAccessException Cannot do this to this board
   */
  private GameBoard checkAndPerformMove(String gameId, Move move, String token)
      throws IllegalMoveException, IOException, GameBoardException, IllegalAccessException {
    String user = authResource.getUsername(token);
    return service.makeMove(gameId, user, move);
  }

  /**
   * Perform a purchase move.
   *
   * @param gameId The id of the game
   * @param move the move
   * @param token The token to be checked
   * @return The gameboard
   * @throws IllegalMoveException Cannot make this move
   * @throws IOException Cannot find game
   * @throws GameBoardException Unknown move
   * @throws IllegalAccessException Cannot do this to this board
   */
  @PostMapping("/purchaseCard")
  public GameBoard makeMove(@PathVariable("id") String gameId,
                            @RequestParam("access_token") String token,
                            @RequestBody PurchaseMove move)
      throws IllegalMoveException, IOException, GameBoardException, IllegalAccessException {
    return checkAndPerformMove(gameId, move, token);
  }
}
