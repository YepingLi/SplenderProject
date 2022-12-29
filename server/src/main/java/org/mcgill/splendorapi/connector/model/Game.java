package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Description of Game.
 */
@Builder
@Getter
public class Game {
  @JsonProperty("gamename")
  String gameName;

  List<String> players;

  @JsonProperty("savegameid")
  String saveGameId;
}
