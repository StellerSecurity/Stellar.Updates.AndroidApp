package com.Stellar.Updates.AndroidApp.updates;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    private static Context context = null;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
       /* Intent serviceIntent = new Intent(this, StellerBackgroundService.class);
        startService(serviceIntent);*/
        startBackgroundService();

    }

    private void startBackgroundService() {
    }

}

