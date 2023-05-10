package org.mcgill.splendorapi.api.game.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CityBoardDtoTest {

  private List<City> cities;
  private Integer remainingLevelOneCards;
  private Integer remainingLevelTwoCards;
  private Integer remainingLevelThreeCards;
  private List<DevelopmentCard> faceUpCards;
  private Integer remainingLevelOneOrientCards;
  private Integer remainingLevelTwoOrientCards;
  private Integer remainingLevelThreeOrientCards;
  private List<DevelopmentCard> faceUpOrientCards;
  private Map<GemType, Integer> availableGems;
  private List<NobleCard> nobles;

  @BeforeEach
  void setUp() {
    cities = new ArrayList<>();
    remainingLevelOneCards = 5;
    remainingLevelTwoCards = 3;
    remainingLevelThreeCards = 2;
    faceUpCards = new ArrayList<>();
    remainingLevelOneOrientCards = 3;
    remainingLevelTwoOrientCards = 2;
    remainingLevelThreeOrientCards = 1;
    faceUpOrientCards = new ArrayList<>();
    availableGems = new HashMap<>();
    nobles = new ArrayList<>();
  }

  @Test
  void testConstructorAndGetters() {
    CityBoardDto cityBoardDto = new CityBoardDto(remainingLevelOneCards, remainingLevelTwoCards,
                                                 remainingLevelThreeCards, faceUpCards, remainingLevelOneOrientCards,
                                                 remainingLevelTwoOrientCards, remainingLevelThreeOrientCards, faceUpOrientCards,
                                                 availableGems, nobles, cities);

    assertEquals(remainingLevelOneCards, cityBoardDto.getRemainingLevelOneCards());
    assertEquals(remainingLevelTwoCards, cityBoardDto.getRemainingLevelTwoCards());
    assertEquals(remainingLevelThreeCards, cityBoardDto.getRemainingLevelThreeCards());
    assertEquals(faceUpCards, cityBoardDto.getFaceUpCards());
    assertEquals(remainingLevelOneOrientCards, cityBoardDto.getRemainingLevelOneOrientCards());
    assertEquals(remainingLevelTwoOrientCards, cityBoardDto.getRemainingLevelTwoOrientCards());
    assertEquals(remainingLevelThreeOrientCards, cityBoardDto.getRemainingLevelThreeOrientCards());
    assertEquals(faceUpOrientCards, cityBoardDto.getFaceUpOrientCards());
    assertEquals(availableGems, cityBoardDto.getAvailableGems());
    assertEquals(nobles, cityBoardDto.getNobles());
    assertEquals(cities, cityBoardDto.getCities());
  }

  @Test
  void testConstructorWithNullValues() {
    CityBoardDto cityBoardDto = new CityBoardDto(null, null, null, null, null,
                                                 null, null, null, null, null, cities);

    assertNull(cityBoardDto.getRemainingLevelOneCards());
    assertNull(cityBoardDto.getRemainingLevelTwoCards());
    assertNull(cityBoardDto.getRemainingLevelThreeCards());
    assertNull(cityBoardDto.getFaceUpCards());
    assertNull(cityBoardDto.getRemainingLevelOneOrientCards());
    assertNull(cityBoardDto.getRemainingLevelTwoOrientCards());
    assertNull(cityBoardDto.getRemainingLevelThreeOrientCards());
    assertNull(cityBoardDto.getFaceUpOrientCards());
    assertNull(cityBoardDto.getAvailableGems());
    assertNull(cityBoardDto.getNobles());
    assertEquals(cities, cityBoardDto.getCities());
  }

  @Test
  void testEqualsAndHashCode() {
    CityBoardDto cityBoardDto1 = new CityBoardDto(remainingLevelOneCards, remainingLevelTwoCards,
                                                  remainingLevelThreeCards, faceUpCards, remainingLevelOneOrientCards,
                                                  remainingLevelTwoOrientCards, remainingLevelThreeOrientCards, faceUpOrientCards,
                                                  availableGems, nobles, cities);
    CityBoardDto cityBoardDto2 = new CityBoardDto(remainingLevelOneCards, remainingLevelTwoCards,
                                                  remainingLevelThreeCards, faceUpCards, remainingLevelOneOrientCards,
                                                  remainingLevelTwoOrientCards, remainingLevelThreeOrientCards, faceUpOrientCards,
                                                  availableGems, nobles, cities);

    assertEquals(cityBoardDto2.hashCode(), cityBoardDto2.hashCode());
  }

  @Test
  void testEqualsAndHashCodeWithDifferentValues() {
    CityBoardDto cityBoardDto1 = new CityBoardDto(remainingLevelOneCards, remainingLevelTwoCards,
                                                  remainingLevelThreeCards, faceUpCards, remainingLevelOneOrientCards,
                                                  remainingLevelTwoOrientCards, remainingLevelThreeOrientCards, faceUpOrientCards,
                                                  availableGems, nobles, cities);

    CityBoardDto cityBoardDto2 = new CityBoardDto(5, 3, 2, new ArrayList<>(), 3,
                                                  2, 1, new ArrayList<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>());

    assertNotEquals(cityBoardDto1, cityBoardDto2);
    assertNotEquals(cityBoardDto1.hashCode(), cityBoardDto2.hashCode());
  }
}
