package org.mcgill.splendorapi.model.exceptions;

import lombok.Builder;

/**
 * Object to handle timeout exception while waiting for update.
 */
public class NoChangeFoundException extends Exception {

  /**
   * Creates no update found error.
   *
   * @param id Game id
   */
  @Builder
  public NoChangeFoundException(String id) {
    super(String.format("No change was found for %s", id));
  }
}
