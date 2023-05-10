package org.mcgill.splendorapi.api;

import feign.FeignException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mcgill.splendorapi.model.Unauthorized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvalidRightsHandlerTest {

  private InvalidRightsHandler invalidRightsHandler;

  @Before
  public void setUp() {
    invalidRightsHandler = new InvalidRightsHandler() {};
  }

  @Test
  public void handleInvalidToken_shouldReturnUnauthorizedResponse() {

    ResponseEntity<Unauthorized> responseEntity = invalidRightsHandler.handleInvalidToken();

    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    assertEquals("Full authentication is required to access this resource", responseEntity.getBody().getDescription());
    assertEquals("Unauthorized", responseEntity.getBody().getError());
  }

}
