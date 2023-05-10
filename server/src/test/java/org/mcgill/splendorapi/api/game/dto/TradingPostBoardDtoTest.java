package org.mcgill.splendorapi.api.game.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.PowerType;
import org.mcgill.splendorapi.model.TradingPost;
import org.mcgill.splendorapi.model.card.*;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

public class TradingPostBoardDtoTest {

  TradingPostBoardDto tradingPostBoardDto;

  @BeforeEach
  public void setup() throws InvalidCardType {
    // Setup a sample TradingPostBoardDto object for testing
    Map<GemType, Integer> availableGems = new HashMap<>();
    availableGems.put(GemType.DIAMOND, 2);
    availableGems.put(GemType.GOLD, 1);

    List<DevelopmentCard> faceUpCards = new ArrayList<>();
    faceUpCards.add(new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 3, new HashMap<>()));
    faceUpCards.add(new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 3, new HashMap<>()));

    List<DevelopmentCard> faceUpOrientCards = new ArrayList<>();
    faceUpOrientCards.add(new OrientDevelopmentCard((new DevelopmentCardMeta((short) 1, (short) 1, CardType.ORIENT)),
                                                    Bonus.EMERALD, null, 3, new HashMap<>(), 0, false, CardState.FREE));

    List<NobleCard> nobles = new ArrayList<>();
    nobles.add(new NobleCard(new CardMeta((short) 1, CardType.NOBLE), 1,1, new HashMap<>()));

    List<TradingPost> tradingPosts = new ArrayList<>();
    tradingPosts.add(new TradingPost(new CardMeta((short) 1, CardType.TRADING_POST), PowerType.FREE_POINTS, new HashMap<>()));

    tradingPostBoardDto = new TradingPostBoardDto(10, 20, 30, faceUpCards, 5, 5, 5, faceUpOrientCards, availableGems, nobles, tradingPosts);
  }

  @Test
  public void testGetTradingPosts() {
    List<TradingPost> tradingPosts = tradingPostBoardDto.getTradingPosts();
    Assertions.assertEquals(1, tradingPosts.size());
  }
  // Test other methods as required
}
