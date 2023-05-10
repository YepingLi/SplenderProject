package org.mcgill.splendorapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.function.Supplier;

/**
 * c.
 */
public interface AbstractBonus {
  static Supplier<IllegalArgumentException> produceError(String name) {
    return () -> new IllegalArgumentException(String.format("Cannot find key with name %s", name));
  }
}
