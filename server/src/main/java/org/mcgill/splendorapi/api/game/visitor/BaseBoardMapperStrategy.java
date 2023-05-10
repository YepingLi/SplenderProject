package org.mcgill.splendorapi.api.game.visitor;

import java.util.List;
import org.mcgill.splendorapi.api.game.dto.BaseGameBoardDto;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
/**
 * Base board mapper.
 */

public class BaseBoardMapperStrategy implements BoardMapperStrategy<BaseGameBoardDto> {

  /**
   * Checks if the game fits the strategy of the mapper.
   *
   * @param game the game
   * @return if the mapper can handle this type of game
   */
  @Override
  public boolean isStrategy(Game game) {
    return game.getType().equals(GameType.BASE);
  }

  /**
   * Convert the board with to a dto.
   *
   * @param gameboard The board
   * @param nobleCards The noble cards on the board
   * @return The dto client safe object
   */
  @Override
  public BaseGameBoardDto map(GameBoard gameboard, List<NobleCard> nobleCards) {
    List<DevelopmentCard> listCards = MapperUtils
        .filterDevelopmentCards(((GameBoard) gameboard).getDeck());
    return new BaseGameBoardDto(MapperUtils.countLevel(1, listCards.stream()),
                                MapperUtils.countLevel(2, listCards.stream()),
                                MapperUtils.countLevel(3, listCards.stream()),
                                MapperUtils.filterTurnedCards(listCards),
                                ((GameBoard) gameboard).getGems(),
                                nobleCards);
  }

}
