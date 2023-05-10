package org.mcgill.splendorapi.api.game;


import java.util.List;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.api.game.dto.BaseGameBoardDto;
import org.mcgill.splendorapi.api.game.dto.GameDto;
import org.mcgill.splendorapi.api.game.visitor.BaseBoardMapperStrategy;
import org.mcgill.splendorapi.api.game.visitor.BoardMapperStrategy;
import org.mcgill.splendorapi.api.game.visitor.CityMapperStrategy;
import org.mcgill.splendorapi.api.game.visitor.OrientMapperStrategy;
import org.mcgill.splendorapi.api.game.visitor.TradingOrientMapperStrategy;
import org.mcgill.splendorapi.api.game.visitor.TradingPostMapperStrategy;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.TradingPostsGameBoard;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.springframework.stereotype.Component;

/**
 * Map the game objects to their client facing objects.
 */
@Component
public class GameMapper {
  private final List<BoardMapperStrategy<? extends BaseGameBoardDto>> mappers;

  /**
   * Mapper of game.
   */
  GameMapper() {
    mappers = List.of(new BaseBoardMapperStrategy(), new OrientMapperStrategy(),
                      new TradingOrientMapperStrategy(),
                      new TradingPostMapperStrategy(), new CityMapperStrategy());
  }

  /**
   * Maps the list of nobles to their free objects.
   *
   * @param board The board given for this DTO
   * @return The List of free nobles (to be rendered)
   */
  private List<NobleCard> getFreeNobles(GameBoard board) {
    return board.getNobles()
                .stream()
                .filter(nobleCard -> nobleCard.getState().equals(CardState.FREE))
                .collect(Collectors.toList());
  }

  /**
   * Dispatch based on the game type.
   *
   * @param game The game in question
   * @return The mapped game board dto
   */
  private BaseGameBoardDto dispatch(Game game) {
    return mappers.stream()
                  .filter(mapper -> mapper.isStrategy(game))
                  .findFirst()
                  .orElseThrow(() ->
                                 new IllegalStateException("No strategy matching your game type!"))
                  .map(game.getBoard(), getFreeNobles(game.getBoard()));
  }

  /**
   * Map the Game to the presentation version.
   */
  public GameDto map(Game game) {
    return GameDto.builder()
                  .players(game.getPlayers())
                  .board(dispatch(game))
                  .gameName(game.getGameName())
                  .gameServer(game.getGameServer())
                  .launched(game.isLaunched())
                  .creator(game.getCreator())
                  .type(game.getType())
                  .curPlayer(game.getCurPlayer())
                  .isOver(game.isOver())
                  .build();
  }

  //TODO:
  BaseGameBoardDto map(TradingPostsGameBoard board) {
    return null;
  }
}
