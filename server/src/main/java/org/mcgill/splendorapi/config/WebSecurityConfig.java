package org.mcgill.splendorapi.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Allow security. Otherwise getting 403 error on put, post, etc....
 * SOURCE: https://stackoverflow.com/questions/50486314/how-to-solve-403-error-in-spring-boot-post-request
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Allow all reqeusts.
   *
   * @param http The http request security
   * @throws Exception unknown
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
  }

  /**
   * Allows all requests.
   *
   * @return Cors config
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}