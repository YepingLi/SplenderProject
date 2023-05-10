package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Request to send to ls.
 */
@Jacksonized
@Builder
@Getter
public class SaveGameRequest {
  @JsonProperty("gamename")
  String gameName;
  @JsonProperty("players")
  List<String> players;
  @JsonProperty("savegameid")
  String saveGameId;
}

