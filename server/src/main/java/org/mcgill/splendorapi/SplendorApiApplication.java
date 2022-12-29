package org.mcgill.splendorapi;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.LobbyServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Entry point for the application.
 */
@OpenAPIDefinition(servers = @Server(url = "http://localhost:8080/api", description = "Default URL for Splendor Api"))
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({AppProperties.class, LobbyServiceProperties.class})
public class SplendorApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(SplendorApiApplication.class, args);
  }
}
