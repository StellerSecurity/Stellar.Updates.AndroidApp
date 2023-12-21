package com.Stellar.Updates.AndroidApp.stellarupdates.API;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Stellar.Updates.AndroidApp.stellarupdates.models.API_Response;
import com.Stellar.Updates.AndroidApp.stellarupdates.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIRepository {

    private ApiCalls apiService;


    public APIRepository(ApiCalls apiService) {
        this.apiService = apiService;
    }


    public LiveData<API_Response> fetchUpdatesFromApi() {
        MutableLiveData<API_Response> updates = new MutableLiveData<>();

        apiService.callForUpdates().enqueue(new Callback<API_Response>() {
            @Override
            public void onResponse(Call<API_Response> call, Response<API_Response> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.code() == 200 && response.body() != null) {
                            API_Response mResponse = response.body();

                            List<Item> itemList = mResponse.getItems();
                            int listSize = itemList.size();
                            Log.d("CallAPI", "onResponse: listSize " + listSize);

                            updates.postValue(mResponse);
                        }
                    }
                } catch (Exception e) {
                    Log.d("CallAPI", "onResponse: " + e.getMessage());
                    updates.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<API_Response> call, Throwable t) {
                Log.d("CallAPI", "onResponse: " + t.getMessage());

                updates.setValue(null);
            }
        });

        return updates;
    }
}

