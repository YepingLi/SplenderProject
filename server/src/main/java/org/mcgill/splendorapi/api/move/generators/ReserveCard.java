package org.mcgill.splendorapi.api.move.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.api.move.model.ReserveDevCardMove;
import org.mcgill.splendorapi.api.move.model.TokenTakingMove;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.DevelopmentCard;

/**
 * Class for reserve development card service.
 */
public class ReserveCard extends AbstractMoveGenerator {

  /**
   * Constructor.
   *
   * @param game the game
   */
  public ReserveCard(Game game) {
    super(game);
  }

  /**
   * Builds a new move object.
   *
   * @return List of all possible ReserveDevCardMoves.
   */
  private static Move handleReserveMove(DevelopmentCard card) {
    if (card.getState().equals(CardState.FREE) && card.isTurned()) {
      return new ReserveDevCardMove(card);
    }
    return null;
  }

  /**
   * Builds the moves for the player.
   *
   * @return The list of the possible moves.
   */
  @Override
  public List<Move> buildMoves() {
    if (game.getCurPlayer().getReservedCards().size() == 3) {
      return List.of();
    }

    GameBoard gameBoard = game.getBoard();
    //PART 1: ADDING THE BASE CARD MOVES.
    List<Move> moves = gameBoard.getDeck()
                    .stream()
                    .map(ReserveCard::handleReserveMove)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    if (game.getCurPlayer().countGems() == 10) {
      moves = addTokenBurningMoves(moves);
    }
    return moves;
  }

  /**
   * If the player has 10 gems, then add a move for each possible gem they could burn.
   *
   * @param  moves is a list of move to add tokems to burn.
   * @returna list of move added burning gems selectiomn.
   */
  private List<Move> addTokenBurningMoves(List<Move> moves) {
    List<Move> tokenBurningMoves = new ArrayList<>();

    //Loops across each of the moves
    for (Move move : moves) {
      ReserveDevCardMove curMove = (ReserveDevCardMove) move;
      //Loops across all the players gems of which they have at least 1
      for (Map.Entry<GemType, Integer> curGem : game.getCurPlayer().getGems().entrySet()) {
        if (curGem.getValue() > 0) {
          //Adds a new ReserveDevCard move with the Gem Burning selection.
          ReserveDevCardMove burningMove =
              new ReserveDevCardMove(curMove.getCard(), curGem.getKey());
          tokenBurningMoves.add(burningMove);
        }
      }
    }
    return tokenBurningMoves;
  }
}

