package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.BasePlayer;

class BasePlayerTest {

  BasePlayer testBasePlayer = new BasePlayer("abc");
  @Test
  void getName() {
    assertSame("abc", testBasePlayer.getName());
  }
}