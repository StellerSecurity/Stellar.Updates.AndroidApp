package com.Stellar.Updates.AndroidApp.stellarupdates.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Stellar.Updates.AndroidApp.stellarupdates.API.ApiCalls;
import com.Stellar.Updates.AndroidApp.stellarupdates.API.RetrofitClient;
import com.Stellar.Updates.AndroidApp.stellarupdates.models.API_Response;
import com.Stellar.Updates.AndroidApp.stellarupdates.models.Item;
import com.Stellar.Updates.AndroidApp.stellarupdates.models.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Notification> mutableHaveNotif = new MutableLiveData<>();
    private MutableLiveData<List<Item>> mutableListItems = new MutableLiveData<>();

    public MainViewModel() {

    }

    public MutableLiveData<List<Item>> getMutableListItems() {
        return mutableListItems;
    }

    public MutableLiveData<Notification> getMutableHaveNotif() {
        return mutableHaveNotif;
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
                            /*List<Item> listUpdatedItems=new ArrayList<>();
                            if (listSize > 0) {
                                for (Item model : itemList) {
                                    if (mResponse.getNotification() != null) {
                                        model.setHasNotification(true);
                                    }
                                    listUpdatedItems.add(model);
                                }
                            }
                            mutableListItems.postValue(listUpdatedItems);*/


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
