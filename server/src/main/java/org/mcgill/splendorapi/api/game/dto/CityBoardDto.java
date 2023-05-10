package org.mcgill.splendorapi.api.game.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;


/**
 * Dto for city.
 */
@Getter
public class CityBoardDto extends OrientGameBoardDto {
  private final List<City> cities;

  /**
   * Constructor.
   */

  public CityBoardDto(Integer remainingLevelOneCards, Integer remainingLevelTwoCards,
                      Integer remainingLevelThreeCards,
                      List<DevelopmentCard> faceUpCards,
                      Integer remainingLevelOneOrientCards,
                      Integer remainingLevelTwoOrientCards,
                      Integer remaininglevelThreeOrientCards,
                      List<DevelopmentCard> faceUpOrientCards,
                      Map<GemType, Integer> availableGems,
                      List<NobleCard> nobles, List<City> cities) {
    super(remainingLevelOneCards, remainingLevelTwoCards, remainingLevelThreeCards, faceUpCards,
          remainingLevelOneOrientCards, remainingLevelTwoOrientCards,
          remaininglevelThreeOrientCards,
          faceUpOrientCards, availableGems, nobles);
    this.cities = cities;
  }


}
