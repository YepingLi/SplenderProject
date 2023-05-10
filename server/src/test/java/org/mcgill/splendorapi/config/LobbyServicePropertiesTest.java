package org.mcgill.splendorapi.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LobbyServicePropertiesTest {

  LobbyServiceProperties.ServiceInformation
  ServiceInformationTest1 = new LobbyServiceProperties.ServiceInformation("t");
  LobbyServiceProperties.ServiceInformation
  ServiceInformationTest2 = new LobbyServiceProperties.ServiceInformation("e");
  LobbyServiceProperties LobbyServicePropertiesTest = new LobbyServiceProperties
    (ServiceInformationTest1, ServiceInformationTest2);


  @Test
  void getApi() {
    assertSame(ServiceInformationTest1,LobbyServicePropertiesTest.getApi() );
  }

  @Test
  void getAuth() {
    assertSame(ServiceInformationTest2,LobbyServicePropertiesTest.getAuth() );
  }
}