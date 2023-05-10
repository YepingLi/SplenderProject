package org.mcgill.splendorapi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mcgill.splendorapi.model.card.CardMeta;
import org.mcgill.splendorapi.model.card.CardType;

public class TradingPostTest {

  private CardMeta cardMeta;
  private PowerType powerType;
  private Map<Bonus, Integer> postRequirements;
  private TradingPost tradingPost;

  @Before
  public void setUp() {
    cardMeta = new CardMeta((short) 1, CardType.TRADING_POST);
    powerType = PowerType.FREE_POINTS;
    postRequirements = new HashMap<>();

    tradingPost = new TradingPost(cardMeta, powerType, postRequirements);
  }

  @Test
  public void testConstructor() {
    assertEquals(cardMeta, tradingPost.getMeta());
    assertEquals(powerType, tradingPost.getPower());
    assertEquals(postRequirements, tradingPost.getRequirements());
  }

  @Test
  public void testAddCoat() {
    Color color = Color.BLUE;
    tradingPost.addCoat(color);
    assertTrue(tradingPost.getCoats().contains(color));
  }

  @Test
  public void testRemoveCoat() {
    Color color = Color.RED;
    tradingPost.addCoat(color);
    tradingPost.removeCoat(color);
    assertTrue(!tradingPost.getCoats().contains(color));
  }

  @Test
  public void testSerialization() throws JsonProcessingException {

    ObjectMapper objectMapper = new ObjectMapper();
    String serializedTradingPost = objectMapper.writeValueAsString(tradingPost);
    TradingPost deserializedTradingPost = objectMapper.readValue(serializedTradingPost, TradingPost.class);

    assertEquals(tradingPost.getMeta().getId(), deserializedTradingPost.getMeta().getId());
    assertEquals(tradingPost.getMeta().getType(), deserializedTradingPost.getMeta().getType());
    assertEquals(tradingPost.getPower(), deserializedTradingPost.getPower());
    assertEquals(tradingPost.getRequirements(), deserializedTradingPost.getRequirements());

  }
}
