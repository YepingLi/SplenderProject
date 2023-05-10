package org.mcgill.splendorapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import java.net.InetAddress;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.LobbyServiceProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Entry point for the application.
 */
@Slf4j
@OpenAPIDefinition(
      servers = @Server(url = "http://localhost:8080/",
    description = "Default URL for Splendor Api")
)
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({AppProperties.class, LobbyServiceProperties.class})
public class SplendorApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(SplendorApiApplication.class, args);
  }

  /**
   * Just an entry message.
   *
   * @param ctx App context
   * @return Nothing
   */
  @Bean
  CommandLineRunner cliRunner(ApplicationContext ctx) {
    return ars -> {
      String[] strings = new String[]{
        "\n\n\n",
        "**************************************************************************"
          + "********************",
        "Application has successfully started and serving the following endpoints for "
          + "your convenience:",
        String.format("Swagger: http://%s:%s/swagger-ui/index.html",
                      InetAddress.getLocalHost().getHostAddress(),
                      ctx.getEnvironment().getProperty("server.port")),
        "-------------------------------         HAPPY HACKING  ;)      ------------"
          + "-------------------",
        "****************************************************************************"
          + "******************",
        "\n\n\n"
      };
      log.info(String.join("\n", strings));
    };
  }
}
