package org.mcgill.splendorapi.connector;

import java.util.List;
import org.mcgill.splendorapi.connector.model.Authority;
import org.mcgill.splendorapi.connector.model.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign interface for connecting our splendor service to the lobby service.
 */
@FeignClient(url = "${lobby-service.infra.auth.url}", name = "lobbyServiceAuth")
public interface LobbyServiceAuthResource {
  @GetMapping("/role")
  List<Authority> getRole(@RequestParam("access_token") String token);

  @GetMapping("/username")
  String getUsername(@RequestParam("access_token") String token);

  @PostMapping("/token")
  Token postTokenPassword(@RequestParam("grant_type") String grantType,
                          @RequestParam("username") String username,
                          @RequestParam("password") String pass,
                          @RequestHeader("Authorization") String headers);

  @PostMapping("/token")
  Token postTokenToken(@RequestParam("grant_type") String grantType,
                       @RequestParam("refresh_token") String token,
                       @RequestHeader("Authorization") String headers);

}
