package com.Stellar.Updates.AndroidApp.stellarupdates;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Stellar.Updates.AndroidApp.stellarupdates.API.APIRepository;
import com.Stellar.Updates.AndroidApp.stellarupdates.API.RetrofitClient;
import com.Stellar.Updates.AndroidApp.stellarupdates.adapters.ItemsAdapter;
import com.Stellar.Updates.AndroidApp.stellarupdates.databinding.ActivityMainBinding;
import com.Stellar.Updates.AndroidApp.stellarupdates.models.Item;
import com.Stellar.Updates.AndroidApp.stellarupdates.services.Constants;
import com.Stellar.Updates.AndroidApp.stellarupdates.services.StellerBackgroundService;
import com.Stellar.Updates.AndroidApp.stellarupdates.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;
    private List<Item> listItems;
    private ItemsAdapter mAdapter;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;
    private NotificationChannel channel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progress.setVisibility(View.VISIBLE);
        APIRepository repository = new APIRepository(RetrofitClient.getApiCalls());
        mainViewModel = new MainViewModel(repository);


        listItems = new ArrayList<Item>();

        mAdapter = new ItemsAdapter(this, listItems);


        //        checkNotificationPermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(Constants.channelId, Constants.channelName, NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        mainViewModel.getMutableListItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {


                if (items != null) {
                    listItems = items;
                    Log.d("CallAPI", "onChanged:  size " + items.size());

                    mAdapter.setData(listItems);
                    binding.listUpdates.setAdapter(mAdapter);

                    binding.progress.setVisibility(View.GONE);
                    binding.tvNoResultsData.setVisibility(View.GONE);
                } else {
                    binding.tvNoResultsData.setVisibility(View.VISIBLE);
                }
                binding.refreshUpdates.setRefreshing(false);


            }
        });


        mainViewModel.readUpdates();

        binding.refreshUpdates.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                binding.progress.setVisibility(View.VISIBLE);
                mainViewModel.readUpdates();
            }
        });

        startService(new Intent(this, StellerBackgroundService.class));
    }


    private void checkNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            showPermissionDialog();
            Log.d("CallAPI", "on permission: issue in checking have notification");

        } else {
            return;
        }
    }


    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable Notifications");
        builder.setMessage("Please enable notifications to receive updates.");

        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openNotificationSettings();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void openNotificationSettings() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            checkNotificationPermission();
            mainViewModel.callUpdates();
        }
    }



}