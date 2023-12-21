package com.Stellar.Updates.AndroidApp.stellarupdates.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.Stellar.Updates.AndroidApp.stellarupdates.viewmodels.MainViewModel;

public class StellerBackgroundService extends Service {
private MainViewModel mainViewModel;
    @Override
    public void onCreate() {
        super.onCreate();
        mainViewModel   = new ViewModelProvider((ViewModelStoreOwner) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(MainViewModel.class);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mainViewModel.callUpdates();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // If your service is not intended to be bound, return null
        return null;
    }

}
