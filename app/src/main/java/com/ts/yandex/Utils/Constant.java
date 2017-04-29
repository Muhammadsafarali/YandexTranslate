package com.ts.yandex.Utils;

/**
 * Created by root on 24.04.2017.
 */

public class Constant {

    public final static String ENDPOINT = "https://translate.yandex.net/api/v1.5/";
    public final static String KEY = "trnsl.1.1.20170422T031034Z.0f74693652d2ed61.5efed30b7707142db238994f859c9db42f46de09";
    public final static String BASE_NAME = "History.realm";
    public final static int BASE_VERSION = 2;


    //  ==========  History field  ==========
    public final static String id = "id";
    public final static String lang = "lang";             // ru-en (с русского на англ)
    public final static String from_lang = "from_lang";   // текст на исходном языке
    public final static String to_lang = "to_lang";       // текст на целевом языке
    public final static String favorite = "favorite";     // true - в избранном
    public final static String deleted = "deleted";
    public final static String date = "date";             // время запроса


}
