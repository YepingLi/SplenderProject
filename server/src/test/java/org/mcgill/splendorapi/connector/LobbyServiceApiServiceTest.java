package org.mcgill.splendorapi.connector;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.connector.model.Token;
import org.mcgill.splendorapi.connector.model.TokenType;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LobbyServiceApiServiceTest {
  LobbyServiceApiResource resource;
  LobbyServiceApiService service;
  public LobbyServiceApiServiceTest(@Mock LobbyServiceApiResource resource) {
    this.resource = resource;
    service = new LobbyServiceApiService(resource);
  }

  @Test
  void testPutGameService() {
    GameService serv = new GameService("", "", "", 1, 2, false);
    Token tok = Token.builder().accessToken("").type(TokenType.BEARER).build();
    service.putGameService(serv, tok);
    verify(resource).putGameService("", serv, tok.getToken());
  }

  @Test
  void testDeleteGameService() {
    GameService serv = new GameService("serviceName", "", "", 1, 2, false);
    Token tok = Token.builder().accessToken("").type(TokenType.BEARER).build();
    service.deleteGameService(serv, tok);
    verify(resource).deleteGameService("serviceName", tok.getToken());
  }
}