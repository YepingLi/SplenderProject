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
 * Represent a token taking move.
 */
@Getter
public class TokenTakingMove implements Move {
  private List<GemType> exchangeGems;

  public TokenTakingMove(List<GemType> retrievedGems) {
    exchangeGems = retrievedGems;
  }

  public TokenTakingMove(TokenTakingMove move) {
    exchangeGems = move.exchangeGems;
  }

  @Override
  public Move copy() {
    return new TokenTakingMove(this);
  }

  @Override
  public void applyMove(Game game)
      throws GameBoardException, IllegalMoveException {
    Player player = game.getCurPlayer();
    GameBoard board = game.getBoard();
    player.addGems(exchangeGems.stream()
                               .collect(Collectors.groupingBy(Function.identity(),
                                                              Collectors.summingInt(x -> 1))));
    exchangeGems.forEach(type -> {
      board.getGems().replace(type, board.getGems().get(type) - 1);
    });

  }

  @Override
  public String toString() {

    return "TokenTakingCascadeMove{"
      +
      "aMove=" + exchangeGems
      +
      '}';
  }
}