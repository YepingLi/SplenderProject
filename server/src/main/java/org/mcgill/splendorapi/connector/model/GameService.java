package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Description of GameService.
 */
@Getter
public class GameService extends MinimalGameService {
  private final String location;
  private final Integer maxSessionPlayers;
  private final Integer minSessionPlayers;
  private final Boolean webSupport;

  /**
   * Builds the object.
   *
   * @param name name of the service.
   * @param displayName display name of the service.
   * @param location location of the service.
   * @param maxPlayers maximum players of the service.
   * @param support supports.
   * @param minPlayers min number of players.
   */
  @Builder
  public GameService(@JsonProperty("name") String name,
                     @JsonProperty("displayName") String displayName,
                     @JsonProperty("name") String location,
                     @JsonProperty("maxSessionPlayers") Integer maxPlayers,
                     @JsonProperty("minSessionPlayers") Integer minPlayers,
                     @JsonProperty("webSupport") Boolean support) {
    super(name, displayName);
    this.location = location;
    webSupport = support;
    minSessionPlayers = minPlayers;
    maxSessionPlayers = maxPlayers;
  }
}
