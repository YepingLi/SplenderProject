package org.mcgill.splendorapi.model;

import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Asset Object.
 *
 * @param <T> The type of the assets id
 */
@RequiredArgsConstructor
public class Asset<T> {
  private final T id;
  private final String name;

  /**
   * Convert the asset to a view.
   *
   * @return c
   */
  public Map.Entry<String, String> asView() {
    return Map.entry(name, id.toString());
  }
}
