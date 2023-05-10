package org.mcgill.splendorapi.api.move.model;


import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * ReserveDevCardMove.
 */
@RequiredArgsConstructor
@Getter
public class ReserveDevCardMove implements Move {
  private final DevelopmentCard card;

  private GemType gem;

  public ReserveDevCardMove(DevelopmentCard card, GemType gem) {
    this.card = card;
    this.gem = gem;
  }

  ReserveDevCardMove(ReserveDevCardMove move) {
    card = move.card;
    gem = move.gem;
  }

  @Override
  public ReserveDevCardMove copy() {
    return new ReserveDevCardMove(this);
  }

  /**
   * Performs the ReserveDevCardMove.
   *
   * @param game The current game.
   * @throws GameBoardException ex.
   * @throws IllegalMoveException ill.
   */
  @Override
  public void applyMove(Game game)
      throws GameBoardException, IllegalMoveException {
    GameBoard board = game.getBoard();
    Player player = game.getCurPlayer();

    //1. Reserved the card on the board.
    DevelopmentCard devCard = (DevelopmentCard) board.getCard(card.getMeta());
    if (devCard.isTurned()) {
      devCard.changeState(CardState.RESERVED);
    }

    //2. Deals a new Card
    board.dealCard(card.getMeta());

    //3. Adds the Card to the players reserved cards and adds a gold token.
    player.reserveCard(card);

    //4. Removes the Gold Token from the board.
    board.getGems()
         .compute(GemType.GOLD,
                  (key, curValue) -> curValue == null || curValue == 0 ? 0 : curValue - 1);

    // If the player had to burn a gem, remove it from their list and add it to the board.
    if (this.gem != null) {
      Map<GemType, Integer> payment = new HashMap<>();
      payment.put(gem, 1);
      game.getCurPlayer().removeGem(gem);
      game.getBoard().addGems(Payment.builder().gems(payment).build());
    }
  }

  /**
   * The value of the card.
   *
   * @return the value
   */
  public int getValue() {
    return card.getMeta().getId();
  }
}