package com.ts.yandex.model;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by root on 07.05.2017.
 */

@RealmClass
public class RealmString extends RealmObject {

    private String string;

    public RealmString() {}

    public RealmString(String _string) {
        this.string = _string;
    }

    public String getString() {
        return string;
    }
    public void setString(String string) {
        this.string = string;
    }
}
