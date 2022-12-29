package com.hexanome.splendor.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hexanome.splendor.assets.GameBoard;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
//Add @Get and @Post requests here to communicate with the server and Lobby Service
 * An interface to provide @Get and @Post requests to communicate with the server and Lobby Service.
 */

public interface BoardApi {
  @Headers("Content-Type: application/json")
  @POST("/api/sessions")
  Call<JsonElement> registerSession(@Query("access_token") String token, @Body JsonObject sessionBody);
  @GET ("/api/sessions")
  Call<JsonElement> getSessions();
  @PUT ("api/sessions/{session}/players/{player}")
  Call<JsonElement> joinSession(@Path ("session") String sessionID, @Path ("player") String username, @Query("access_token") String token);
  @POST("api/sessions/{session}")
  Call<JsonElement> launchSession(@Path ("session") String sessionID, @Query("access_token") String token);

  Call<GameBoard> loadBoard();
}
