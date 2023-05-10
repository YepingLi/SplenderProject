package org.mcgill.splendorapi.api.game.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.Player;

/**
 * Builds a GameDTO which is safe for the Clients to see.
 */
@Builder
@Getter
public class GameDto {
  private final List<Player> players;
  private final String gameName;
  private final BaseGameBoardDto board;
  private final String creator;
  private final String gameServer;
  // Discriminator
  private final GameType type;
  private final boolean launched;
  private final Player curPlayer;
  private final boolean isOver;
}
