package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BasePlayerTest {

  BasePlayer testBasePlayer = new BasePlayer("abc");
  @Test
  void getName() {
    assertSame("abc", testBasePlayer.getName());
  }
}