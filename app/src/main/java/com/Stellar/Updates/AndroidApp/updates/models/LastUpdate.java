package com.Stellar.Updates.AndroidApp.updates.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastUpdate {
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
