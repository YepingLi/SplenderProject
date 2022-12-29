package org.mcgill.splendorapi.connector.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeleteRequest {
  @Test
  public void testBuild(){
    DeleteRequest req = DeleteRequest.builder().id("12").build();
    assertEquals("12", req.getId());
  }
}
