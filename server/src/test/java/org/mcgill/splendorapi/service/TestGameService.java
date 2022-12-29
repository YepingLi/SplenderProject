package org.mcgill.splendorapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles({"test"})
@SpringBootTest
public class TestGameService {
  @Autowired
  private GameLoaderService service;

  @Test
  public void should_load_one() {
    assertThrows(IllegalArgumentException.class, () -> service.getGame("123"));
  }
}
