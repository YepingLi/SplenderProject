package org.mcgill.splendorapi.api.game.visitor;

import java.util.List;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.card.NobleCard;

/**
 * Strategy for Boarder.
 *
 * @param <T> Generic.
 *
 */

public interface BoardMapperStrategy<T> {
  boolean isStrategy(Game g);

  T map(GameBoard b, List<NobleCard> nobleCards);
}
