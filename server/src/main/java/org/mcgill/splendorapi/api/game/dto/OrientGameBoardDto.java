package org.mcgill.splendorapi.api.game.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;


/**
 * Converts the Orient Board into a safe DTO.
 */
@Getter
public class OrientGameBoardDto extends BaseGameBoardDto {
  // Discriminator
  private static final GameType type = GameType.ORIENT;

  private final Integer remainingLevelOneOrientCards;
  private final Integer remainingLevelTwoOrientCards;
  private final Integer remainingLevelThreeOrientCards;
  private final List<DevelopmentCard> faceUpOrientCards;


  /**
   * Constructor.
   */
  public OrientGameBoardDto(Integer remainingLevelOneCards,
                            Integer remainingLevelTwoCards,
                            Integer remainingLevelThreeCards,
                            List<DevelopmentCard> faceUpCards,
                            Integer remainingLevelOneOrientCards,
                            Integer remainingLevelTwoOrientCards,
                            Integer remaininglevelThreeOrientCards,
                            List<DevelopmentCard> faceUpOrientCards,
                            Map<GemType, Integer> availableGems,
                            List<NobleCard> nobles) {
    super(remainingLevelOneCards, remainingLevelTwoCards, remainingLevelThreeCards, faceUpCards,
          availableGems, nobles);
    this.remainingLevelOneOrientCards = remainingLevelOneOrientCards;
    this.remainingLevelTwoOrientCards = remainingLevelTwoOrientCards;
    this.remainingLevelThreeOrientCards = remaininglevelThreeOrientCards;
    this.faceUpOrientCards = faceUpOrientCards;
  }
}
