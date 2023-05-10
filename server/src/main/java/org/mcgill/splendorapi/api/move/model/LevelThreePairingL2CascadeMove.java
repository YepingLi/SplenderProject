package org.mcgill.splendorapi.api.move.model;

import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * L3 cascade -> L2 pairing cascade -> L1 orient pairing.
 */
@Getter
public class LevelThreePairingL2CascadeMove extends LevelTwoPairingL1Move {
  private final DevelopmentCard levelTwoCascade;

  /**
   * to simulateMove for level three parsing.

   * @param player to perform the simulation on
   * @return a map of Bonus, Integer simulateMove for level three parsing.
   */

  @Override
  public Map<Bonus, Integer> simulateMove(Player player) {
    Map<Bonus, Integer> tempBonuses = super.simulateMove(player);
    addBonusHelper(levelTwoCascade.getBonus(), tempBonuses);
    return tempBonuses;
  }

  public LevelThreePairingL2CascadeMove(DevelopmentCard card,
                                        DevelopmentCard lvlOne,
                                        Bonus pairing,
                                        Bonus lvlOnePairing,
                                        DevelopmentCard lvlTwoCascade) {
    super(card, lvlOne, pairing, lvlOnePairing);
    levelTwoCascade = lvlTwoCascade;
  }

  public LevelThreePairingL2CascadeMove(LevelThreePairingL2CascadeMove move) {
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
    if (player.getBonuses().get(null) != null) {
      player.getBonuses().remove(null);
    }
    game.getCurPlayer().addFreeCard(levelTwoCascade);
    game.getBoard().dealCard(new DevelopmentCardMeta((short) 0, (short) 2, CardType.ORIENT));
    if (player.getBonuses().get(null) != null) {
      player.getBonuses().remove(null);
    }
  }

  @Override
  public BuyCardMove copy() {
    return new LevelThreePairingL2CascadeMove(this);
  }
}
