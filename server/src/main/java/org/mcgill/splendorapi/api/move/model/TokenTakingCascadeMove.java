package org.mcgill.splendorapi.api.move.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * Taking care pg the burn tokens after taking tokens.
 */
@Getter
public class TokenTakingCascadeMove extends TokenTakingMove {
  List<GemType> gemTypeListToBurn;


  public TokenTakingCascadeMove(List<GemType> retrievedGems, List<GemType> burnList) {
    super(retrievedGems);
    gemTypeListToBurn = burnList;
  }

  public TokenTakingCascadeMove(TokenTakingCascadeMove move) {
    super(move);
    gemTypeListToBurn = move.gemTypeListToBurn;
  }

  @Override
  public Move copy() {
    return new TokenTakingCascadeMove(this);
  }

  @Override
  public void applyMove(Game game)
      throws GameBoardException, IllegalMoveException {
    GameBoard board = game.getBoard();
    Player player = game.getCurPlayer();

    super.applyMove(game);

    // remove gem from player
    for (GemType theGemType :  gemTypeListToBurn) {
      player.removeGem(theGemType);
    }
    //Add gems to the board.
    gemTypeListToBurn.forEach(type -> {
      board.getGems().replace(type, board.getGems().get(type) + 1);
    });
  }

  @Override
  public String toString() {

    return "TokenTakingCascadeMove{"
      +
      "aMove=" + super.getExchangeGems() + gemTypeListToBurn
      +
      '}';
  }
}
