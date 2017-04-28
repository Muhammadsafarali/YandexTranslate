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
        h.setDeleted(false);
        h.setDate(new Date());
        RealmBase.save(myRealm, h);
        myRealm.close();

        setChanged();
        notifyObservers(_jobject.getText().get(0));
    }

    // Получить список истории. Работает
    public List<History> GetHistory(Context context) {
        Realm myRealm = RealmBase.getInstance(context);

        // Очистить удаленные ранее из истории и исключенные из избранного позже записи.
        RealmResults<History> results = myRealm.where(History.class).equalTo(Constant.deleted, true).equalTo(Constant.favorite, false).findAll();
        if (results != null && results.size() > 0) {
            RealmBase.deleteList(myRealm, results);
        }

        // Получить все неудаленные записи
        results = myRealm.where(History.class).equalTo(Constant.deleted, false).findAll();
        if (results != null && results.size() > 0) {
            results.sort(Constant.date, Sort.DESCENDING);

            List<History> list = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                History h = new History();
                h.setId(results.get(i).getId());
                h.setFrom_lang(results.get(i).getFrom_lang());
                h.setTo_lang(results.get(i).getTo_lang());
                h.setFavorite(results.get(i).getFavorite());
                h.setLang(results.get(i).getLang());
                h.setDeleted(results.get(i).getDeleted());
                h.setDate(results.get(i).getDate());
                list.add(h);
            }

            myRealm.close();
            return list;
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
                    h.setDeleted(results.get(i).getDeleted());
                    h.setDate(results.get(i).getDate());
                    list.add(h);
                }

                myRealm.close();
                return list;
            }
        }
        myRealm.close();
        return new ArrayList<>();
    }

    // Добавление в избранное. Работает
    public void MarkFavorite(Context context, History _history) {
        Realm myRealm = RealmBase.getInstance(context);
        RealmBase.save(myRealm, _history);
        myRealm.close();
    }

    // Удаление списка истории.
    public void DeleteHistory(Context context) {
        Realm myRealm = RealmBase.getInstance(context);

        RealmResults<History> history = myRealm.where(History.class).equalTo(Constant.favorite, false).findAll();
        if (history != null) {
            RealmBase.deleteList(myRealm, history);

            history = myRealm.where(History.class).findAll();
            if (history != null && history.size() > 0) {
                List<History> list = new ArrayList<>();
                list.addAll(history);
                myRealm.beginTransaction();
                for (History h : list) {
                    h.setDeleted(true);
                }
                myRealm.commitTransaction();
            }
        }
        myRealm.close();
    }

    public void DeleteFavorite(Context context) {
        Realm myRealm = RealmBase.getInstance(context);

        RealmResults<History> results = myRealm.where(History.class).equalTo(Constant.favorite, true).equalTo(Constant.deleted, true).findAll();
        if (results != null && results.size() > 0) {
            RealmBase.deleteList(myRealm, results);
        }
        results = myRealm.where(History.class).equalTo(Constant.favorite, true).findAll();
        if (results != null && results.size() > 0) {
            List<History> list = new ArrayList<>();
            list.addAll(results);
            myRealm.beginTransaction();
            for (History h : list) {
                h.setFavorite(false);
            }
            myRealm.commitTransaction();
        }
    }

}
