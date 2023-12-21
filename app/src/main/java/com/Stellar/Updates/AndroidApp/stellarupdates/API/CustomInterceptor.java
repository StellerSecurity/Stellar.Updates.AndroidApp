package com.Stellar.Updates.AndroidApp.stellarupdates.API;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        return chain.proceed(chain.request().newBuilder().headers(Headers.of(NetworkHelper.getHeaders()))
                .build());

    }
}