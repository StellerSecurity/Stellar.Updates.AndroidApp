package com.Stellar.Updates.AndroidApp.updates.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class API_Response implements Parcelable {

    @SerializedName("items")
    @Expose
    private List<Item> items;
    @SerializedName("notification")
    @Expose
    private Notification notification;
    @SerializedName("last_update")
    @Expose
    private LastUpdate lastUpdate;

    protected API_Response(Parcel in) {
    }

    public static final Creator<API_Response> CREATOR = new Creator<API_Response>() {
        @Override
        public API_Response createFromParcel(Parcel in) {
            return new API_Response(in);
        }

        @Override
        public API_Response[] newArray(int size) {
            return new API_Response[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }

    public LastUpdate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LastUpdate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
