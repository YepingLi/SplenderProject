package org.mcgill.splendorapi.api.move.model;

import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * L2 reserve noble.
 */
@Getter
public class LevelTwoNobleMove extends BuyCardMove {
  private final NobleCard reservedNoble;

  /**
   * to simulateMove for LevelTwoNobleMove.

   * @param player to perform the simulation on
   * @return a map of Bonus, Integer simulateMove for LevelTwoNobleMove.
   */

  @Override
  public Map<Bonus, Integer> simulateMove(Player player) {
    return super.simulateMove(player);
  }

  public LevelTwoNobleMove(DevelopmentCard card, NobleCard noble) {
    super(card);
    reservedNoble = noble;
  }

  public LevelTwoNobleMove(LevelTwoNobleMove move) {
    super(move);
    reservedNoble = move.reservedNoble;
  }

  /**
   * To apply the move.

   * @param game the curGame
   * @throws GameBoardException GameBoardException
   * @throws IllegalMoveException IllegalMoveException
   */
  @Override
  void applyMove(Game game, Player player) throws GameBoardException, IllegalMoveException {
    super.applyMove(game, player);
    game.getCurPlayer().addReservedNoble(reservedNoble);
  }

  @Override
  public BuyCardMove copy() {
    return new LevelTwoNobleMove(this);
  }
}
