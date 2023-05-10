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
 * L2 pairing cascade -> L1 pairing.
 */
@Getter
public class LevelTwoPairingL1Move extends LevelTwoPairingMove {
  private final Bonus levelOnePairing;

  /**
   * to simulateMove for LevelTwoPairingL1Move.

   * @param player to perform the simulation on
   * @return a map of Bonus, Integer simulateMove for LevelTwoPairingL1Move.
   */

  @Override
  public Map<Bonus, Integer> simulateMove(Player player) {
    Map<Bonus, Integer> tempBonuses = super.simulateMove(player);
    addBonusHelper(levelOnePairing, tempBonuses);
    return tempBonuses;
  }

  public LevelTwoPairingL1Move(DevelopmentCard card,
                               DevelopmentCard lvlOne,
                               Bonus pairing, Bonus lvlOnePairing) {
    super(card, lvlOne, pairing);
    levelOnePairing = lvlOnePairing;
  }

  public LevelTwoPairingL1Move(LevelTwoPairingL1Move move) {
    super(move);
    levelOnePairing = move.levelOnePairing;
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
    game.getCurPlayer().addBonus(levelOnePairing);
  }

  @Override
  public BuyCardMove copy() {
    return new LevelTwoPairingL1Move(this);
  }
}
