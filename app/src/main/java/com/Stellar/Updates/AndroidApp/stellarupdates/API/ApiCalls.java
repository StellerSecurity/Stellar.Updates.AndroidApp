package com.Stellar.Updates.AndroidApp.stellarupdates.API;



import com.Stellar.Updates.AndroidApp.stellarupdates.models.API_Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCalls {
    @GET("api/v1/updatescontroller/updates")
    Call<API_Response> callForUpdates();

     /*@POST("api/push")
    Call<String> sendPush(@Body Push Model);*/

    /*@GET
    Call<HadithModel> getChapters(@Url String url);

    @GET("quran/verses/indopak")
    Call<QuranJuz> getQuranJuz(@Query("juz_number") int juzNo);

    @GET("juzs")
    Call<All_Juz> getQuranAllJuz();

    @GET("quran/translations/{tranId}")
    Call<VerseTranslation> getVerseTranslation(@Path("tranId") int transID, @Query("verse_key") String verseKey);
*/

}

