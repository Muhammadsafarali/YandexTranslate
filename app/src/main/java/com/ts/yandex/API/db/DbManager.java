package com.ts.yandex.API.db;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.ts.yandex.Utils.Constant;
import com.ts.yandex.model.History;
import com.ts.yandex.model.TranslateResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by root on 22.04.2017.
 */

public class DbManager extends Observable {

    private static final String LOG_TAG = "DbManager";

    // Добавление в историю. Работает
    public void SaveHistory(Context context, final String _text, final TranslateResult _jobject) {
        final Realm myRealm = RealmBase.getInstance(context);

        Long id = (Long)myRealm.where(History.class).max(Constant.id);
        Integer nextId;
        if (id == null) {
            nextId = 1;
        }
        else {
            nextId = id.intValue() + 1;
        }
        History h = new History();
        h.setId(nextId);
        h.setFrom_lang(_text);
        h.setTo_lang(_jobject.getText().get(0));
        h.setFavorite(false);
        h.setLang(_jobject.getLang());
        h.setDate(new Date());
        RealmBase.save(myRealm, h);
        myRealm.close();

        setChanged();
        notifyObservers(_jobject.getText().get(0));
    }

    // Получить список истории. Работает
    public List<History> GetHistory(Context context) {
        Realm myRealm = RealmBase.getInstance(context);

        RealmResults<History> results = myRealm.where(History.class).findAll();
        if (results != null) {
            if (results.size() > 0) {
                results.sort(Constant.date, Sort.DESCENDING);

                List<History> list = new ArrayList<>();
                for (int i = 0; i < results.size(); i++) {
                    History history = new History();
                    history.setId(results.get(i).getId());
                    history.setFrom_lang(results.get(i).getFrom_lang());
                    history.setTo_lang(results.get(i).getTo_lang());
                    history.setFavorite(results.get(i).getFavorite());
                    history.setLang(results.get(i).getLang());
                    history.setDate(results.get(i).getDate());
                    list.add(history);
                }

                myRealm.close();
                return list;
            }
        }
        myRealm.close();
        return new ArrayList<>();
    }

    public List<History> GetFaforite(Context context) {
        Realm myRealm = RealmBase.getInstance(context);

        RealmResults<History> results = myRealm.where(History.class).equalTo(Constant.favorite, true).findAll();
        if (results != null) {
            if (results.size() > 0) {
                results.sort(Constant.date, Sort.DESCENDING);

                List<History> list = new ArrayList<>();
                for (int i = 0; i < results.size(); i++) {
                    History h = new History();
                    h.setId(results.get(i).getId());
                    h.setFrom_lang(results.get(i).getFrom_lang());
                    h.setTo_lang(results.get(i).getTo_lang());
                    h.setFavorite(results.get(i).getFavorite());
                    h.setLang(results.get(i).getLang());
                    h.setDate(results.get(i).getDate());
                    list.add(h);
                }

                myRealm.close();
                return list;
            }
        }
        myRealm.close();
        return null;
    }

    // Добавление в избранное. Работает
    public void MarkFavorite(Context context, History _history, boolean _favorite) {
        Realm myRealm = RealmBase.getInstance(context);

        History h = new History();
        h.setId(_history.getId());
        h.setFrom_lang(_history.getFrom_lang());
        h.setTo_lang(_history.getTo_lang());
        h.setLang(_history.getLang());
        h.setDate(_history.getDate());
        h.setFavorite(_favorite);
        RealmBase.save(myRealm, h);

        myRealm.close();
    }

    // Удаление списка истории. Работает
    public void DeleteHistory(Context context) {
        Realm myRealm = RealmBase.getInstance(context);

        RealmResults<History> history = myRealm.where(History.class).findAll();
        if (history != null) {
            myRealm.beginTransaction();
            history.clear();
            myRealm.commitTransaction();
        }
        myRealm.close();
    }

}
