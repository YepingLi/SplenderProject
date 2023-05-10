package org.mcgill.splendorapi.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Class which stores the assetPath.
 */
public class AssetPath {
  /**
   * The default object.
   */
  @RequiredArgsConstructor
  public static class Default {
    private final String name;
    private final String value;
  }

  @Getter
  private final String path;
  private final List<Default> defaults;
  private final Set<String> replaceable;

  /**
   * Initializes the asset path.
   *
   * @param path path
   * @param defaults defaults
   */
  public AssetPath(@NotNull String path, List<Default> defaults) {
    this.path = path;
    // assert it exists
    if (defaults == null) {
      defaults = List.of();
    }
    this.defaults = defaults;
    char[] stream = path.toCharArray();
    List<Integer> ints = new ArrayList<>();
    for (int i = 0; i < stream.length; i++) {
      if (stream[i] == '$') {
        ints.add(i);
      }
    }

    if (ints.size() % 2 != 0) {
      throw new IllegalStateException("Invalid state for matching!");
    }

    replaceable = IntStream.range(0, ints.size() / 2)
                           .mapToObj(i -> {
                             int next = i * 2;
                             return path.substring(ints.get(next) + 1,
                                                   ints.get(next + 1));
                           })
                           .collect(Collectors.toSet());
  }

  /**
   * Replace using the values provided otherwise use the defaults (if any).
   *
   * @param toReplace The values to replace
   * @return The string path to the asset
   */
  public String replace(Map<String, String> toReplace) throws IllegalArgumentException {
    Set<String> keys = new HashSet<>(toReplace.keySet());
    Set<String> defaulted = defaults.stream()
                                    .map(def -> def.name.toUpperCase())
                                    .collect(Collectors.toSet());
    Set<String> replaceables = replaceable.stream()
                                          .filter(name -> !defaulted.contains(name.toUpperCase()))
                                          .map(String::toLowerCase)
                                          .collect(Collectors.toSet());
    if (keys.size() != 0 && !keys.removeAll(replaceables)) {
      throw new IllegalArgumentException(
        "The items to replace contains keys not available for replacement!");
    }
    String returnable = toReplace.keySet()
                                 .stream()
                                 .reduce(path,
                                         (current, key) -> current.replace(
                                           String.format("$%s$", key.toUpperCase()),
                                           toReplace.get(key))
                                 );
    if (defaults.size() > 0) {
      for (Default def : defaults) {
        String name = String.format("$%s$", def.name.toUpperCase());
        if (returnable.contains(name)) {
          returnable = returnable.replace(name, def.value);
        }
      }
    }
    return returnable;
  }
}
