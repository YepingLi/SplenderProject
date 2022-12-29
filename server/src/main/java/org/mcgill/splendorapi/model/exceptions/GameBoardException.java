package org.mcgill.splendorapi.model.exceptions;

/**
 * Parent type for all game exceptions.
 */
public class GameBoardException extends Exception {

  /**
   * Constructor for building game exception.
   *
   * @param message The message to be sent.
   */
  public GameBoardException(String message) {
    super(message);
  }
}
