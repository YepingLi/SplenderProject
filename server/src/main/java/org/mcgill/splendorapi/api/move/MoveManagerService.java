package org.mcgill.splendorapi.api.move;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.api.game.GameService;
import org.mcgill.splendorapi.api.move.generators.BuyCard;
import org.mcgill.splendorapi.api.move.generators.MoveGenerator;
import org.mcgill.splendorapi.api.move.generators.ReserveCard;
import org.mcgill.splendorapi.api.move.generators.TakeToken;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.util.TimeToLiveCache;
import org.springframework.stereotype.Service;

/**
 * THe move manager service managing move generation and application.
 */
@Slf4j
@Service
@Getter
public class MoveManagerService {
  private final GameService gameService;
  private final TimeToLiveCache cache;

  /**
   * Constructor for the MoveManager service.
   */
  MoveManagerService(GameService service, TimeToLiveCache gameCache) {
    gameService = service;
    cache = gameCache;
  }

  /**
   * Get the list of types.
   *
   * @return THe list of types
   */
  private List<MoveGenerator> getMoveServices(Game game) {
    return List.of(new BuyCard(game),
            new ReserveCard(game),
            new TakeToken(game));
  }

  /**
   * Builds are move objects for a given game.
   *
   * @param game The game as input
   * @return The stream of moves
   */
  public Stream<Move> buildMoves(Game game) {
    return getMoveServices(game).stream()
                                .map(MoveGenerator::buildMoves)
                                .flatMap(List::stream);
  }

  /**
   * Get all the move services to produce their moves and then return the list of moves.
   *
   * @param gameId   The game id
   * @param username the user accessing this
   * @return The stream of moves
   * @throws IllegalAccessException If it is not the player turn
   * @throws IllegalMoveException   If the move generated is invalid
   */
  public List<Move> buildMoves(String gameId, String username)
      throws IllegalAccessException, IllegalMoveException {
    Game game = gameService.getGame(gameId).checkIsTurn(username);
    List<Move> moves = buildMoves(game).collect(Collectors.toList());
    cache.addMovesForGame(gameId, moves);
    return moves;
  }

  /**
   * Performs the move.
   *
   * @param gameId   The game id
   * @param username the user accessing this
   * @param index    The index of the move
   * @throws IllegalAccessException If it is not the player turn
   * @throws IllegalMoveException   If the move is invalid
   * @throws GameBoardException     If the gameboard is invalid (post checks)
   */
  public void performMove(String gameId, String username, Integer index)
      throws IllegalAccessException, IllegalMoveException, GameBoardException {
    Game game = gameService.getGame(gameId).checkIsTurn(username);
    cache.getGeneratedMoves(gameId)
         .get(index)
         .applyMove(game);
    if (isGameOver(game)) {
      game.setIsOver();
    } else {
      game.changePlayer();
      gameService.handleUpdate(gameId, game);
    }
  }

  public void endGame() {

  }

  /**
   * Check if the game is over.

   * @param game game
   * @return if the game is over.
   */
  public boolean isGameOver(Game game) {
    int curPlayerIndex = game.getPlayers().indexOf(game.getCurPlayer());
    boolean isLast = curPlayerIndex == (game.getPlayers().size() - 1);
    if (game.isTerminating() && isLast) {
      return true;
    }
    return false;
  }
}
