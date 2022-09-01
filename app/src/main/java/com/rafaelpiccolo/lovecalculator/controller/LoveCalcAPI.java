package com.rafaelpiccolo.lovecalculator.controller;

import com.rafaelpiccolo.lovecalculator.model.LoveResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface LoveCalcAPI {
    String BASE_URL = "https://love-calculator.p.rapidapi.com/";

    @Headers({
            "X-RapidAPI-Key: c32a861dd2mshe6e77d0419c3a74p1ec76djsn1702839866ca",
            "X-RapidAPI-Host: love-calculator.p.rapidapi.com"
    })
    @GET("getPercentage")
    Call<LoveResult> getResult(@Query("fname") String fname, @Query("sname") String sname);
}
