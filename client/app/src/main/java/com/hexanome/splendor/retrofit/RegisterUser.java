package com.hexanome.splendor.retrofit;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUser {
    String username;
    String token;
    JsonObject userJson;

    public RegisterUser(String username, String token, JsonObject userJson) {
        this.username = username;
        this.token = token;
        this.userJson = userJson;
    }
    public void AddUser(){
        RetrofitService retrofitService = new RetrofitService();
        PlayersApi playersApi = retrofitService.getRetrofit().create(PlayersApi.class);
        Log.d("User:", this.userJson.toString());
        Call<Object> call = playersApi.putPlayer( this.username, this.token.replace("\"", ""), this.userJson);
        Log.d("Token inside Register", token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("Registration", "Success");
                Log.d("Registration", String.valueOf(response));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("Registration", "Fail!");
                Log.d("Error", t.toString());
            }
        });
    }
}
