package org.mcgill.splendorapi.api.move.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.api.move.model.TokenTakingCascadeMove;
import org.mcgill.splendorapi.api.move.model.TokenTakingMove;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.PowerType;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.paukov.combinatorics3.Generator;

/**
 * service class for token taking.
 */

public class TakeToken extends AbstractMoveGenerator {

  /**
   * Constructor of TakeTokenService.
   *
   * @param game the game.
   */
  public TakeToken(Game game) {
    super(game);
  }

  /**
   * Generate all possible moves.
   */
  private List<Move> possibleMovesGenerator(Map<GemType, Integer> apossiblegems) {
    List<Move> possibleTokenTakingMoves = new ArrayList<>();
    //Filters out the Gold tokens.
    List<GemType> availableGemType = apossiblegems.keySet()
                                                  .stream().filter(x -> !x.equals(GemType.GOLD))
                                                  .collect(Collectors.toList());

    //PART 1: Adds the Gold taking move and the moves of taking two of the same type.
    for (GemType pgemType : apossiblegems.keySet()) {
      // whenever there is a Gold, there is a move of taking one gold associated with it
      if (pgemType == GemType.GOLD) {
        possibleTokenTakingMoves.add(new TokenTakingMove(List.of(GemType.GOLD)));
      } else if (apossiblegems.get(pgemType) >= 4) {
        possibleTokenTakingMoves.add(new TokenTakingMove(List.of(pgemType, pgemType)));
        if (game.getCurPlayer().getPowers().contains(PowerType.TWO_AND_ONE)
            &&
            (game.getCurPlayer().getBonuses().get(Bonus.DIAMOND) >= 2)) {
          for (GemType gemType : availableGemType) {
            if (pgemType != gemType) {
              possibleTokenTakingMoves
                .add(new TokenTakingMove(List.of(pgemType, pgemType, gemType)));
            }
          }
        }
      }
    }
    if (availableGemType.size() < 3) {
      return possibleTokenTakingMoves;
    }

    //PART 2: Adds all the moves which take one gem of 3 different types.
    List<List<GemType>> permutations = new ArrayList<>();
    for (GemType firstSelection : availableGemType) {
      //The Gems not of the current type
      for (GemType secondSelection : availableGemType) {
        if (secondSelection.equals(firstSelection)) {
          continue; // Skip the current gem type
        }
        for (GemType thirdSelection : availableGemType) {
          if (thirdSelection.equals(firstSelection) || thirdSelection.equals(secondSelection)) {
            continue; // Skip the current and previous gem types
          }
          permutations.add(List.of(firstSelection, secondSelection, thirdSelection));
        }
      }

    }


    possibleTokenTakingMoves.addAll(permutations.stream()
                                                .map(TokenTakingMove::new)
                                                .collect(Collectors.toList()));
    return possibleTokenTakingMoves;
  }

  private List<List<GemType>> reduceList(List<List<GemType>> lists) {
    List<List<GemType>> withoutDuplicate = new ArrayList<>(new HashSet<>(lists));
    return withoutDuplicate;
  }

  private List<Move> cascadeMovesGenerator(TokenTakingMove move, int numToRemove) {
    List<GemType> listPosGemsToRemove = new ArrayList<>();

    listPosGemsToRemove.addAll(move.getExchangeGems());
    for (GemType key : game.getCurPlayer().getGems().keySet()) {
      int tempC = game.getCurPlayer().getGems().get(key);
      int tempD = 0;
      while (tempC > 0 && tempD < 3) { //list of gems possible to remove
        listPosGemsToRemove.add(key);
        tempC -= 1;
        tempD += 1;
      }
    }
    List<Move> listPosCascadeMoves = new ArrayList<>();
    //create a list of lists of gem types to be removed.
    List<List<GemType>> differentCombinationsList = Generator.combination(listPosGemsToRemove)
                                                             .simple(numToRemove)
                                                             .stream()
                                                             .collect(Collectors.toList());
    differentCombinationsList = reduceList(differentCombinationsList);

    differentCombinationsList
        .stream()
        .forEach(x -> listPosCascadeMoves
          .add(new TokenTakingCascadeMove(move.getExchangeGems(), x)));
    return listPosCascadeMoves;
  }


  @Override
  public List<Move> buildMoves() {
    List<Move> finalPossibleTokenTakingMoves = new ArrayList<>();
    GameBoard agameboard = game.getBoard();
    Map<GemType, Integer> apossiblegems = Arrays.stream(GemType.values())
                                                .filter(x -> agameboard.getAvailableGemType(x) != 0)
                                                .collect(Collectors.toMap(Function.identity(),
        agameboard::getAvailableGemType));
    //num of gems the current player has rn
    int totalGemsNum = 0;
    for (int i : game.getCurPlayer().getGems().values()) {
      totalGemsNum += i;
    }
    List<Move> tempList = possibleMovesGenerator(apossiblegems);

    for (Move move : tempList) {
      TokenTakingMove theMove = (TokenTakingMove) move;
      //if the move doesn't result more than 10 tokens, simply add move to the list.
      if ((totalGemsNum + theMove.getExchangeGems().size()) < 11) {
        finalPossibleTokenTakingMoves.add(move);
      } else { //if the move results more than 10 tokens, trigger a list of cascade moves,
        finalPossibleTokenTakingMoves.addAll(cascadeMovesGenerator(theMove,
                                                                   ((totalGemsNum
                                                                     +
                                                                     theMove.getExchangeGems()
                                                                            .size()) - 10)));
      }
    }
    return finalPossibleTokenTakingMoves;
  }
}
