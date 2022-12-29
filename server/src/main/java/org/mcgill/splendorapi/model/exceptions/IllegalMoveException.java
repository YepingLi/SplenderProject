package org.mcgill.splendorapi.model.exceptions;

/**
 * Illegal move exception.
 */
public class IllegalMoveException extends Exception {
  private static final String baseMessage = "The provided move is illegal!";

  /**
   * Illegal move exception constructor.
   */
  public IllegalMoveException() {
    super(baseMessage);
  }

  /**
   * Illegal move with custom message.
   *
   * @param msg The message
   */
  public IllegalMoveException(String msg) {
    super(msg);
  }
}
