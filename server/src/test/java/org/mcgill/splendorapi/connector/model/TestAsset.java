package org.mcgill.splendorapi.connector.model;

import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Asset;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TestAsset {
  @Test
  public void testAsset(){
    Integer id = 15;
    String name = "Name";
    Asset<Integer> asset = new Asset<>(id, name);
    assertThat(asset.asView()).isEqualTo(Map.entry(name, id.toString()));
  }
}
