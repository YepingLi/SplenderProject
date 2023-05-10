package org.mcgill.splendorapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class AssetTest {

  @Test
  void testAssetCreation() {
    Asset<Integer> asset = new Asset<>(1, "Gold");
    assertNotNull(asset, "Asset should be created successfully");
  }

  @Test
  void testAssetAsView() {
    Integer id = 1;
    String name = "Gold";
    Asset<Integer> asset = new Asset<>(id, name);
    Map.Entry<String, String> view = asset.asView();
    assertEquals(name, view.getKey(), "Key in the view should match the asset name");
    assertEquals(id.toString(), view.getValue(), "Value in the view should match the asset ID");
  }
}
