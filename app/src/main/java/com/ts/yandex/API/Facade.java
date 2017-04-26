package com.ts.yandex.API;

import com.google.gson.JsonObject;
import com.ts.yandex.API.db.DbManager;
import com.ts.yandex.API.network.HttpManager;
import com.ts.yandex.model.History;
import com.ts.yandex.model.TranslateResult;
import com.ts.yandex.myApplication;

import java.util.Dictionary;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by root on 22.04.2017.
 */

public class Facade extends Observable implements Observer {

    private static Facade instance;
    private static HttpManager httpManager;
    private static DbManager dbManager;

    public static synchronized Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
            httpManager = new HttpManager();
            dbManager = new DbManager();
            dbManager.addObserver(instance);
        }
        return instance;
    }

    public void TranslateText(String _text) {
        httpManager.TranslateText(_text);
    }

    public void SaveHistory(String _text, TranslateResult _jobject) {
        dbManager.SaveHistory(myApplication.getInstance(), _text, _jobject);
    }

    public List<History> GetHistory() {
        return dbManager.GetHistory(myApplication.getInstance());
    }

    public List<History> GetFavorite() {
        return dbManager.GetFaforite(myApplication.getInstance());
    }

    public void RemoveHistory() {
        dbManager.DeleteHistory(myApplication.getInstance());
    }

    public void MarkFavorite(History _history, boolean _mark) {
        dbManager.MarkFavorite(myApplication.getInstance(), _history, _mark);
    }

    @Override
    public void update(Observable observable, Object obj) {
        setChanged();
        notifyObservers(obj);
    }
}
