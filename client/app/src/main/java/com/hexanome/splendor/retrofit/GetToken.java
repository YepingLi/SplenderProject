package com.hexanome.splendor.retrofit;

import android.util.Log;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A class that helps to get token.
 */
public class GetToken {
    public String finalToken;
    public String finalRefreshToken;
    public String userToken;
    public String userRefreshToken;

    public void setUserRefreshToken(String userRefreshToken) {
        this.userRefreshToken = userRefreshToken;
    }

    public String getUserRefreshToken() {
        return userRefreshToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public GetToken() {
    }

    public void setToken(String token) {
        finalToken = token;
    }

    public void setRefreshToken(String refreshToken) {
        finalRefreshToken = refreshToken;
    }

    public String getFinalToken() {
        return finalToken;
    }

    public String getFinalRefreshToken() {
        return finalRefreshToken;
    }

    /**
     * Return the token as a String.
     *
     * @return The return object is a String.
     */
    public void userTokenString(String username, String password){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        Call<JsonElement> call = userApi.getTokenRetrofit("password", username, password);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                Log.d("TOKEN", String.valueOf(response));
                Log.d("TOKEN", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    setUserToken(response.body().getAsJsonObject().get("access_token").toString());
                    setUserRefreshToken(response.body().getAsJsonObject().get("refresh_token").toString());
                    Log.d("Now", "now");
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
            }
        });
    }
    public void tokenString() {
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        Call<JsonElement> call = userApi.getTokenRetrofit("password", "maex", "abc123_ABC123");
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                if (response.isSuccessful()) {
                    setToken(response.body().getAsJsonObject().get("access_token").toString());
                    setRefreshToken(response.body().getAsJsonObject().get("refresh_token").toString());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
            }
        });
    }
}
