package com.Stellar.Updates.AndroidApp.updates.services;

import android.Manifest;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.Stellar.Updates.AndroidApp.updates.API.APIRepository;
import com.Stellar.Updates.AndroidApp.updates.API.RetrofitClient;
import com.Stellar.Updates.AndroidApp.updates.R;
import com.Stellar.Updates.AndroidApp.updates.viewmodels.MainViewModel;

public class StellerBackgroundService extends JobService {
    private static final int JOB_ID = 123; // Unique job ID
    private static final long JOB_INTERVAL = 10000; // Job interval in milliseconds (10 seconds)

    private MainViewModel mainViewModel;
    private Handler handler;


    private Runnable apiRunnable;



    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("Routine", "onStartJob: calling API every 10 seconds");

        APIRepository repository = new APIRepository(RetrofitClient.getApiCalls());
        mainViewModel = new MainViewModel(repository);

        handler = new Handler();
        handler.postDelayed(() -> {
            mainViewModel.readUpdates();
            jobFinished(params, false); // Finish the job after execution
        }, JOB_INTERVAL);

        return true; // Indicates whether there is more work to be done
    }
    @Override
    public boolean onStopJob(JobParameters params) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        return false; // Reschedule the job if it didn't complete
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

    public static void scheduleJob(Context context) {
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("key", "value");

        androidx.core.app.JobIntentService.enqueueWork(
                context,
                StellerBackgroundService.class,
                JOB_ID,
                new Intent()
        );
    }

}
