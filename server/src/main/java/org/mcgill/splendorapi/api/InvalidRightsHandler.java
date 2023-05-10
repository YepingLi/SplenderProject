package org.mcgill.splendorapi.api;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.model.Unauthorized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Interface creating default functionality for the handling of exceptions.
 */
@Slf4j
public abstract class InvalidRightsHandler {
  /**
   * A method for handling unexpected FeignExceptions.
   *
   * @return Response entity matching the exception
   */
  @ExceptionHandler({FeignException.Unauthorized.class})
  ResponseEntity<Unauthorized> handleInvalidToken() {
    return new ResponseEntity<>(
      Unauthorized.builder()
                  .description("Full authentication is required to access this resource")
                  .error("Unauthorized")
                  .build(),
      HttpStatus.UNAUTHORIZED);
  }
}
