package com.ts.yandex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 04.05.2017.
 */

public class Langs {

    @SerializedName("dirs")
    @Expose
    private List<String> dirs = null;
    @SerializedName("langs")
    @Expose
    private Map<String,String> langs = null;

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    public Map<String,String> getLangs() {
        return langs;
    }

    public void setLangs(Map<String,String> langs) {
        this.langs = langs;
    }

}