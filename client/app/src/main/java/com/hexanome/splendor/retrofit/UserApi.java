package com.hexanome.splendor.retrofit;

import android.widget.Toast;
import com.google.gson.JsonElement;
import com.hexanome.splendor.assets.Player;
import java.util.List;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Exchange information about Users between LobbyService and Client.
 */
public interface UserApi {


  @POST("/oauth/token")
  @Headers({
          "authorization:Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc="
  })
    Call<JsonElement> getTokenRetrofit(
            @Query("grant_type") String grant_type,
            @Query("username") String username,
            @Query("password") String password
    );

  @POST("/oauth/token?grant_type=password&username=admin&password=admin")
    Call<JsonElement> getTokenRetrofitRefresh(
            @Query("grant_type") String grant_type,
            @Query("refresh_token") String refresh_token
    );
}
