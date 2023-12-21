package com.Stellar.Updates.AndroidApp.stellarupdates;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.Stellar.Updates.AndroidApp.stellarupdates.services.StellerBackgroundService;

public class MainApplication extends Application {
    private static Context context = null;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Intent serviceIntent = new Intent(this, StellerBackgroundService.class);
        startService(serviceIntent);

    }

}

