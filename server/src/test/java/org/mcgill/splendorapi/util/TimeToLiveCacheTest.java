package org.mcgill.splendorapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.model.Move;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TimeToLiveCacheTest {

  private static final long TIMEOUT = 1000L;
  private static final String GAME_ID = "game1";

  @Captor
  private ArgumentCaptor<TimerTask> taskCaptor;

  private TimeToLiveCache cache;

  @BeforeEach
  public void setUp() {
    cache = new TimeToLiveCache(TIMEOUT);
  }

  @Test
  public void testAddMovesForGame() {
    List<Move> moves = new ArrayList<>();
    moves.add(mock(Move.class));
    cache.addMovesForGame(GAME_ID, moves);
    assertEquals(1, TimeToLiveCache.getGameMoveMap().size());
  }

  @Test
  public void testGetGeneratedMoves() {
    List<Move> moves = new ArrayList<>();
    moves.add(mock(Move.class));
    cache.addMovesForGame(GAME_ID, moves);

    List<Move> retrievedMoves = cache.getGeneratedMoves(GAME_ID);
    assertEquals(1, retrievedMoves.size());
    assertEquals(moves.get(0), retrievedMoves.get(0));
    assertEquals(0, TimeToLiveCache.getGameMoveMap().size());

    assertThrows(IllegalStateException.class, () -> cache.getGeneratedMoves(GAME_ID));
  }

}
