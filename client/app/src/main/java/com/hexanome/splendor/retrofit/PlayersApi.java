package com.hexanome.splendor.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hexanome.splendor.assets.Player;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface PlayersApi {
    @PUT("/api/users/{users}")
    @Headers("Content-Type: application/json")
    Call<Object> putPlayer(@Path("users") String username, @Query(value = "access_token", encoded = true) String token, @Body JsonElement playerJson);
    @GET("/api/users")
    Call<List<JsonElement>> getAllPlayers(
            @Query("access_token") String token
    );
    @GET("/api/users/{users}")
    Call<JsonElement> getPlayer(@Path("users") String username, @Query("access_token") String accessToken);

}
