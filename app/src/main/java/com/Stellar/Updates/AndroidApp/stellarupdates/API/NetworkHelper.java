package com.Stellar.Updates.AndroidApp.stellarupdates.API;

import java.util.HashMap;
import java.util.Map;

class NetworkHelper {

    private static final String HEADER_AUTHORIZATION = "authorization";

    static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HEADER_AUTHORIZATION, "");
        return headers;
    }

}
