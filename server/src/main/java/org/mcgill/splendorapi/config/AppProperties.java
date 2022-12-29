package org.mcgill.splendorapi.config;

import java.nio.file.Path;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


/**
 * Properties to be read out of the yml config file.
 */
@ConstructorBinding
@ConfigurationProperties("app")
@RequiredArgsConstructor
@Getter
public class AppProperties {
  @NotBlank private final String name;
  @NotBlank private final String displayName;
  @NotNull private final Path pathToGames;
  @NotNull private final Integer minPlayers;
  @NotNull private final Integer maxPlayers;
  @NotNull private final OpenIdAuth2 authCredentials;
  @NotNull private final Integer maxTimeout;
  @NotNull private final Integer gameIdLength;
  @NotBlank private final String cardDefs;

  public Path getAbsPathToGames() {
    return pathToGames.toAbsolutePath();
  }
}
