package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.card.CardMeta;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

public class AdditionPlayer {
  Player player = Player.builder().build();
  private City city;
  private CardMeta meta;
  private int prestigePoints;
  private int sameRequirement;

  @BeforeEach
  void citySetUp() {
    meta = new CardMeta((short) 1, CardType.CITY);
    prestigePoints = 5;
    sameRequirement = 2;
    try {
      city = new City(meta, prestigePoints, new HashMap<>(), sameRequirement);
    } catch (InvalidCardType e) {
      fail("Unexpected InvalidCardType exception");
    }
  }

  @Test
  void addCityTest1() throws InvalidCardType {
    //TEST 1: THE PLAYER DOES NOT HAVE GOLD.

    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY, 3);
    tokens.put(GemType.EMERALD, 1);
    tokens.put(GemType.ONYX, 2);

    //BUILD THE PLAYER
    Player player =
      Player.builder().playerPrestige(0).gems(tokens).bonuses(new HashMap<>()).build();

    //BUILD THE CITY
    citySetUp();
    City copy1 = new City(city);
    player.addCity(copy1);
    assertSame(copy1, player.getCities().get(0));

  }

  @Test
  void addCityTest2() throws InvalidCardType {
    //TEST 1: THE PLAYER DOES NOT HAVE GOLD.

    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY, 3);
    tokens.put(GemType.EMERALD, 1);
    tokens.put(GemType.ONYX, 2);

    //BUILD THE PLAYER
    Player player =
      Player.builder().playerPrestige(0).gems(tokens)
            .cities(new ArrayList<>())
            .bonuses(new HashMap<>())
            .build();

    //BUILD THE CITY
    citySetUp();
    City copy1 = new City(city);
    player.addCity(copy1);
    assertSame(copy1, player.getCities().get(0));

  }


}
