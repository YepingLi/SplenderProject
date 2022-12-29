package org.mcgill.splendorapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Game object.
 */
@Jacksonized
@Builder
@Getter
public class Game {
  private List<Player> players;
  private String gameName;
  private GameBoard board;
  private String creator;
  private String gameServer;

  @JsonProperty("savegame")
  private String saveGame;

  // Final object which is started or not (but cannot be changed once it has been started).
  private boolean launched;

  /**
   * Check if the user has access to this game.
   *
   * @param username The username trying to load the game
   * @return The game
   * @throws IllegalAccessException If the user does not have access to this game.
   */
  public Game checkUserRights(String username) throws IllegalAccessException {
    if (players.stream().filter(player -> player.getName().equals(username)).count() != 1) {
      throw new IllegalAccessException(
          "You do not have sufficient rights to connect with this game");
    }
    return this;
  }

  /**
   * Start the game.
   *
   * @param theBoard The board attached to the game
   */
  public void startGame(GameBoard theBoard) {
    if (launched) {
      throw new IllegalStateException("Game cannot be launched and making a new game with it");
    }

    board = theBoard;
  }
}
