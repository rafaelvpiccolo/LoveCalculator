package com.rafaelpiccolo.lovecalculator.model;

import com.google.gson.annotations.SerializedName;

public class LoveResult{

    @SerializedName("result")
    private String result;
    @SerializedName("percentage")
    private String percentage;

    public LoveResult(String result, String percentage) {
        this.result = result;
        this.percentage = percentage;
    }

    public String getResult() {
        return result;
    }

    public String getPercentage() {
        return percentage;
    }

}
