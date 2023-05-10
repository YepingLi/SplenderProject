package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Description of LauncherInfo.
 */
@Jacksonized
@Builder
@Getter
public class LauncherInfo {
  @JsonProperty("gamename")
  String name;

  @JsonProperty("players")
  List<LsPlayer> players;

  @JsonProperty("savegame")
  String saveGameId;

  @JsonProperty("creator")
  String creator;

  public boolean hasId() {
    return Optional.ofNullable(saveGameId).map(x -> !x.equals("")).orElse(false);
  }
}
