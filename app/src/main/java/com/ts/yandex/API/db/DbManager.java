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

/*        myRealm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                                     @Override
                                     public void execute(Realm realm) {
                                         // increment index
                                         Number currentIdNum = realm.where(History.class).max(Constant.id);
                                         int nextId;
                                         if(currentIdNum == null) {
                                             nextId = 1;
                                         } else {
                                             nextId = currentIdNum.intValue() + 1;
                                         }
                                         History h = new History(); // unmanaged
                                         h.setId(nextId);
                                         h.setFrom_lang(_text);
                                         h.setTo_lang(_jobject.getText().get(0));
                                         h.setFavorite(false);
                                         h.setLang(_jobject.getLang());
                                         h.setDate(new Date());
                                         realm.copyToRealm(h); // using insert API
                                     }
                                 });*/


        /*myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number id = realm.where(History.class).max(Constant.id);
                int nextId;
                if (id == null) {
                    nextId = 1;
                }
                else {
                    nextId = id.intValue() + 1;
                }
                History h = new History();
                h.setMyId(nextId);
                h.setFrom_lang(_text);
                h.setTo_lang(_jobject.getText().get(0));
                h.setFavorite(false);
                h.setLang(_jobject.getLang());
                h.setDate(new Date());
                RealmBase.save(myRealm, h);
            }
        });*/

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
        myRealm.beginTransaction();
        myRealm.copyToRealm(h);
        myRealm.commitTransaction();
        myRealm.close();

        /*History history = myRealm.where(History.class).equalTo(Constant.from_lang, _text).findFirst();
        if (history == null) {
            Number maxId = myRealm.where(History.class).max(Constant.id);
            int nextId;
            if (maxId == null) {
                nextId = 1;
            }
            else {
                nextId = maxId.intValue() + 1;
            }

            *//*Integer id = Integer.valueOf(String.valueOf(maxId));*//*
            history = new History();
            history.setMyId(nextId);
            history.setFrom_lang(_text);
            history.setTo_lang(_jobject.getText().get(0));
            history.setFavorite(false);
            history.setLang(_jobject.getLang());
            history.setDate(new Date());
            RealmBase.save(myRealm, history);
        }
        myRealm.close();*/
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

        RealmResults<History> _history = myRealm.where(History.class).equalTo(Constant.favorite, true).findAll();
        if (_history != null) {
            if (_history.size() > 0) {
                _history.sort(Constant.date, Sort.DESCENDING);

                List<History> list = new ArrayList<>();
                for (int i = 0; i < _history.size(); i++) {
                    History history = new History();
                    history.setFrom_lang(_history.get(i).getFrom_lang());
                    history.setTo_lang(_history.get(i).getTo_lang());
                    history.setFavorite(_history.get(i).getFavorite());
                    history.setLang(_history.get(i).getLang());
                    history.setDate(_history.get(i).getDate());
                    list.add(history);
                }

                myRealm.close();
                return list;
            }
        }
        myRealm.close();
        return null;
    }

    public void MarkFavorite(Context context, History _history, boolean _favorite) {
        Realm myRealm = RealmBase.getInstance(context);

//        Log.e(LOG_TAG, String.valueOf(_favorite));

//        History _history = myRealm.where(History.class).equalTo(Constant.id, _id).findFirst();
//        if (_history != null) {
        History h = new History();
        h.setId(_history.getId());
        h.setFrom_lang(_history.getFrom_lang());
        h.setTo_lang(_history.getTo_lang());
        h.setLang(_history.getLang());
        h.setDate(_history.getDate());
        h.setFavorite(_favorite);
        myRealm.beginTransaction();
        myRealm.copyToRealmOrUpdate(h);
        myRealm.commitTransaction();
//            RealmBase.save(myRealm, h);
//        }
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
