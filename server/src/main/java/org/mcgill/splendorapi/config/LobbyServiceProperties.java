package org.mcgill.splendorapi.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Properties for the lobby service connection.
 */
@ConstructorBinding
@ConfigurationProperties("lobby-service.infra")
@RequiredArgsConstructor
@Getter
public class LobbyServiceProperties {

  /**
   * The internal representation of the objects of the configuration.
   */
  @RequiredArgsConstructor
  @Getter
  public static class ServiceInformation {
    private final String url;
  }

  private final ServiceInformation api;
  private final ServiceInformation auth;
}
