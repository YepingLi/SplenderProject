package org.mcgill.splendorapi.connector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.OpenIdAuth2;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.connector.model.GrantType;
import org.mcgill.splendorapi.connector.model.Token;
import org.mcgill.splendorapi.connector.model.UserCredentials;
import org.springframework.stereotype.Component;


/**
 * Register to the lobby service.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RegisterLobbyService {
  private final LobbyServiceApiService service;
  private final LobbyServiceAuthResource authService;
  private final AppProperties properties;

  private GameService gameService;

  /**
   * Get the token from the lobby service.
   *
   * @return Token object received.
   */
  private Token getToken() {
    OpenIdAuth2 config = properties.getAuthCredentials();
    UserCredentials creds = UserCredentials.builder()
                                .name(config.getName())
                                .passcode(config.getPassword())
                                .build();
    return authService.postTokenPassword(
        GrantType.PASSWORD.getValue(),
        creds.getName(),
        creds.getPasscode(),
        config.getToken()
    );
  }

  /**
   * Inject the game service into the object in a thread-safe manner.
   *
   * @return game service
   */
  private synchronized GameService injectGameService() {
    if (gameService == null) {
      gameService = new GameService(
          properties.getName(),
          properties.getDisplayName(),
          "http://localhost:8080",
          properties.getMaxPlayers(),
          properties.getMinPlayers(),
          false
      );
    }
    return gameService;
  }

  /**
   * Perform the registration.
   */
  @PostConstruct
  public void register() {

    Token token = getToken();

    service.putGameService(injectGameService(), token);
    log.info("Successfully registered to the lobby service");
  }

  /**
   * Deregister the api at the lobby service.
   */
  @PreDestroy
  public void deregister() {
    Token token = getToken();
    service.deleteGameService(injectGameService(), token);
    log.info("Successfully unregistered from LS");
  }
}
