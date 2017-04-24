package com.ts.yandex.API.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.ts.yandex.API.Facade;
import com.ts.yandex.Utils.Constant;
import com.ts.yandex.model.TranslateResult;
import com.ts.yandex.myApplication;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by root on 22.04.2017.
 */

public class HttpManager {

    private Subscription subscription;

    public void TranslateText(final String _text) {

        if (subscription != null)
            subscription.unsubscribe();

        myApplication context = myApplication.get(myApplication.getInstance());
        Routes routes = context.getRoutes();

        subscription = routes.translateText(_text, Constant.KEY, "en", "plain", 1, "fun")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(context.getDefaultSubscribeScheduler())
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if (jsonObject != null) {
                            Log.e("LOG_HTTP_MANAGER","success");
                            TranslateResult result = Routes.Factory.getGson().fromJson(jsonObject, TranslateResult.class);
                            Log.e("LOG_HTTP_MANAGER", result.getText().get(0));
                            Facade.getInstance().SaveHistory(_text, result);
                        }
                    }
                });
    }

}
