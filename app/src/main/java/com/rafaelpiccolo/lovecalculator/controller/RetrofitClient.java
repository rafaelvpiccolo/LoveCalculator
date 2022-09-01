package com.rafaelpiccolo.lovecalculator.controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private LoveCalcAPI api;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoveCalcAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(LoveCalcAPI.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public LoveCalcAPI getApi() {
        return api;
    }

}
