package com.ts.yandex.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by root on 24.04.2017.
 */

@RealmClass
public class History extends RealmObject {

    @PrimaryKey
    private long id;
    private String lang;        // ru-en (с русского на англ)
    private String from_lang;   // текст на исходном языке
    private String to_lang;     // текст на целевом языке
    private Boolean favorite;   // true - в избранном
    private Boolean deleted;    // true - очистил историю. Устанавливаю, если favorite is true
    private Date date;          // время запроса

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getFrom_lang() {
        return from_lang;
    }

    public void setFrom_lang(String from_lang) {
        this.from_lang = from_lang;
    }

    public String getTo_lang() {
        return to_lang;
    }

    public void setTo_lang(String to_lang) {
        this.to_lang = to_lang;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
