package org.mcgill.splendorapi.api.assetloader;

import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.AssetPath;
import org.mcgill.splendorapi.config.OpenIdAuth2;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProducerService {
  ProducerService loader = new ProducerService(
    new AppProperties(
      null,Paths.get(""),
      100,
      200,
      new OpenIdAuth2("", "", ""),
      200,
      200,
      12,
      "cards.json", "orientCards.json", "nobles.json", "tradingPosts.json",
      "cities.json",
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      false
    )
  );

  public TestProducerService() throws IOException {
  }

  @Test
  public void testLoadCards() {
    assertEquals(1, loader.getCards().size());
  }

  @Test
  public void testLoadOrientCards() {
    assertEquals(1, loader.getOrientCards().size());
  }

  @Test
  public void testLoadNobles() {
    assertEquals(1, loader.getNobleCards().size());
  }

  @Test
  public void testLoadTradingPosts() {
    assertEquals(1, loader.getTradingPosts().size());
  }
}
