package org.mcgill.splendorapi.config;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.model.GameType;

/**
 * Registration information class, encapsulates the information for the registration
 * at the lobby service.
 */
@RequiredArgsConstructor
public class RegistrationInformation {
  @NotBlank
  @Getter
  private final String name;

  @NotBlank
  private final String displayName;

  @NotBlank
  private final GameType endpoint;

  /**
   * Convert the object to game service object.
   *
   * @param minPlayers The min players
   * @param maxPlayers the number of max players
   * @param webSupport if the web can support the ui
   * @param host       The host information
   * @return The game service as an object
   */
  public GameService convertToGameService(Integer minPlayers,
                                          Integer maxPlayers,
                                          Boolean webSupport,
                                          String host) {
    String[] strings = new String[] {"%s", "%s"};
    String path = String.format(String.join(host.endsWith("/") ? "" : "/", strings),
                                host, endpoint.getValue());
    return new GameService(name,
                           displayName,
                           path,
                           maxPlayers,
                           minPlayers,
                           webSupport);
  }

  /**
   * Get the endpoint information.
   *
   * @return the endpoint
   */
  public GameType getEndpoint() {
    return endpoint;
  }
}
