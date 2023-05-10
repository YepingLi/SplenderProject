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
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * L3 cascade -> L2 reserve noble.
 */
@Getter
public class LevelThreePairingL2NobleMove extends LevelTwoNobleMove {
  private final DevelopmentCard levelTwoCascadeCard;

  /**
   * to simulateMove for LevelThreePairingL2NobleMove.

   * @param player to perform the simulation on
   * @return a map of Bonus, Integer simulateMove for LevelThreePairingL2NobleMove.
   */

  @Override
  public Map<Bonus, Integer> simulateMove(Player player) {
    Map<Bonus, Integer> tempBonuses = super.simulateMove(player);
    addBonusHelper(levelTwoCascadeCard.getBonus(), tempBonuses);
    return tempBonuses;
  }

  public LevelThreePairingL2NobleMove(DevelopmentCard card,
                                      NobleCard noble,
                                      DevelopmentCard levelTwoCard) {
    super(card, noble);
    levelTwoCascadeCard = levelTwoCard;
  }

  public LevelThreePairingL2NobleMove(LevelThreePairingL2NobleMove move) {
    super(move);
    levelTwoCascadeCard = move.levelTwoCascadeCard;
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
    game.getCurPlayer().addFreeCard(levelTwoCascadeCard);
    game.getBoard().dealCard(new DevelopmentCardMeta((short) 0, (short) 2, CardType.ORIENT));
  }

  @Override
  public BuyCardMove copy() {
    return new LevelThreePairingL2NobleMove(this);
  }
}
