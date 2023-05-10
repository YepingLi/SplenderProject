package org.mcgill.splendorapi.api.game.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.TradingPost;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;

/**
 * Represents the Dto for the game board with TradingPosts and Orient extensions.
 */
@Getter
public class TradingPostOrientBoardDto extends OrientGameBoardDto {
  private final List<TradingPost> tradingPosts;

  /**
   * Constructor.
   */
  public TradingPostOrientBoardDto(Integer remainingLevelOneCards, Integer remainingLevelTwoCards,
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
