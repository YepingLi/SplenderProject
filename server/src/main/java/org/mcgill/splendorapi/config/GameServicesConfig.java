package org.mcgill.splendorapi.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.model.GameType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Configures all game service information.
 */
@Configuration
public class GameServicesConfig {
  private final Map<GameType, GameService> services;
  private final Environment environment;
  private final String baseHost;

  /**
   * Resolve the port number.
   *
   * @return port as a string.
   */
  private String getPort() {
    return environment.getProperty("server.port");
  }

  private static String getHost() throws UnknownHostException {
    return InetAddress.getLocalHost().getHostAddress();
  }

  private ImmutablePair<GameType, GameService> producer(RegistrationInformation info,
                                                        AppProperties properties) {
    return new ImmutablePair<>(info.getEndpoint(),
                               info.convertToGameService(properties.getMinPlayers(),
                                                         properties.getMaxPlayers(),
                                                         properties.getWebSupport(),
                                                         baseHost));
  }

  /**
   * Builds the object and builds the game services' information.
   * Fails if we cannot resolve the host.
   *
   * @param properties app properties.
   * @param env the environment.
   */
  public GameServicesConfig(AppProperties properties,
                            Environment env) throws UnknownHostException  {
    environment = env;
    baseHost = String.format("http://%s:%s/", getHost(), getPort());
    services = properties.getRegistrationInformation()
                         .stream()
                         .map(info -> producer(info, properties))
                         .collect(Collectors.toUnmodifiableMap(ImmutablePair::getLeft,
                                                               ImmutablePair::getRight));
  }

  /**
   * Get the game services map.
   *
   * @return The map
   */
  public Map<GameType, GameService> getServices() {
    return services;
  }

  /**
   * Get the host information from the bean.
   *
   * @return host information
   */
  public String getBaseHost() {
    return baseHost;
  }
}
