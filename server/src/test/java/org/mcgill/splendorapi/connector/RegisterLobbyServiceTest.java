package org.mcgill.splendorapi.connector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mcgill.splendorapi.model.GameType.BASE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.api.game.GameLoaderService;
import org.mcgill.splendorapi.api.game.GameManagerService;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.AssetPath;
import org.mcgill.splendorapi.config.GameServicesConfig;
import org.mcgill.splendorapi.config.OpenIdAuth2;
import org.mcgill.splendorapi.config.RegistrationInformation;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.connector.model.Scope;
import org.mcgill.splendorapi.connector.model.Token;
import org.mcgill.splendorapi.connector.model.TokenType;
import org.mcgill.splendorapi.model.GameType;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.env.MockEnvironment;

@ExtendWith(MockitoExtension.class)
class RegisterLobbyServiceTest {
  private final TokenManager manager;
  private final LobbyServiceApiService resource;
  private final AppProperties properties;
  private final InetAddress addr;
  private final GameLoaderService service;

  /**
   * Sets up the object for testing
   *
   * @param manager The mocked token manager
   * @param resource The resource
   * @throws UnknownHostException never thrown
   */
  RegisterLobbyServiceTest(@Mock TokenManager manager,
                           @Mock LobbyServiceApiService resource,
                           @Mock InetAddress addr, @Mock GameLoaderService service) throws UnknownHostException {
    this.addr = addr;
    this.service = service;
    properties = new AppProperties(
      List.of(new RegistrationInformation("test", "testDisplay", BASE)),
      Paths.get(""),
      100, 200,
      new OpenIdAuth2("name", "pass", "something"),
      200, 12, 12,
      "cards.json", "orientCards.json", "nobles.json", "tradingPosts.json",
      "cities.json",
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("", Collections.emptyList()),
      new AssetPath("",Collections.emptyList()),
      false
    );
    this.manager = manager;
    this.resource = resource;

  }

  private RegisterLobbyService getRegistrar() throws Exception {
    return new RegisterLobbyService(manager, resource,
                                    new GameServicesConfig(properties,
                                                           new MockEnvironment().withProperty("server.port", "8080")),
                                    properties,
                                    service);
  }

  @Test
  void testRegister() throws Exception {
    Token token = new Token(TokenType.BEARER, Scope.builder()
                                                   .scopes(Set.of("admin"))
                                                   .build(), "", 100, "access");
    RegisterLobbyService registrar;
    try (MockedStatic<InetAddress> mockedStatic = mockStatic(InetAddress.class)) {
      mockedStatic.when(InetAddress::getLocalHost).thenReturn(addr);
      when(addr.getHostAddress()).thenReturn("0.0.0.0");
      registrar = getRegistrar();
    } catch (Exception e) {
      throw e;
    }
    when(manager.token()).thenReturn(token);
    doAnswer((i) -> {
      return null;
    }).when(resource).putGameService(any(), any());
    Map<GameType, GameService> map = Map.of(BASE,
                                          GameService.builder()
                                                     .location("http://0.0.0.0:8080/base")
                                                     .displayName("testDisplay")
                                                     .name("test")
                                                     .minPlayers(100)
                                                     .maxPlayers(200)
                                                     .support(false)
                                                     .build());
    registrar.register();
    assertThat(registrar.getServices()).usingRecursiveComparison()
                                       .isEqualTo(map);

    assertThat(registrar.getSuccessfulRegistry()).usingRecursiveComparison()
                                                 .isEqualTo(Map.of(BASE, true));
  }

  @Test
  void testDeregister() throws Exception {
    Token token = new Token(TokenType.BEARER, Scope.builder()
                                                   .scopes(Set.of("admin"))
                                                   .build(), "", 100, "access");
    RegisterLobbyService registrar;
    try (MockedStatic<InetAddress> mockedStatic = mockStatic(InetAddress.class)) {
      mockedStatic.when(InetAddress::getLocalHost).thenReturn(addr);
      when(addr.getHostAddress()).thenReturn("0.0.0.0");
      registrar = getRegistrar();
    } catch (Exception e) {
      throw e;
    }
    when(manager.token()).thenReturn(token);
    doAnswer((i) -> {
      return null;
    }).when(resource).deleteGameService(any(), any());

    registrar.getSuccessfulRegistry().put(BASE, true);
    registrar.deregister();

    assertThat(registrar.getSuccessfulRegistry()).usingRecursiveComparison()
                                                 .isEqualTo(Map.of(BASE, false));
  }

  @Test
  void testRegister_Fail() throws Exception {
    Token token = new Token(TokenType.BEARER, Scope.builder()
                                                   .scopes(Set.of("admin"))
                                                   .build(), "", 100, "access");
    RegisterLobbyService registrar;
    try (MockedStatic<InetAddress> mockedStatic = mockStatic(InetAddress.class)) {
      mockedStatic.when(InetAddress::getLocalHost).thenReturn(addr);
      when(addr.getHostAddress()).thenReturn("0.0.0.0");
      registrar = getRegistrar();
    }
    when(manager.token()).thenReturn(token);
    doAnswer((i) -> {
      throw new Exception("Failing");
    }).when(resource).putGameService(any(), any());
    assertThrows(RuntimeException.class, registrar::register).getMessage().equals("fails");
  }

  @Test
  void testDeregister_Fail() throws Exception {
    Token token = new Token(TokenType.BEARER, Scope.builder()
                                                   .scopes(Set.of("admin"))
                                                   .build(), "", 100, "access");
    RegisterLobbyService registrar;
    try (MockedStatic<InetAddress> mockedStatic = mockStatic(InetAddress.class)) {
      mockedStatic.when(InetAddress::getLocalHost).thenReturn(addr);
      when(addr.getHostAddress()).thenReturn("0.0.0.0");
      registrar = getRegistrar();
    }
    when(manager.token()).thenReturn(token);
    doAnswer((i) -> {
      throw new Exception("Failing");
    }).when(resource).deleteGameService(any(), any());
    registrar.getSuccessfulRegistry().put(BASE, true);
    registrar.deregister();

    assertThat(registrar.getSuccessfulRegistry()).usingRecursiveComparison()
                                                 .isEqualTo(Map.of(BASE, true));
  }

  @Test
  void testDeregister_noDeregister() throws Exception {
    Token token = new Token(TokenType.BEARER, Scope.builder()
                                                   .scopes(Set.of("admin"))
                                                   .build(), "", 100, "access");
    RegisterLobbyService registrar;
    try (MockedStatic<InetAddress> mockedStatic = mockStatic(InetAddress.class)) {
      mockedStatic.when(InetAddress::getLocalHost).thenReturn(addr);
      when(addr.getHostAddress()).thenReturn("0.0.0.0");
      registrar = getRegistrar();
      registrar.getSuccessfulRegistry().put(BASE, false);
    }
    when(manager.token()).thenReturn(token);
    doAnswer((i) -> {
      throw new Exception("Failing");
    }).when(resource).putGameService(any(), any());
    registrar.deregister();
    assertThat(registrar.getSuccessfulRegistry()).usingRecursiveComparison()
                                                 .isEqualTo(Map.of(BASE, false));
  }
}