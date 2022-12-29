package com.hexanome.splendor.retrofit;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.hexanome.splendor.assets.Game;
import com.hexanome.splendor.assets.Session;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Stub.
 */
public class SessionsController {
  String saveGameId;
  String sessionId;
  Session session;
  ArrayList<String> playersList;
  Game gameObj;
  String gameName;
  int maxPlayers;
  int minPlayers;
  String location;
  String Creator;
  public String username;
  public String token;
  private ArrayList<Session> sessions;

  public void setSessions(ArrayList<Session> sessions) {
    this.sessions = sessions;
  }

  public void setSaveGameId(String saveGameId) {
    this.saveGameId = saveGameId;
  }

  public String getSaveGameId() {
    return saveGameId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public Session getSession() {
    return session;
  }

  public ArrayList<String> getPlayersList() {
    return playersList;
  }

  public Game getGameObj() {
    return gameObj;
  }

  public String getGameName() {
    return gameName;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public int getMinPlayers() {
    return minPlayers;
  }

  public String getLocation() {
    return location;
  }

  public String getCreator() {
    return Creator;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public void setPlayersList(ArrayList<String> playersList) {
    this.playersList = playersList;
  }

  public void setGameObj(Game gameObj) {
    this.gameObj = gameObj;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public void setMaxPlayers(int maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

  public void setMinPlayers(int minPlayers) {
    this.minPlayers = minPlayers;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setCreator(String creator) {
    Creator = creator;
  }
  public SessionsController(String token) {
    this.token = token;
  }


  public ArrayList<Session> getSessions() {
    return this.sessions;
  }

  /**
   * Stub.
   */
  public void launchSession(String token){
    RetrofitService retrofitService = new RetrofitService();
    BoardApi boardApi = retrofitService.getRetrofit().create(BoardApi.class);
    Call<JsonElement> call =boardApi.launchSession(getSessionId(), token);
    call.enqueue(new Callback<JsonElement>(){
      @Override
      public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        Log.d("LaunchSession", "The session was launched successfully");
      }

      @Override
      public void onFailure(Call<JsonElement> call, Throwable t) {
        Log.d("LaunchSession", "The session failed to launch");
      }
    });
  }
  public String CreateSession(String token, String username){
    RetrofitService retrofitService = new RetrofitService();
    BoardApi boardApi = retrofitService.getRetrofit().create(BoardApi.class);
    GSONObjectGenerator GSONObjectGenerator = new GSONObjectGenerator(username, "");
    Log.d("My session information", String.valueOf(GSONObjectGenerator.createSessionGSON()));
    Call<JsonElement> call = boardApi.registerSession(token.replace("\"", ""), GSONObjectGenerator.createSessionGSON());
    call.enqueue(new Callback<JsonElement>() {
      @Override
      public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        Log.d("Session", "Session registered successfully");
        Log.d("session", String.valueOf(response));
        Log.d("Session", String.valueOf(response.body()));
      }

      @Override
      public void onFailure(Call<JsonElement> call, Throwable t) {
        Log.d("Problem", t.toString());
      }
    });
    return  getSessionId();
  }
  public void joinSession(String token, String username){
    RetrofitService retrofitService = new RetrofitService();
    BoardApi boardApi = retrofitService.getRetrofit().create(BoardApi.class);
    Call<JsonElement> call = boardApi.joinSession(getSessionId(), username, token);
    call.enqueue(new Callback<JsonElement>() {
      @Override
      public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        Log.d("Session", "Joined session successfully");
        Log.d("RequestBody", call.toString());
      }

      @Override
      public void onFailure(Call<JsonElement> call, Throwable t) {

      }
    });
  }
  public void GetSessions() {
    RetrofitService retrofitService = new RetrofitService();
    BoardApi boardApi = retrofitService.getRetrofit().create(BoardApi.class);
    Log.d("Session time check", "Now");
    Call<JsonElement> call = boardApi.getSessions();
    call.enqueue(new Callback<JsonElement>() {
      @Override
      public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        Log.d("Sessions", "Retrieving Sessions");
        Log.d("Call", response.toString());
        //Log.d("Call", String.valueOf(response.body().getAsJsonObject().get("sessions").getAsJsonObject().keySet().toArray()[0]));
        //TODO: Iterate over each Session in the Sessions Json.
        if (!response.body().getAsJsonObject().get("sessions").toString().equals("{}")) {
          setSessionId(String.valueOf(response.body().getAsJsonObject().get("sessions").getAsJsonObject().keySet().toArray()[0]));
          JsonElement sessionsJSon = response.body().getAsJsonObject().get("sessions").getAsJsonObject().get(getSessionId());
          setCreator(sessionsJSon.getAsJsonObject().get("creator").toString());
          Log.d("My session", sessionsJSon.getAsJsonObject().get("creator").toString());
          JsonElement game = sessionsJSon.getAsJsonObject().get("gameParameters");
          setLocation(game.getAsJsonObject().get("location").toString());
          setMaxPlayers(Integer.parseInt(game.getAsJsonObject().get("maxSessionPlayers").toString()));
          setMinPlayers(Integer.parseInt(game.getAsJsonObject().get("minSessionPlayers").toString()));
          setGameName(game.getAsJsonObject().get("name").toString());
          setGameObj(new Game(location, maxPlayers, minPlayers, gameName, true));
          String Lauched = sessionsJSon.getAsJsonObject().get("launched").toString();
          boolean isLaunched = false;
          if (Lauched.equals("true")) {
            isLaunched = true;
          }
          JsonArray players = sessionsJSon.getAsJsonObject().get("players").getAsJsonArray();
          setPlayersList(new ArrayList<>());
          for (int i = 0; i < getPlayersList().size(); i++) {
            if (players.get(i) != null) { //TODO: Confirm that null is the correct value here.
              getPlayersList().add(String.valueOf(players.get(i)));
            } else {
              break;
            }
          }
          //TODO: How to parse a json array!!!
          JsonElement playerLocations = sessionsJSon.getAsJsonObject().get("playerLocations");
          setSaveGameId(sessionsJSon.getAsJsonObject().get("savegameid").toString());
          setSession(new Session(getCreator(), getGameObj(), false, getPlayersList(), null, getSaveGameId()));
          Log.d("My session", getSession().toString());
        }
      }
      @Override
      public void onFailure(Call<JsonElement> call, Throwable t) {

      }
    });
  }
}

