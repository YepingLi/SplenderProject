package org.mcgill.splendorapi.model.exceptions;

/**
 * Class to handle failure to load games.
 */
public class NoGameFoundException extends Exception {

  private static final String msg = "Failed to load game with id %s";

  /**
   * Build the exception.
   */
  public NoGameFoundException(String id) {
    super(String.format(msg, id));
  }
}
