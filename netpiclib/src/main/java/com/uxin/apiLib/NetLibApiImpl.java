package com.uxin.apiLib;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetLibApiImpl{

    public void init() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.test.com");
        OkHttpClient client = new OkHttpClient();
        builder.client(client);
        Retrofit retrofit = builder.build();
    }
}
