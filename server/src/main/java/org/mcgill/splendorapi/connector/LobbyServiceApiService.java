package org.mcgill.splendorapi.connector;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.connector.model.SaveGameRequest;
import org.mcgill.splendorapi.connector.model.Token;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.Player;
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
   * @param token   The token
   */
  public void putGameService(GameService service, Token token) {
    resource.putGameService(service.getName(), service, token.getToken());
  }

  public void saveSessionService(String service, String token, String gameId,
                                 SaveGameRequest body) {
    resource.saveGameToService(service, gameId, token, body);
  }

  /**
   * Saves games to the service.
   *
   * @param token The authentication token
   * @param gameObj Game
   * @param properties properties
   */
  public void putGameToService(String token, Game gameObj, AppProperties properties) {
    String gameType = properties.getRegistrationInformation()
                                .stream()
                                .filter(x -> x.getEndpoint().equals(gameObj.getType()))
                                .findFirst().orElseThrow().getName();
    SaveGameRequest bodyGame = SaveGameRequest.builder()
                                              .gameName(gameType)
                                              .players(gameObj.getPlayers()
                                                              .stream()
                                                              .map(Player::getName)
                                                              .collect(Collectors.toList()))
                                              .saveGameId(gameObj.getGameName()
                                                                 .replace(".json", ""))
                                              .build();
    resource.saveGameToService(gameType,
                               gameObj.getGameName()
                                      .replace(".json", ""),
                               token,
                               bodyGame);
  }

  /**
   * Deletes the game service.
   *
   * @param gameService Game service in question
   * @param token       The token
   */
  public void deleteGameService(GameService gameService, Token token) {
    resource.deleteGameService(gameService.getName(), token.getToken());
  }
}
