package org.mcgill.splendorapi.connector;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.AssetPath;
import org.mcgill.splendorapi.config.OpenIdAuth2;
import org.mcgill.splendorapi.config.RegistrationInformation;
import org.mcgill.splendorapi.connector.model.Token;
import org.mcgill.splendorapi.connector.model.TokenType;
import org.mcgill.splendorapi.model.GameType;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenManagerTest {
  private AppProperties factory(List<RegistrationInformation> info) {
    return new AppProperties(
      info,
      Paths.get(""),
      100, 200,
      new OpenIdAuth2("name", "pass", "something"),
      200, 12, 2,
      "cards.json", "orientCards.json", "nobles.json", "tradingPosts.json",
      "cities.json",
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      false
    );
  }

  public TokenManager before(LobbyServiceAuthResource resource,
                             List<RegistrationInformation> info) {
    return new TokenManager(factory(info), resource);
  }

  @Test
  void testGetToken(@Mock LobbyServiceAuthResource resource) {
    RegistrationInformation info = new RegistrationInformation("test", "testDisplay", GameType.BASE);
    Token theTok = Token.builder()
                        .type(TokenType.BEARER)
                        .accessToken("token")
                        .tokenExpiresIn(1)
                        .build();
    when(resource.postTokenPassword(anyString(),
                                    anyString(),
                                    anyString(),
                                    anyString())).thenReturn(theTok);
    TokenManager manager = before(resource, List.of(info));
    Token tok = manager.token();
    assertThat(tok).usingRecursiveComparison().isEqualTo(theTok);
  }

  @Test
  void testGetExpiredToken(@Mock LobbyServiceAuthResource resource) throws InterruptedException {
    RegistrationInformation info = new RegistrationInformation("test", "testDisplay", GameType.BASE);
    Function<InvocationOnMock, Token> run = (invocation) -> Token.builder()
                                                                 .type(TokenType.BEARER)
                                                                 .accessToken("token")
                                                                 .tokenExpiresIn(1)
                                                                 .refreshTok("")
                                                                 .build();
    Token theTok = Token.builder()
                        .type(TokenType.BEARER)
                        .accessToken("token")
                        .tokenExpiresIn(0)
                        .build();
    when(resource.postTokenPassword(anyString(),
                                    anyString(),
                                    anyString(),
                                    anyString())).thenAnswer(run::apply);
    when(resource.postTokenToken(anyString(),
                                anyString(),
                                anyString())).thenAnswer(run::apply);
    TokenManager manager = before(resource, List.of(info));
    Token tok = manager.token();
    while(!tok.isExpired()) {
      Thread.sleep(1000);
    }

    assertTrue(tok.isExpired());
    Token newTok = manager.token();
    assertThat(newTok).isNotNull();
    assertThat(newTok).isNotEqualTo(tok);
  }
}