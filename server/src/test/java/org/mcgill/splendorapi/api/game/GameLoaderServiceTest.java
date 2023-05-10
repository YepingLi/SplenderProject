package org.mcgill.splendorapi.api.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.model.Game;

@ExtendWith(MockitoExtension.class)
public class GameLoaderServiceTest {

  private GameLoaderService gameLoaderService;

  @Mock
  private AppProperties properties;

  @BeforeEach
  public void setup() {
    gameLoaderService = new GameLoaderService(properties);
  }

  @Test
  public void testLoadGameFromDisk() throws Exception {
  }

  @Test
  public void testLoadGameFromDiskWithEmptyGameId() throws Exception {
    // Given
    String gameId = "";

    // When / Then
    assertThrows(IllegalAccessException.class, () -> gameLoaderService.loadGameFromDisk(gameId));
  }

  @Test
  public void testLoadGameFromDiskWithInvalidPath() throws Exception {
  }

  @Test
  public void testSaveGame() {

  }
}
