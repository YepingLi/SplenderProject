package org.mcgill.splendorapi.model;

import lombok.Getter;

/**
 * Defines the atomic actions which make up a move object.
 */
@Getter
public class Action<T> {
  private ActionType type;
  private T value;

  public Action(ActionType atype, T value) {
    this.type = atype;
    this.value = value;
  }
}
