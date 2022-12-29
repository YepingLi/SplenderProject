package org.mcgill.splendorapi.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameBoard;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.springframework.stereotype.Service;

/**
 * GameService.
 */
@RequiredArgsConstructor
@Service
public class GameService {
  private final GameLoaderService loader;

  /**
   * Apply a move to applied to the current game state.
   *
   * @param gameId the id of the game
   * @param user the user making the move
   * @param move The move
   * @return The game board
   */
  public GameBoard makeMove(String gameId, String user, Move move)
      throws IllegalMoveException, GameBoardException, IllegalAccessException {
    Game game = loader.getGame(gameId).checkUserRights(user);
    GameBoard board = game.getBoard().checkIsTurn(user);
    if (move.canApply(board)) {
      throw new IllegalMoveException();
    }

    move.applyMove(board);
    // notify any thread waiting on a change
    board.notifyAll();
    return board;
  }


}
