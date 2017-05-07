package com.ts.yandex.API.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ts.yandex.Utils.Constant;
import com.ts.yandex.Utils.JsonConverter;
import com.ts.yandex.model.Langs;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by root on 22.04.2017.
 */

public interface Routes {

    @POST("tr.json/getLangs")
    Observable<JsonObject> getLangs(@Query("key") String key,
                                    @Query("ui") String ui);

    @POST("tr.json/detect")
    Observable<JsonObject> detectLang();

    @FormUrlEncoded
    @POST("tr.json/translate")
    Observable<JsonObject> translateText(@Field("text") String text,
                                         @Field("key") String key,
                                         @Field("lang") String lang,
                                         @Field("format") String format,
                                         @Field("options") int options,
                                         @Field("callback") String callback);

    class Factory {

        public static Gson getGson() { return GSON; }

        private static final Gson GSON = new GsonBuilder()
                .registerTypeAdapter(Langs.class, new JsonConverter())
                .create();

        public static Routes create() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(GSON))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(Routes.class);
        }

    }

}
