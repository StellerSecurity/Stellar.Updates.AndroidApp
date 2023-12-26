package com.Stellar.Updates.AndroidApp.updates.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Stellar.Updates.AndroidApp.updates.API.APIRepository;
import com.Stellar.Updates.AndroidApp.updates.API.ApiCalls;
import com.Stellar.Updates.AndroidApp.updates.API.RetrofitClient;
import com.Stellar.Updates.AndroidApp.updates.MainApplication;
import com.Stellar.Updates.AndroidApp.updates.models.API_Response;
import com.Stellar.Updates.AndroidApp.updates.models.Item;
import com.Stellar.Updates.AndroidApp.updates.models.Notification;
import com.Stellar.Updates.AndroidApp.updates.services.StellerBackgroundService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private APIRepository repository;
    private MutableLiveData<Notification> mutableHaveNotif = new MutableLiveData<>();
    private MutableLiveData<List<Item>> mutableListItems = new MutableLiveData<>();

    public MainViewModel(APIRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Item>> getMutableListItems() {
        return mutableListItems;
    }

    public MutableLiveData<Notification> getMutableHaveNotif() {
        return mutableHaveNotif;
    }

    public void readUpdates() {
        repository.fetchUpdatesFromApi().observeForever(api_response -> {
            if (api_response != null) {
                API_Response mResponse = api_response;

                List<Item> itemList = mResponse.getItems();
                int listSize = itemList.size();
                Log.d("CallAPI", "onResponse: listSize " + listSize);

                mutableListItems.postValue(itemList);
                Log.d("CallAPI", "onResponse: checking have notification");
                if (mResponse.getNotification() != null) {
                    boolean haveNotif = mResponse.getNotification().getSend();
                    Log.d("CallAPI", "onResponse: " + haveNotif + " checking have notification");

                    if (haveNotif) {
                        //mutableHaveNotif.postValue(mResponse.getNotification());
                        StellerBackgroundService.showNotification(MainApplication.getAppContext(),mResponse.getNotification().getTitle(),mResponse.getNotification().getMessage());
                    } else {
                        mutableHaveNotif.postValue(null);
                    }
                }
            }

        });
    }



    public void callUpdates() {

        ApiCalls apiCalls = RetrofitClient.getApiCalls();
        apiCalls.callForUpdates().enqueue(new Callback<API_Response>() {
            @Override
            public void onResponse(Call<API_Response> call, Response<API_Response> response) {

                try {
                    if (response.isSuccessful()) {
                        if (response.code() == 200 && response.body() != null) {
                            API_Response mResponse = response.body();

                            List<Item> itemList = mResponse.getItems();
                            int listSize = itemList.size();
                            Log.d("CallAPI", "onResponse: listSize " + listSize);

                            mutableListItems.postValue(itemList);
                            Log.d("CallAPI", "onResponse: checking have notification");
                            if (mResponse.getNotification() != null) {
                                boolean haveNotif = mResponse.getNotification().getSend();
                                Log.d("CallAPI", "onResponse: " + haveNotif + " checking have notification");

                                if (haveNotif) {
                                    mutableHaveNotif.postValue(mResponse.getNotification());
                                } else {
                                    mutableHaveNotif.postValue(null);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d("CallAPI", "onResponse: " + e.getMessage());
                    mutableListItems.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<API_Response> call, Throwable t) {
                Log.e("CallAPI", "onResponse: " + t.getMessage().trim() + "");

            }
        });


    }
}
