package org.mcgill.splendorapi.config;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class TestAssetPath {
  @Test
  void testThrowsIllegalArgumentException() {
    AssetPath path = new AssetPath("not_replaceable",
                                   List.of(new AssetPath.Default("something_not_in", "replaceable")));
    assertThatIllegalArgumentException().isThrownBy(() -> path.replace(Map.of("something", "nextValue")));
  }

  @Test
  void testAssertEquals() {
    AssetPath path = new AssetPath("not_replaceable_$REPLACE_ME$",
                                   List.of());
    assertThat(path.replace(Map.of("replace_me", "nextValue"))).isEqualTo("not_replaceable_nextValue");
  }

  @Test
  void testAssertEqualsDefault() {
    AssetPath path = new AssetPath("not_$REPLACE$_$REPLACE_ME$",
                                   List.of(new AssetPath.Default("replace", "something")));
    assertThat(path.replace(Map.of("replace_me", "nextValue"))).isEqualTo("not_something_nextValue");
    assertThat(path.getPath()).isEqualTo("not_$REPLACE$_$REPLACE_ME$");
  }

  @Test
  void testImproperString() {
    assertThatIllegalStateException().isThrownBy(() -> new AssetPath("not_$REPLACE$_REPLACE_ME$",
                                                                     List.of(new AssetPath.Default("replace", "something"))));
  }
}
