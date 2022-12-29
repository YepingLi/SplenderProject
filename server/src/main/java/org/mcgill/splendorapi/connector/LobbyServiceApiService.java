package org.mcgill.splendorapi.connector;

import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.connector.model.Token;
import org.springframework.stereotype.Service;

/**
 * Resource wrapping to permit proper injection of objects.
 */
@Service
@RequiredArgsConstructor
public class LobbyServiceApiService {
  private final LobbyServiceApiResource resource;

  /**
   * Wraps the resource (which is not public) and injects the proper information.
   *
   * @param service The game service information
   * @param token The token
   */
  public void putGameService(GameService service, Token token) {
    resource.putGameService(service.getName(), service, token.getToken());
  }

  /**
   * Deletes the game service.
   *
   * @param gameService Game service in question
   * @param token The token
   */
  public void deleteGameService(GameService gameService, Token token) {
    resource.deleteGameService(gameService.getName(), token.getToken());
  }

}
