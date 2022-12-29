package org.mcgill.splendorapi.model;

import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;

/**
 * Card object.
 */
@RequiredArgsConstructor
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class Card {
  /**
   * Enum of the state.
   */
  public enum State {
    FREE, PURCHASED, RESERVED
  }

  private static final long serialVersionUID = 1L;
  private final int level;
  private final int id;
  private final Bonus bonus;
  private final int prestiagePoints;
  private final String uri;
  private final Map<Gem.Type, Integer> price;
  private State state = State.FREE;
  private Boolean turned = false;

  /**
   * Turn the card over.
   *
   * @throws IllegalStateException Thrown when card is already turned
   */
  public void turnOver() throws IllegalStateException {
    if (turned) {
      throw new IllegalStateException("Cannot turn over card which is already turned over");
    }

    turned = true;
  }

  /**
   * update the newState of the card.
   *
   * @param newState newState of the object
   */
  public void changeState(State newState) throws IllegalMoveException {
    if (!turned) {
      throw new IllegalMoveException("Trying to access non-visible card.");
    }

    if (newState.equals(State.PURCHASED)
        && (state.equals(State.RESERVED) || state.equals(State.FREE))) {
      state = newState;
    } else if (newState.equals(State.RESERVED) && !state.equals(State.RESERVED)
        && state.equals(State.FREE)) {
      state = newState;
    }
  }
}
