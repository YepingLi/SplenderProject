package org.mcgill.splendorapi.api.move.model;

import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;


/**
 * L2 pairing cascade -> L1 base | orient double gold.
 */
@Getter
public class LevelTwoPairingMove extends BuyCardMove {
  private final Bonus levelTwoPairing;
  private final DevelopmentCard levelOneCard;

  /**
   * to simulateMove for LevelTwoPairingMove.

   * @param player to perform the simulation on
   * @return a map of Bonus, Integer simulateMove for LevelTwoPairingMove.
   */

  @Override
  public Map<Bonus, Integer> simulateMove(Player player) {
    Map<Bonus, Integer> tempBonuses = super.simulateMove(player);
    addBonusHelper(levelTwoPairing, tempBonuses);
    addBonusHelper(levelOneCard.getBonus(), tempBonuses);
    return tempBonuses;
  }

  /**
   * Move to pair Level two card.

   * @param card DevelopmentCard
   * @param lvlOne lvlOne DevelopmentCard
   * @param pairing Bonus of pairing
   */

  public LevelTwoPairingMove(DevelopmentCard card, DevelopmentCard lvlOne, Bonus pairing) {
    super(card);
    levelOneCard = lvlOne;
    levelTwoPairing = pairing;
  }

  /**
   * Move to pair Level two card, overload constructor.

   * @param move the LevelTwoPairingMove
   */

  public LevelTwoPairingMove(LevelTwoPairingMove move) {
    super(move);
    levelOneCard = move.levelOneCard;
    levelTwoPairing = move.levelTwoPairing;
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
    player.addBonus(levelTwoPairing);
    player.addFreeCard(levelOneCard);
    if (levelOneCard instanceof OrientDevelopmentCard) {
      //has to be a double gold card
      player.addGoldCard();
      game.getBoard().dealCard(new DevelopmentCardMeta((short) 0, (short) 1, CardType.ORIENT));
    } else {
      game.getBoard().dealCard(new DevelopmentCardMeta((short) 0, (short) 1, CardType.DEVELOPMENT));
    }
  }

  @Override
  public BuyCardMove copy() {
    return new LevelTwoPairingMove(this);
  }
}
