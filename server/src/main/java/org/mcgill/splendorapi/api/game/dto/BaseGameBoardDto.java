package org.mcgill.splendorapi.api.game.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;

/**
 * Game which can be sent to the client.
 */
@Getter
public class BaseGameBoardDto {
  // Discriminator
  private static final GameType type = GameType.BASE;
  private final Integer remainingLevelOneCards;
  private final Integer remainingLevelTwoCards;
  private final Integer remainingLevelThreeCards;
  private final List<DevelopmentCard> faceUpCards;
  private final Map<GemType, Integer> availableGems;
  private final List<NobleCard> nobles;

  /**
   * Converts the GameBoard into a GameBoard DTO which is safe for the client to use.
   */
  public BaseGameBoardDto(Integer remainingLevelOneCards, Integer remainingLevelTwoCards,
                          Integer remainingLevelThreeCards, List<DevelopmentCard> faceUpCards,
                          Map<GemType, Integer> availableGems, List<NobleCard> nobles) {
    this.remainingLevelOneCards = remainingLevelOneCards;
    this.remainingLevelTwoCards = remainingLevelTwoCards;
    this.remainingLevelThreeCards = remainingLevelThreeCards;
    this.faceUpCards = faceUpCards;
    this.availableGems = availableGems;
    this.nobles = nobles;
  }

}
