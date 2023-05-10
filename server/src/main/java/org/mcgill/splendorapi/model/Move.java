package org.mcgill.splendorapi.model;

import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * Contract for a base move.
 */

public interface Move {
  Move copy();

  void applyMove(Game game) throws GameBoardException, IllegalMoveException;
}
