package org.mcgill.splendorapi.api.move.generators;

import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.model.Game;


/**
 * Builds the base of the Move Service.
 */
@RequiredArgsConstructor
public abstract class AbstractMoveGenerator implements MoveGenerator {
  protected final Game game;
}
