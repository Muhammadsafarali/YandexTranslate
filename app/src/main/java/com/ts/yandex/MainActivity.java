package com.ts.yandex;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (TabHost) findViewById(R.id.tabhost);
        initTabHost();
    }

    public void initTabHost() {

        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1");
        tabSpec.setContent(R.id.translate_layout);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab1_selector));
//        tabSpec.setIndicator("tab1");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab2");
        tabSpec.setContent(R.id.history_layout);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab2_selector));
//        tabSpec.setIndicator("tab2");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab3");
        tabSpec.setContent(R.id.settings_layout);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab3_selector));
//        tabSpec.setIndicator("tab3");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }

}
