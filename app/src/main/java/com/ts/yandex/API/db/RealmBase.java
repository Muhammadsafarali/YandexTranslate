package com.ts.yandex.API.db;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ts.yandex.Utils.Constant;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by root on 24.04.2017.
 */

public class RealmBase {

    public static Realm getInstance(Context context) {

        RealmConfiguration config = new RealmConfiguration
                .Builder(context)
                .schemaVersion(Constant.BASE_VERSION)
                .name(Constant.BASE_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);
    }

    // POST
    public static void save(@NonNull Realm realm, RealmObject data) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
    }

}
