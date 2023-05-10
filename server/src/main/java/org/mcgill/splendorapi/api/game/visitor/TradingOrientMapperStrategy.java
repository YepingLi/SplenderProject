package org.mcgill.splendorapi.api.game.visitor;

import java.util.List;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.api.game.dto.TradingPostOrientBoardDto;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.TradingPostsOrientBoard;
import org.mcgill.splendorapi.model.card.Card;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
/**
 * Trading Post with Orient board mapper.
 */

public class TradingOrientMapperStrategy implements BoardMapperStrategy<TradingPostOrientBoardDto> {
  /**
   * Checks if the game fits the strategy of the mapper.
   *
   * @param game the game
   * @return if the mapper can handle this type of game
   */
  @Override
  public boolean isStrategy(Game game) {
    return game.getType().equals(GameType.ORIENT_TRADING_POST);
  }

  /**
   * Convert the board with to a dto.

   * @param b The board,
   * @param nobleCards The noble cards on the board
   * @return The dto client safe object
   */
  @Override
  public TradingPostOrientBoardDto map(GameBoard b, List<NobleCard> nobleCards) {
    TradingPostsOrientBoard board = (TradingPostsOrientBoard) b;
    List<DevelopmentCard> listCards = MapperUtils.filterDevelopmentCards(board.getDeck());
    List<DevelopmentCard> listOrientCards = board.getDeck()
                                                 .stream()
                                                 .filter(Card::isOrientCard)
                                                 .map(card -> (OrientDevelopmentCard) card)
                                                 .collect(Collectors.toList());
    return new TradingPostOrientBoardDto(MapperUtils.countLevel(1, listCards.stream()),
                                         MapperUtils.countLevel(2, listCards.stream()),
                                         MapperUtils.countLevel(3, listCards.stream()),
                                         MapperUtils.filterTurnedCards(listCards),
                                         MapperUtils.countLevel(1, listOrientCards.stream()),
                                         MapperUtils.countLevel(2, listOrientCards.stream()),
                                         MapperUtils.countLevel(3, listOrientCards.stream()),
                                         MapperUtils.filterTurnedCards(listOrientCards),
                                         board.getGems(),
                                         nobleCards,
                                         board.getTradingPosts());
  }
}
