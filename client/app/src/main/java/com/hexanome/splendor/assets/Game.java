package com.hexanome.splendor.assets;

import java.io.Serializable;

public class Game implements Serializable {
  private String location;
  private int maxSessionPlayers;
  private int minSessionPlayers;
  private String name;
  private boolean webSupport;

  public Game(String location, int maxSessionPlayers, int minSessionPlayers,String name,boolean webSupport){
    this.location = location;
    this.maxSessionPlayers=maxSessionPlayers;
    this.minSessionPlayers=minSessionPlayers;
    this.name = name;
    this.webSupport=webSupport;
  }
  public String getLocation() {
    return this.location;
  }
  public int getMaxSessionPlayers(){
    return this.maxSessionPlayers;
  }
  public int getMinSessionPlayers(){
    return this.minSessionPlayers;
  }
  public String getName(){
    return this.name;
  }
  public boolean getWebSupport(){
    return this.webSupport;
  }
}
