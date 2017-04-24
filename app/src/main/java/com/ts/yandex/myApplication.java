package com.ts.yandex;

import android.app.Application;
import android.content.Context;

import com.ts.yandex.API.network.Routes;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by root on 24.04.2017.
 */

public class myApplication extends Application {

    private static myApplication instance;
    private Routes routes;
    private Scheduler defaultSubscribeScheduler;

    public static myApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static myApplication get(Context context) {
        return (myApplication)context.getApplicationContext();
    }

    public Scheduler getDefaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    public Routes getRoutes() {
        if (routes == null) {
            this.routes = Routes.Factory.create();
        }
        return routes;
    }

}
