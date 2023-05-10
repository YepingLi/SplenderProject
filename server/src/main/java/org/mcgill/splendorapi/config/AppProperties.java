package org.mcgill.splendorapi.config;

import java.nio.file.Path;
import java.util.List;
import javax.annotation.PostConstruct;
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
  @NotNull
  private final List<RegistrationInformation> registrationInformation;
  @NotNull
  private final Path pathToGames;
  @NotNull
  private final Integer minPlayers;
  @NotNull
  private final Integer maxPlayers;
  @NotNull
  private final OpenIdAuth2 authCredentials;
  @NotNull
  private final Integer maxTimeout;
  @NotNull
  private final Integer cacheTimeout;
  @NotNull
  private final Integer gameIdLength;
  @NotBlank
  private final String cardDefs;
  @NotBlank
  private final String orientDefs;
  @NotBlank
  private final String nobleDefs;
  @NotBlank
  private final String tradingPostDefs;
  @NotBlank
  private final String cityDefs;

  private final String backgroundsDefs = "Backgrounds.json";
  @NotNull
  private final AssetPath pathToCards;
  @NotNull
  private final AssetPath pathToNobles;
  @NotNull
  private final AssetPath pathToTradingPosts;
  @NotNull
  private final AssetPath pathToTokens;

  @NotNull
  private final AssetPath pathToBackgrounds;
  @NotNull
  private final AssetPath pathToCities;
  @NotNull
  private final Boolean webSupport;

  public Path getAbsPathToGames() {
    return pathToGames.toAbsolutePath();
  }

  /**
   * check the sanity.
   */
  @PostConstruct
  public void sanityCheck() {
    assert minPlayers <= maxPlayers;
    if (pathToTradingPosts == null) {
      throw new AssertionError("Failed assertion error");
    }
    ;

  }

}
