package org.mcgill.splendorapi.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * Player object to maintain the player state.
 */
@Slf4j
@Builder
@Value
public class PurchaseMove implements Move {
  GamePlayer player;
  int purchasing;

  /**
   * Perform checks then perform the actual move.
   *
   * @param board The current game board
   * @throws GameBoardException More board exception
   */
  @Override
  public void applyMove(GameBoard board) throws GameBoardException, IllegalMoveException {
    Card card = board.getCard(purchasing);
    List<Gem> removed = player.buyCard(card);
    // apply this to the board
    removed.forEach(Gem::free);
  }

  /**
   * Perform checks if the actual move can be applied.
   *
   * @param board The current game board
   */
  @Override
  public boolean canApply(GameBoard board) {
    try {
      Card card = board.getCard(purchasing);
      return player.canBuyCard(card);
    } catch (GameBoardException e) {
      log.error("Caught exception while trying to check if move was applicable", e);
      return false;
    }
  }
}
