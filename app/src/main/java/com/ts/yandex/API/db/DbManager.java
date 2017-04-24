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
    public void SaveHistory(Context context, String _text, TranslateResult _jobject) {
        Realm myRealm = RealmBase.getInstance(context);

        History history = myRealm.where(History.class).equalTo(Constant.from_lang, _text).findFirst();
        if (history == null) {
            Long id = (Long) myRealm.where(History.class).max(Constant.id);
            if (id == null)
                id = Long.valueOf(0);
            else
                id++;
            history = new History();
            history.setId(id);
            history.setFrom_lang(_text);
            history.setTo_lang(_jobject.getText().get(0));
            history.setFavorite(false);
            history.setLang(_jobject.getLang());
            history.setDate(new Date());
            RealmBase.save(myRealm, history);
        }
        myRealm.close();
    }

    // Получить список истории. Работает
    public List<History> GetHistory(Context context) {
        Realm myRealm = RealmBase.getInstance(context);

        RealmResults<History> result = myRealm.where(History.class).findAll();
        if (result != null) {
            if (result.size() > 0) {
                result.sort(Constant.date, Sort.DESCENDING);

                List<History> list = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    History history = new History();
                    history.setFrom_lang(result.get(i).getFrom_lang());
                    history.setTo_lang(result.get(i).getTo_lang());
                    history.setFavorite(result.get(i).getFavorite());
                    history.setLang(result.get(i).getLang());
                    history.setDate(result.get(i).getDate());
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

        RealmResults<History> result = myRealm.where(History.class).equalTo(Constant.favorite, true).findAll();
        if (result != null) {
            if (result.size() > 0) {
                result.sort(Constant.date, Sort.DESCENDING);

                List<History> list = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    History history = new History();
                    history.setFrom_lang(result.get(i).getFrom_lang());
                    history.setTo_lang(result.get(i).getTo_lang());
                    history.setFavorite(result.get(i).getFavorite());
                    history.setLang(result.get(i).getLang());
                    history.setDate(result.get(i).getDate());
                    list.add(history);
                }

                myRealm.close();
                return list;
            }
        }
        myRealm.close();
        return null;
    }

    public void MarkFavorite(Context context, String _text, Boolean _favorite) {
        Realm myRealm = RealmBase.getInstance(context);

        History result = myRealm.where(History.class).equalTo(Constant.from_lang, _text).findFirst();
        if (result != null) {
            History h = new History();
            h.setId(result.getId());
            h.setFrom_lang(result.getFrom_lang());
            h.setTo_lang(result.getTo_lang());
            h.setLang(result.getLang());
            h.setDate(result.getDate());
            h.setFavorite(_favorite);
        }
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
