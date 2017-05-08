package com.ts.yandex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by root on 04.05.2017.
 */

@RealmClass
public class Langs extends RealmObject {

    @PrimaryKey
    private int id;
    private RealmList<RealmString> dirs = null;
    private RealmList<RealmMap> langs = null;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public RealmList<RealmString> getDirs() {
        return dirs;
    }
    public void setDirs(RealmList<RealmString> value) {
        this.dirs = value;
    }

    public RealmList<RealmMap> getLangs() {
        return langs;
    }
    public void setLangs(RealmList<RealmMap> value) {
        this.langs = value;
    }
}