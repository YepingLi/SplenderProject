package com.hexanome.splendor;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.hexanome.splendor.retrofit.GetToken;
import com.hexanome.splendor.retrofit.PlayersApi;
import com.hexanome.splendor.retrofit.RetrofitService;
import com.hexanome.splendor.retrofit.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * check if the InstanceState is onCreate.
 */
public class Check extends AppCompatActivity {
  TextView textView;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_check);
    RetrofitService retrofitService = new RetrofitService();
    textView = findViewById(R.id.username);
    PlayersApi playersApi = retrofitService.getRetrofit().create(PlayersApi.class);
    GetToken getToken = new GetToken();
    getToken.tokenString();
    String token = getToken.getFinalToken();
    Log.d("toke:", token);
    playersApi.getAllPlayers(token).enqueue(new Callback<List<JsonElement>>() {
      @Override
      public void onResponse(Call<List<JsonElement>> call, Response<List<JsonElement>> response) {
        textView.setText(response.body().toString());
      }

      @Override
      public void onFailure(Call<List<JsonElement>> call, Throwable t) {

      }
    });
  }
}