package com.example.tatmon.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static RetrofitClient ourInstance;
    private Retrofit retrofit;
    private String BASE_URL;

    public static synchronized RetrofitClient getInstance() {
        if (ourInstance == null) {
            ourInstance = new RetrofitClient();
            System.out.println("New instance initialized");
        }
        else {
            System.out.println("Initialized before");
        }

        return ourInstance;
    }

    private RetrofitClient() {
        this.BASE_URL = "https://smarttracks.org/test/tat/API/public/";
        System.out.println("Base_url: " + BASE_URL);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.BASE_URL)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public API getApi() {
        System.out.println("Calling api!");
        return retrofit.create(API.class);
    }
}
