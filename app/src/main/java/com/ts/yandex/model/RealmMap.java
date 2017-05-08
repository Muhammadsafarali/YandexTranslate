package com.ts.yandex.model;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by root on 07.05.2017.
 */

@RealmClass
public class RealmMap extends RealmObject {

    private String key;
    private String value;
    private Boolean favorite;

    public RealmMap() {}
    public RealmMap(String _key, String _value) {
        this.key = _key;
        this.value = _value;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getFavorite() {
        return favorite;
    }
    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
