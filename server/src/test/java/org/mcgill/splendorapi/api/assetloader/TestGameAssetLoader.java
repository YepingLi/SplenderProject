package org.mcgill.splendorapi.api.assetloader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.config.OpenIdAuth2;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.AssetPath;
import org.mcgill.splendorapi.model.AssetType;
import org.mcgill.splendorapi.model.GemType;

@ExtendWith(MockitoExtension.class)
public class TestGameAssetLoader {

  private AppProperties properties = new AppProperties(
    null, Paths.get(""),
    100,
    200,
    new OpenIdAuth2("", "", ""),
    200, 200,
    12,
    "cards.json", "orientCards.json", "nobles.json", "tradingPosts.json",
    "cities.json",
    new AssetPath("$NAME$Level$LEVEL$/card$ID$.png", List.of(new AssetPath.Default("name", ""))),
    new AssetPath("noble$ID$.png", Collections.emptyList()),
    new AssetPath("TradingPost$ID$.png", Collections.emptyList()),
    new AssetPath("$ID$.png", Collections.emptyList()),
    new AssetPath("background.png", Collections.emptyList()),
    new AssetPath("city$ID$.png", Collections.emptyList()),
    false
  );

  private GameAssetLoader gameAssetLoader;

  @BeforeEach
  void setUp() {
    gameAssetLoader = new GameAssetLoader(properties);
  }

  @Test
  void testLoadNobleTradingPostAsset() throws IOException {
    byte[] asset = gameAssetLoader.loadNobleTradingPostAsset("1", AssetType.Noble);
    assertNotNull(asset);
    // Perform additional assertions as needed
  }

  @Test
  void testLoadTokenAsset() throws IOException {
    byte[] asset = gameAssetLoader.loadTokenAsset(GemType.DIAMOND.name());
    assertNotNull(asset);
    // Perform additional assertions as needed
  }

  @Test
  void testLoadBackgroundAsset() throws IOException {
    byte[] asset = gameAssetLoader.loadBackgroundAsset("1");
    assertNotNull(asset);
    // Perform additional assertions as needed
  }

  @Test
  void testLoadCardAsset() throws IOException {
    byte[] asset = gameAssetLoader.loadCardAsset("0", 1, AssetType.Card);
    assertNotNull(asset);
    // Perform additional assertions as needed
  }

  @Test
  void testLoadOrientCardAsset() throws IOException {
    byte[] asset = gameAssetLoader.loadCardAsset("1", 1, AssetType.Orient);
    assertNotNull(asset);
    // Perform additional assertions as needed
  }


  @Test
  void testVerifyIsNotNum() throws IOException {
    assertThatThrownBy(() -> gameAssetLoader.loadCardAsset("xb31", 1, AssetType.Orient)).isInstanceOf(NumberFormatException.class);
  }
  @Test
  void testVerifyIsNotLevel() throws IOException {
    assertThatThrownBy(() -> gameAssetLoader.loadCardAsset("1", null, AssetType.Orient)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testLoadCity() throws IOException {
    byte[] asset = gameAssetLoader.loadCardAsset("0", 1, AssetType.Card);
    assertNotNull(asset);;
  }


  @Test
  void testVerifyNoFile() throws IOException {
    assertThatThrownBy(() -> gameAssetLoader.loadCardAsset("5", 5, AssetType.Orient)).isInstanceOf(IOException.class);
  }
}
