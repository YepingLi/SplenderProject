package com.hexanome.splendor.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dummy.
 */
public class RetrofitService {
  private Retrofit retrofit;

  public RetrofitService() {
    initializeRetrofit();
  }

  private void initializeRetrofit() {
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    retrofit = new Retrofit.Builder()
        .baseUrl("http://10.0.2.2:4242")
        .client(clientBuilder.build()) //TODO: Add the correct url!!
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public Retrofit getRetrofit() {
    return retrofit;
  }
}
