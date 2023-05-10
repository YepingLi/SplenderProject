package org.mcgill.splendorapi.api.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.connector.LobbyServiceApiResource;
import org.mcgill.splendorapi.connector.LobbyServiceApiService;
import org.mcgill.splendorapi.model.Game;
import org.springframework.stereotype.Service;

/**
 * Loader service, only service which interacts with the FS.
 * Encapsulates the FS interaction.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GameLoaderService {
  private static final ObjectMapper mapper = new ObjectMapper();
  private final AppProperties properties;

  /**
   * Load the game from the disk.
   *
   * @param gameId    LauncherInfo id
   * @throws IOException          If we cannot find the game
   * @throws InvalidPathException If the path is invalid.
   */
  public synchronized Game loadGameFromDisk(String gameId)
        throws IOException, InvalidPathException, IllegalAccessException {
    if (gameId.equals("")) {
      throw new IllegalAccessException("Cannot load file with no name");
    }

    Path path = Paths.get(
          properties.getAbsPathToGames().toString(),
          String.format("%s.json", gameId)
    );

    BufferedReader buffered = new BufferedReader(new FileReader(path.toFile()));
    StringBuilder fileAsString = new StringBuilder();
    String line;

    while ((line = buffered.readLine()) != null) {
      fileAsString.append(line);
    }

    buffered.close();
    return mapper.readValue(fileAsString.toString(), Game.class);
  }

  /**
   * Save the game to the disk.
   *
   * @param game the game
   * @throws IOException During saving
   */
  public synchronized void saveGame(Game game, String randomId) throws IOException {
    Path path = Paths.get(
          properties.getAbsPathToGames().toString(),
          String.format("%s.json", randomId)
    );
    BufferedWriter buffered = new BufferedWriter(new FileWriter(path.toFile()));
    buffered.write(mapper.writeValueAsString(game));
    buffered.flush();
    buffered.close();
  }

  public void getGame(String s) {
  }
}
