package org.mcgill.splendorapi.api.game.visitor;

import java.util.List;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.api.game.dto.OrientGameBoardDto;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.card.Card;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;

/**
 * Orient board mapper.
 */
public class OrientMapperStrategy implements BoardMapperStrategy<OrientGameBoardDto> {
  /**
   * Map the board to the OrientGameBoardDto object.
   *
   * @param gameboard The board object
   *
   */
  public OrientGameBoardDto map(GameBoard gameboard, List<NobleCard> nobleCards) {
    OrientGameBoard board = (OrientGameBoard) gameboard;

    List<DevelopmentCard> listCards = MapperUtils.filterDevelopmentCards(board.getDeck());
    List<DevelopmentCard> listOrientCards = board.getDeck()
                                                 .stream()
                                                 .filter(Card::isOrientCard)
                                                 .map(card -> (OrientDevelopmentCard) card)
                                                 .collect(Collectors.toList());
    return new OrientGameBoardDto(MapperUtils.countLevel(1, listCards.stream()),
                                  MapperUtils.countLevel(2, listCards.stream()),
                                  MapperUtils.countLevel(3, listCards.stream()),
                                  MapperUtils.filterTurnedCards(listCards),
                                  MapperUtils.countLevel(1, listOrientCards.stream()),
                                  MapperUtils.countLevel(2, listOrientCards.stream()),
                                  MapperUtils.countLevel(3, listOrientCards.stream()),
                                  MapperUtils.filterTurnedCards(listOrientCards),
                                  board.getGems(),
                                  nobleCards);
  }

  /**
   * Checks if the game fits the strategy of the mapper.
   *
   * @param game the game
   * @return if the mapper can handle this type of game
   */
  @Override
  public boolean isStrategy(Game game) {
    return game.getType().equals(GameType.ORIENT);
  }
}
