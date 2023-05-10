package org.mcgill.splendorapi.connector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.mcgill.splendorapi.api.game.GameLoaderService;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.GameServicesConfig;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.connector.model.Token;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.springframework.stereotype.Component;


/**
 * Register to the lobby service.
 */
@Slf4j
@Component
public class RegisterLobbyService {
  private final LobbyServiceApiService service;
  private final TokenManager tokenManager;
  private final AppProperties properties;

  @Getter
  private final Map<GameType, GameService> services;
  private final GameLoaderService gameLoader;
  @Getter
  private final Map<GameType, Boolean> successfulRegistry = new HashMap<>();

  /**
   * Builds the registration bean.
   */
  public RegisterLobbyService(TokenManager manager, LobbyServiceApiService serviceApiService,
                              GameServicesConfig servicesConfig, AppProperties appProperties,
                              GameLoaderService loader) {
    tokenManager = manager;
    service = serviceApiService;
    services = servicesConfig.getServices();
    properties = appProperties;
    gameLoader = loader;
  }

  /**
   * Perform the registration.
   */
  @PostConstruct
  public void register() throws IOException, ParseException, IllegalAccessException {

    Token token = tokenManager.token();
    services.forEach((key, value) -> successfulRegistry.computeIfAbsent(key, (a) -> {
      try {
        service.putGameService(value, token);
        log.info("Successfully registered {} to the lobby service!", a.getValue());
        return true;
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }));
    Files.createDirectories(properties.getAbsPathToGames());
    File folder = new File(properties.getAbsPathToGames().toString());
    for (File file : Objects.requireNonNull(folder.listFiles())) {
      Game game = gameLoader.loadGameFromDisk(file.getName().replace(".json", ""));
      service.putGameToService(token.getToken(), game, properties);
    }
    log.info("Successfully registered all to the lobby service");
  }

  /**
   * Deregister the api at the lobby service.
   */
  @PreDestroy
  public void deregister() {
    Token token = tokenManager.token();
    services.forEach((key, value) -> successfulRegistry.computeIfPresent(key, (a, b) -> {
      if (!b) {
        log.debug("Cannot deregister {} from LS since never managed to connect it in first place!",
                  a.getValue());
        return false;
      }

      try {
        service.deleteGameService(value, token);
        log.info("Successfully registered {} to the lobby service!", a.getValue());
        return false;
      } catch (Exception e) {
        return true;
      }
    }));
    log.info("Successfully unregistered from LS");
  }
}
