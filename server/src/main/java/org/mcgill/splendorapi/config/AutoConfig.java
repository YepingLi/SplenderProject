package org.mcgill.splendorapi.config;

import org.mcgill.splendorapi.util.TimeToLiveCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Autoconfig bean for spring boot.
 */
@Configuration
public class AutoConfig {

  @Bean
  public TimeToLiveCache createCache(AppProperties properties) {
    return new TimeToLiveCache(properties.getCacheTimeout());
  }
}
