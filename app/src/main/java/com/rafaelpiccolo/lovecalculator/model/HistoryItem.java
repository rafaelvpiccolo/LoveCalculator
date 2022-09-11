package com.rafaelpiccolo.lovecalculator.model;

public class HistoryItem {

    private int id;
    private String loverOne;
    private String loverTwo;
    private String percentage;

    public HistoryItem(int id, String loverOne, String loverTwo, String percentage) {
        this.id = id;
        this.loverOne = loverOne;
        this.loverTwo = loverTwo;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoverOne() {
        return loverOne;
    }

    public void setLoverOne(String loverOne) {
        this.loverOne = loverOne;
    }

    public String getLoverTwo() {
        return loverTwo;
    }

    public void setLoverTwo(String loverTwo) {
        this.loverTwo = loverTwo;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
