package com.hexanome.splendor.assets;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stub.
 */
public class Session implements Serializable {
  private String creator;
  private Game game;
  private boolean launched;
  private ArrayList<String> players;
  private ArrayList<String> playerLocations;
  private String saveGameId;

  /**
   * Constructor.
   */
  public Session(String creator, Game game, boolean launched,
                 ArrayList<String> players, ArrayList<String> playerLocations, String saveGameId) {
    this.creator = creator;
    this.game = game;
    this.launched = launched;
    this.players = players;
    this.playerLocations = playerLocations;
    this.saveGameId = saveGameId;
  }

  public String getCreator() {
    return this.creator;
  }

  public Game getGame() {
    return this.game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public boolean getLaunched() {
    return this.launched;
  }

  public void setLaunched(boolean launched) {
    this.launched = launched;
  }

  public ArrayList<String> getPlayers() {
    return this.players;
  }

  public void setPlayers(ArrayList<String> players) {
    this.players = players;
  }

  public ArrayList<String> getPlayerLocations() {
    return this.getPlayerLocations();
  }


  public void setPlayerLocations(ArrayList<String> playerLocations) {
    this.playerLocations = playerLocations;
  }

  public String getSaveGameId() {
    return this.saveGameId;
  }

  public void setGameId(String saveGameId) {
    this.saveGameId = saveGameId;
  }
}


