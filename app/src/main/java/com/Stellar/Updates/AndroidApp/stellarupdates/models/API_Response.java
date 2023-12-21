package com.Stellar.Updates.AndroidApp.stellarupdates.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class API_Response {

    @SerializedName("items")
    @Expose
    private List<Item> items;
    @SerializedName("notification")
    @Expose
    private Notification notification;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

}
