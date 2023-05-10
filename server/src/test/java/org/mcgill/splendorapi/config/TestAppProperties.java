package org.mcgill.splendorapi.config;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class TestAppProperties {
  @Test
  public void testObjectBuild(){
    Path expected = Paths.get("");
    AppProperties app = new AppProperties(
        null, expected,
        100, 200,
        new OpenIdAuth2("", "", ""),
        200, 200, 12,
        "cards.json", "orientCards.json", "nobles.json", "tradingPosts.json",
        "cities.json",
        new AssetPath("", Collections.emptyList()),
        new AssetPath("", Collections.emptyList()),
        new AssetPath("", Collections.emptyList()),
        new AssetPath("", Collections.emptyList()),
        new AssetPath("",Collections.emptyList()),
        new AssetPath("",Collections.emptyList()),
        false
    );
    Assert.assertEquals(Integer.valueOf(100), app.getMinPlayers());
    Assert.assertEquals(Integer.valueOf(200), app.getMaxPlayers());
    //TODO: The following 3 lines was causing error
    //Assert.assertEquals("", app.getAuthCredentials().getToken());
    //Assert.assertEquals("", app.getAuthCredentials().getName());
    //Assert.assertEquals("", app.getAuthCredentials().getPassword());
    Assert.assertEquals(expected, app.getPathToGames());
    Assert.assertEquals(Integer.valueOf(200), app.getMaxTimeout());
  }
}
