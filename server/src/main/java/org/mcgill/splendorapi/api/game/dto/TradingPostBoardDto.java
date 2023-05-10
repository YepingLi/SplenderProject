package org.mcgill.splendorapi.api.game.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.TradingPost;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;

/**
 * trading post Game board representation for the client.
 */
@Getter
public class TradingPostBoardDto extends OrientGameBoardDto {
  private final List<TradingPost> tradingPosts;
  /**
   * Converts the TradingPostBoard into a GameBoard DTO which is safe for the client to use.
   *
   * @param remainingLevelOneCards remainingLevelOneCards
   * @param remainingLevelTwoCards remainingLevelTwoCards
   * @param remainingLevelThreeCards remainingLevelThreeCards
   * @param faceUpCards faceUpCards
   * @param availableGems availableGems
   * @param nobles nobles
   * @param tradingPosts tradingPosts
   */

  public TradingPostBoardDto(Integer remainingLevelOneCards, Integer remainingLevelTwoCards,
                             Integer remainingLevelThreeCards,
                             List<DevelopmentCard> faceUpCards,
                             Integer remainingLevelOneOrientCards,
                             Integer remainingLevelTwoOrientCards,
                             Integer remaininglevelThreeOrientCards,
                             List<DevelopmentCard> faceUpOrientCards,
                             Map<GemType, Integer> availableGems,
                             List<NobleCard> nobles, List<TradingPost> tradingPosts) {
    super(remainingLevelOneCards, remainingLevelTwoCards, remainingLevelThreeCards, faceUpCards,
          remainingLevelOneOrientCards, remainingLevelTwoOrientCards,
          remaininglevelThreeOrientCards,
          faceUpOrientCards, availableGems, nobles);
    this.tradingPosts = tradingPosts;
  }
}
