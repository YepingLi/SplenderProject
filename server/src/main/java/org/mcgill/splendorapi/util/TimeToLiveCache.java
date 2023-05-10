package org.mcgill.splendorapi.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.lang3.tuple.Pair;
import org.mcgill.splendorapi.model.Move;

/**
 * Cache for the game moves with a time to live (not forever).
 */
public class TimeToLiveCache {
  private static final Map<String, Pair<Timer, List<Move>>> gameMoveMap = new HashMap<>();
  private final long timeout;

  public TimeToLiveCache(long theTimeout) {
    timeout = theTimeout;
  }

  /**
   * Handles adding and managing the data in the map.
   *
   * @param gameId The game.
   * @param moves  The list of moves being added to the map.
   */
  public synchronized void addMovesForGame(String gameId, List<Move> moves) {
    Timer t = new Timer();
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        gameMoveMap.remove(gameId);
      }
    }, timeout);
    gameMoveMap.compute(gameId, (x, pair) -> {
      if (pair != null) {
        pair.getLeft().cancel();
      }
      return Pair.of(t, moves);
    });
  }

  /**
   * Remove and cancel the timer.
   *
   * @param gameId The game id
   * @return The moves
   */
  public synchronized List<Move> getGeneratedMoves(String gameId) {
    Pair<Timer, List<Move>> pair = Optional.ofNullable(gameMoveMap.remove(gameId))
                                           .orElseThrow(() -> new IllegalStateException(
                                             "Trying to get moves on a game without moves!"));
    pair.getLeft().cancel();
    return pair.getRight();
  }

  protected static Map<String, Pair<Timer, List<Move>>> getGameMoveMap() {
    return gameMoveMap;
  }
}
