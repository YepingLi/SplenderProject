package com.hexanome.splendor.assets;

import com.hexanome.splendor.R;
import java.util.function.Consumer;

/**
 * Dummy.
 */
public class Card {
  private State state = State.FREE;
  private final int id;
  private final Bonus bonus;
  private final int prestiagePoints;
  private final String uri;
  private final boolean isTerminating;  //Whether purchasing this Card is the end of the turn

  /**
   *Dummy.
   */
  public enum State {
    FREE, PURCHASED, RESERVED;
  }

  /**
   * Dummy.
   */

  public Card(int anId, int prestiagepoints, Bonus bonus,
              String imageUri, boolean isTerminating) {
    id = anId;
    this.bonus = bonus;
    prestiagePoints = prestiagepoints;
    uri = imageUri;
    this.isTerminating = isTerminating;
  }

  public Card(int anId, int prestiagepoints,
              Bonus bonus, State state, String imageuri, boolean isterminating) {
    this(anId, prestiagepoints, bonus, imageuri, isterminating);
    this.state = state;
  }

  public boolean isTerminating() {
    return this.isTerminating;
  }

  public int getId() {
    return id;
  }

  public State getState() {
    return state;
  }

  /**
   * Dummy.
   */

  public void changeState(State state) {
    if (state.equals(State.PURCHASED) && (this.state.equals(State.RESERVED)
        || this.state.equals(State.FREE))) {
      this.state = state;
    } else if (state.equals(State.RESERVED) && !this.state.equals(State.RESERVED)
        && this.state.equals(State.FREE)) {
      this.state = state;
    }
  }

  public Bonus getBonus() {
    return bonus;
  }

  public int getPrestiagePoints() {
    return prestiagePoints;
  }

  public int getResourceImage() throws NoSuchFieldException, IllegalAccessException {
    return R.drawable.class.getField(uri).getInt(null);
  }
}
