package org.mcgill.splendorapi.api.move.generators;

import java.util.List;
import org.mcgill.splendorapi.model.Move;


/**
 * Base move service.
 */
public interface MoveGenerator {
  List<Move> buildMoves();
}
