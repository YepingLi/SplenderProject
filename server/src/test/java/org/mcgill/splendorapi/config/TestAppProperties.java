package org.mcgill.splendorapi.config;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestAppProperties {
  @Test
  public void testObjectBuild(){
    Path expected = Paths.get("");
    AppProperties app = new AppProperties(
        "", "", expected, 100, 200, new OpenIdAuth2("", "", ""), 200, 12, ""
    );
    Assert.assertEquals("", app.getName());
    Assert.assertEquals("", app.getDisplayName());
    Assert.assertEquals(Integer.valueOf(100), app.getMinPlayers());
    Assert.assertEquals(Integer.valueOf(200), app.getMaxPlayers());
    Assert.assertEquals("", app.getAuthCredentials().getToken());
    Assert.assertEquals("", app.getAuthCredentials().getName());
    Assert.assertEquals("", app.getAuthCredentials().getPassword());
    Assert.assertEquals(expected, app.getPathToGames());
    Assert.assertEquals(Integer.valueOf(200), app.getMaxTimeout());
  }
}
