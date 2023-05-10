package org.mcgill.splendorapi.connector;

import java.util.Optional;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.OpenIdAuth2;
import org.mcgill.splendorapi.connector.model.GrantType;
import org.mcgill.splendorapi.connector.model.Token;
import org.mcgill.splendorapi.connector.model.UserCredentials;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * Token manager for getting information from the lobby service.
 */
@Component
@Service
public class TokenManager {
  private final LobbyServiceAuthResource authResource;
  private final OpenIdAuth2 config;
  private Token token;

  /**
   * Build the token manager. Use only a subpart of the properties object

   * @param properties The application properties
   *
   * @param authResource The authentication resource
   */
  public TokenManager(AppProperties properties,
                      LobbyServiceAuthResource authResource) {
    this.authResource = authResource;
    config = properties.getAuthCredentials();
  }

  /**
   * Get the original token.
   *
   * @return The token
   */
  private Token getToken() {
    UserCredentials creds = UserCredentials.builder()
                                           .name(config.getName())
                                           .passcode(config.getPassword())
                                           .build();
    token = authResource.postTokenPassword(
      GrantType.PASSWORD.getValue(),
      creds.getName(),
      creds.getPasscode(),
      config.getToken()
    );

    return token;
  }

  /**
   * Get the token from the refresh token.
   *
   * @return The token
   */
  private Token refreshToken() {
    token = authResource.postTokenToken(
      GrantType.TOKEN.getValue(),
      token.getRefreshToken(),
      config.getToken()
    );
    return token;
  }

  /**
   * Get the token from the lobby service.
   *
   * @return Token object received.
   */
  public Token token() {
    Token curToken = Optional.ofNullable(token)
                             .orElseGet(this::getToken);
    if (curToken.isExpired()) {
      return refreshToken();
    }
    return curToken;
  }
}
