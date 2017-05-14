package com.ts.yandex.Utils;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.ts.yandex.API.network.Routes;
import com.ts.yandex.model.Langs;
import com.ts.yandex.model.RealmMap;
import com.ts.yandex.model.RealmString;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.realm.RealmList;

/**
 * Created by root on 06.05.2017.
 */

public class JsonConverter implements JsonDeserializer<Object> {

    @Override
    public Langs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Langs result = new Langs();

        if (json != null) {

            JsonArray dirsArray = json.getAsJsonObject().getAsJsonArray("dirs");
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> dirs = Routes.Factory.getGson().fromJson(dirsArray, listType);

            JsonElement obj = json.getAsJsonObject().get("langs");
            Type type = new TypeToken<Map<String,String>>(){}.getType();
            Map<String,String> langs = Routes.Factory.getGson().fromJson(obj,type);

            RealmList<RealmString> d = new RealmList<>();
            for (int i = 0; i < dirs.size(); i++)
                d.add(new RealmString(dirs.get(i)));

            RealmList<RealmMap> m = new RealmList<>();
            Set<String> keys = langs.keySet();
            for (String k : keys)
                m.add(new RealmMap(k, langs.get(k)));

            result.setDirs(d);
            result.setLangs(m);
            return result;
        }
        return null;
    }

}
