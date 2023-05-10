package org.mcgill.splendorapi.api.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import org.mcgill.splendorapi.api.game.dto.GameDto;
import org.mcgill.splendorapi.model.Game;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Manages the games currently being played. All methods are synchronized since we can have issues
 * with multiple accesses at once (concurrency)
 */
@Builder
@Getter
@Service
public class GameManagerService {
  private final Map<String, Game> gameMapping;
  /**
   * Is the current game being played.
   *
   * @param key The id of the session
   * @return If the map contains the id
   */

  public synchronized boolean hasGame(String key) {
    return gameMapping.containsKey(key);
  }

  /**
   * Get the game for that session.
   *
   * @param id The id of the session
   * @return The game associated to the id
   */
  public synchronized Game getGame(String id) {
    return gameMapping.get(id);
  }

  /**
   * Add the game to the map.
   *
   * @param id The id of the session
   * @param game The game being added
   */
  public synchronized void putGame(String id, Game game) {
    gameMapping.put(id, game);
  }

  /**
   * Remove the game associated to the id.
   *
   * @param id The id of the session
   * @return The game associated to the id
   */
  public synchronized Game getAndRemove(String id) {
    return gameMapping.remove(id);
  }
}
