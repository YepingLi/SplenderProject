package org.mcgill.splendorapi.api.game.visitor;

import java.util.List;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.api.game.dto.CityBoardDto; // <--- This is the class we want to import
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.board.CityGameBoard;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.card.Card;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;

/**
 * Map city board to frontend.
 */

public class CityMapperStrategy implements BoardMapperStrategy<CityBoardDto> {
  @Override
  public CityBoardDto map(GameBoard b, List<NobleCard> nobleCards) {
    CityGameBoard board = (CityGameBoard) b;
    List<DevelopmentCard> listCards = MapperUtils.filterDevelopmentCards(board.getDeck());
    List<DevelopmentCard> listOrientCards = board.getDeck()
                                                 .stream()
                                                 .filter(Card::isOrientCard)
                                                 .map(card -> (OrientDevelopmentCard) card)
                                                 .collect(Collectors.toList());
    return new CityBoardDto(MapperUtils.countLevel(1, listCards.stream()),
                            MapperUtils.countLevel(2, listCards.stream()),
                            MapperUtils.countLevel(3, listCards.stream()),
                            MapperUtils.filterTurnedCards(listCards),
                            MapperUtils.countLevel(1, listOrientCards.stream()),
                            MapperUtils.countLevel(2, listOrientCards.stream()),
                            MapperUtils.countLevel(3, listOrientCards.stream()),
                            MapperUtils.filterTurnedCards(listOrientCards),
                            board.getGems(),
                            nobleCards,
                            board.getCities());
  }

  @Override
  public boolean isStrategy(Game g) {
    return g.getType().equals(GameType.CITIES_ORIENT);
  }
}
