package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GemTest {
 Gem testGem1 = Gem.builder().type(Gem.Type.DIAMOND).isAvailable(false).
     id("abc").uri("abc").build();
 Gem testGem2 = Gem.builder().type(Gem.Type.DIAMOND).isAvailable(true).build();
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
   assertSame("abc", testGem1.getUri());
  }

  @Test
  void isAvailable() {
   assertFalse(testGem1.isAvailable());
   assertTrue(testGem2.isAvailable());
  }

  @Test
  void getType() {
   assertSame(Gem.Type.DIAMOND,testGem1.getType());
  }

  @Test
  void builder() {
   Gem testGem2 = Gem.builder().type(Gem.Type.DIAMOND).isAvailable(true).build();
   assertSame(Gem.Type.DIAMOND, testGem2.getType());
   assertTrue(testGem2.isAvailable());
  }
}