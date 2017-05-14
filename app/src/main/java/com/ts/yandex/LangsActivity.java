package com.ts.yandex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ts.yandex.API.Facade;
import com.ts.yandex.Utils.Constant;
import com.ts.yandex.model.Langs;
import com.ts.yandex.model.ResultObserver;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by root on 04.05.2017.
 */

public class LangsActivity extends Activity implements Observer {

    private static ListView listView;
    private String[] names;
    private Langs langs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lang_fragment);

        listView = (ListView) findViewById(R.id.lang_list);

        Facade.getInstance().addObserver(this);
        Facade.getInstance().getLangs();
    }

    @Override
    protected void onDestroy() {
        Facade.getInstance().deleteObserver(this);
        super.onDestroy();
    }

    @Override
    public void update(Observable observable, Object obj) {
        if (obj != null) {
            ResultObserver result = (ResultObserver)obj;
            if (result.getCode() == Constant.langs_save_complete) {
//                Log.e("LANGS_ACTIVITY", "Update");
                langs = Facade.getInstance().getLocalLangs();

                names = new String[langs.getLangs().size()];
                for (int i = 0; i < langs.getLangs().size(); i++) {
                    names[i] = langs.getLangs().get(i).getValue();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent();
                        intent.putExtra(Constant.SelectLangExtra, langs.getLangs().get(i).getKey() + ":" + langs.getLangs().get(i).getValue());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        }
    }

}
