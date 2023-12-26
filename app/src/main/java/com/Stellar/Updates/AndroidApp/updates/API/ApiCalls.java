package com.Stellar.Updates.AndroidApp.updates.API;



import com.Stellar.Updates.AndroidApp.updates.models.API_Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCalls {
    @GET("api/v1/updatescontroller/updates")
    Call<API_Response> callForUpdates();

    }

