package org.mcgill.splendorapi.model;

import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Gem;
import org.mcgill.splendorapi.model.GemType;

import static org.junit.jupiter.api.Assertions.*;

class GemTest {
  Gem testGem1 = Gem.builder()
                    .type(GemType.DIAMOND)
                    .isAvailable(false)
                    .id("abc").build();
  Gem testGem2 = Gem.builder().type(GemType.DIAMOND).isAvailable(true).build();

  @Test
  void free() {
    testGem1.free();
    assertTrue(testGem1.isAvailable());
  }

  @Test
  void purchase() {
    testGem2.purchase();
    assertFalse(testGem2.isAvailable());
  }

  @Test
  void getId() {
    assertSame("abc", testGem1.getId());
  }

  @Test
  void getUri() {
    assertSame("abc", testGem1.getId());
  }

  @Test
  void isAvailable() {
    assertFalse(testGem1.isAvailable());
    assertTrue(testGem2.isAvailable());
  }

  @Test
  void getType() {
    assertSame(GemType.DIAMOND, testGem1.getType());
  }

  @Test
  void builder() {
    Gem testGem2 = Gem.builder().type(GemType.DIAMOND).isAvailable(true).build();
    assertSame(GemType.DIAMOND, testGem2.getType());
    assertTrue(testGem2.isAvailable());
  }
}