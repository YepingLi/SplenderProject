package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Token representation received from the lobby service.
 */
@Getter
public class Token extends TokenCredentials {

  private final Integer expiresIn;
  private final String refreshToken;
  private final Scope scope;
  private final LocalDateTime createdTime = LocalDateTime.now();

  /**
   * Creates the token object.
   *
   * @param type           The token type
   * @param tokenScope     the token scope
   * @param refreshTok     the refresh token
   * @param tokenExpiresIn expiration of the token
   * @param accessToken    the access token
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  @Jacksonized
  @Builder
  public Token(@JsonProperty("token_type")
               TokenType type,
               @JsonDeserialize(using = Scope.ScopeDeserializer.class)
               @JsonProperty("scope")
               Scope tokenScope,
               @JsonProperty("refresh_token")
               String refreshTok,
               @JsonProperty("expires_in")
               Integer tokenExpiresIn,
               @JsonProperty("access_token")
               String accessToken) {
    super(accessToken, type);
    expiresIn = tokenExpiresIn;
    refreshToken = refreshTok;
    scope = tokenScope;
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(createdTime.plusSeconds(expiresIn));
  }

}