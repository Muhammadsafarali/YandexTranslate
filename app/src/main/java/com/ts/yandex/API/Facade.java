package com.ts.yandex.API;

import com.ts.yandex.API.db.DbManager;
import com.ts.yandex.API.network.HttpManager;

/**
 * Created by root on 22.04.2017.
 */

public class Facade {

    private static Facade instance;
    private static HttpManager httpManager;
    private static DbManager dbManager;

    public static synchronized Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
            httpManager = new HttpManager();
            dbManager = new DbManager();
        }
        return instance;
    }



}
