package org.mcgill.splendorapi.api.move.model;

import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * L1 orient pairing.
 */
@Getter
public class LevelOnePairingMove extends BuyCardMove {
  private final Bonus pairing;

  /**
   * simulateMove for players.

   * @param player to perform the simulation on
   * @return Map of Bonus, Integer of simulateMove
   */
  @Override
  public Map<Bonus, Integer> simulateMove(Player player) {
    Map<Bonus, Integer> tempBonuses = super.simulateMove(player);
    addBonusHelper(pairing, tempBonuses);
    return tempBonuses;
  }


  public LevelOnePairingMove(DevelopmentCard card, Bonus pairing) {
    super(card);
    this.pairing = pairing;
  }

  public LevelOnePairingMove(LevelOnePairingMove move) {
    super(move);
    this.pairing = move.pairing;
  }


  /** To apply the move.

   * @param game the curGame
   * @throws GameBoardException GameBoardException
   * @throws IllegalMoveException IllegalMoveException
   */
  @Override
  void applyMove(Game game, Player player) throws GameBoardException, IllegalMoveException {
    super.applyMove(game, player);
    game.getCurPlayer().addBonus(pairing);
  }

  @Override
  public LevelOnePairingMove copy() {
    return new LevelOnePairingMove(this);
  }
}
