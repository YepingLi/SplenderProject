package org.mcgill.splendorapi.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.connector.model.GameService;
import org.mcgill.splendorapi.model.GameType;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mock.env.MockEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mockStatic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestGameServiceConfig {
  AppProperties properties;
  Environment env = new MockEnvironment().withProperty("server.port", "8080");
  InetAddress addr;

  public TestGameServiceConfig(@Mock InetAddress addr) {
    this.addr = addr;
    properties = new AppProperties(
      List.of(new RegistrationInformation("name",
                                          "display",
                                          GameType.BASE)),
      Paths.get(""),
      1,
      2,
      new OpenIdAuth2("", "", ""),
      200,
      200,
      12,
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

  @Test
  public void testGameServiceConfig() throws UnknownHostException {
    try(MockedStatic<InetAddress> mockedStatic = mockStatic(InetAddress.class)){
      mockedStatic.when(InetAddress::getLocalHost).thenReturn(addr);
      when(addr.getHostAddress()).thenReturn("0.0.0.0");
      GameService expected = new GameService("name", "display", "http://0.0.0.0:8080/base", 2, 1, false);
      GameServicesConfig config =  new GameServicesConfig(properties, env);
      Map<GameType, GameService> service = config.getServices();
      assertThat(service).usingRecursiveComparison().isEqualTo(Map.of(GameType.BASE, expected));
      assertThat(config.getBaseHost());
    } catch (Exception e) {
        fail("");
    }
  }
}
