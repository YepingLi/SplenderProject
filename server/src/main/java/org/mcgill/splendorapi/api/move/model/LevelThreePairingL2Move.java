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
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * L3 cascade -> L2 pairing cascade -> L1 base | orient double gold.
 */

@Getter
public class LevelThreePairingL2Move extends LevelTwoPairingMove {
  private final DevelopmentCard levelTwoCascade;

  /**
   * to simulateMove for LevelThreePairingL2Move.

   * @param player to perform the simulation on
   * @return a map of Bonus, Integer simulateMove for LevelThreePairingL2Move.
   */
  @Override
  public Map<Bonus, Integer> simulateMove(Player player) {
    return super.simulateMove(player);
  }

  public LevelThreePairingL2Move(DevelopmentCard card,
                                 DevelopmentCard lvlOne,
                                 Bonus pairing,
                                 DevelopmentCard cascade) {
    super(card, lvlOne, pairing);
    levelTwoCascade = cascade;
  }

  public LevelThreePairingL2Move(LevelThreePairingL2Move move) {
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
    game.getCurPlayer().addFreeCard(levelTwoCascade);
    game.getBoard().dealCard(new DevelopmentCardMeta((short) 0, (short) 2, CardType.ORIENT));
  }

  @Override
  public BuyCardMove copy() {
    return new LevelThreePairingL2Move(this);
  }
}
