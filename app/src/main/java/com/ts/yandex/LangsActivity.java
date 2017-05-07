package com.ts.yandex;

import android.app.Activity;
import android.os.Bundle;

import com.ts.yandex.API.Facade;

/**
 * Created by root on 04.05.2017.
 */

public class LangsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lang_fragment);

        Facade.getInstance().getLangs();
    }



}
