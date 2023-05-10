package org.mcgill.splendorapi.api.move.model;

import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * L3 cascade -> L2 base | orient double bonus.
 */
@Getter
public class LevelThreePairingL2BaseMove extends BuyCardMove {
  private final DevelopmentCard levelTwoCascade;

  /**
   * To simulateMove.

   * @param player to perform the simulation on
   * @return map of Bonus, Integer of simulateMove
   */
  @Override
  public Map<Bonus, Integer> simulateMove(Player player) {
    Map<Bonus, Integer> tempBonuses = super.simulateMove(player);
    addBonusHelper(levelTwoCascade.getBonus(), tempBonuses);
    if (levelTwoCascade instanceof OrientDevelopmentCard) {
      addBonusHelper(levelTwoCascade.getBonus(), tempBonuses);
    }
    return tempBonuses;
  }

  public LevelThreePairingL2BaseMove(DevelopmentCard card, DevelopmentCard cascade) {
    super(card);
    levelTwoCascade = cascade;
  }

  public LevelThreePairingL2BaseMove(LevelThreePairingL2BaseMove move) {
    super(move);
    levelTwoCascade = move.levelTwoCascade;
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
    player.addFreeCard(levelTwoCascade);
    game.getBoard().dealCard(levelTwoCascade.getMeta());
    if (levelTwoCascade instanceof OrientDevelopmentCard) {
      //has to be a double bonus orient card
      player.addBonus(levelTwoCascade.getBonus());
    }
  }

  @Override
  public LevelThreePairingL2BaseMove copy() {
    return new LevelThreePairingL2BaseMove(this);
  }
}
