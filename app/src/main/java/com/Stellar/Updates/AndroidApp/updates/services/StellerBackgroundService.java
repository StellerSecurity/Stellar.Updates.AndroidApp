package com.Stellar.Updates.AndroidApp.updates.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.Stellar.Updates.AndroidApp.updates.API.APIRepository;
import com.Stellar.Updates.AndroidApp.updates.API.RetrofitClient;
import com.Stellar.Updates.AndroidApp.updates.R;
import com.Stellar.Updates.AndroidApp.updates.viewmodels.MainViewModel;

public class StellerBackgroundService extends Service {
    private MainViewModel mainViewModel;
    private Handler handler;
    private Runnable apiRunnable;


    @Override
    public void onCreate() {
        super.onCreate();


        APIRepository repository = new APIRepository(RetrofitClient.getApiCalls());
        mainViewModel = new MainViewModel(repository);


        handler = new Handler();
        apiRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Routine", "run: calling API 10sec");
                mainViewModel.readUpdates();
                //  handler.postDelayed(this, (7200000)); // Run every 2 hours
                handler.postDelayed(this, (10000)); // Run every 10seconds
            }
        };
        handler.postDelayed(apiRunnable, 0);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // mainViewModel.callUpdates();
        return START_STICKY;
    }


    public static void showNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title).setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Log.d("CallAPI", "on showing:   checking have notification");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
