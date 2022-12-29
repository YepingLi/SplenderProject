package org.mcgill.splendorapi.connector.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

public class TestToken {
  @Test
  public void testToken() {
    HashSet<String> set = new HashSet<>();
    set.add("read");
    Scope scope = Scope.builder().scopes(set).build();
    Token token = new Token(TokenType.BEARER, scope,
        "refresh token", 600, "access");
    assertThat(token.getToken()).isEqualTo("bearer access");
    assertThat(token.getRefreshToken()).isEqualTo("refresh token");
    assertThat(token.getExpiresIn()).isEqualTo(600);
    assertThat(token.getScope()).isEqualTo(scope);
  }
}
