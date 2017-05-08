package com.ts.yandex;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ts.yandex.API.Facade;
import com.ts.yandex.Adapter.HistoryList;
import com.ts.yandex.Utils.Constant;
import com.ts.yandex.model.History;
import com.ts.yandex.model.ResultObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Observer {

    private final static String LOG_TAG = "MAIN_ACTIVITY";

    private TabHost tabHost;
    private TabLayout tabLayout;
    private EditText editText;
//    private static MenuItem menuItem;  // Кнопка меню в toolbar удаления истории
    private HistoryList adapter;
    private TextView translateView;
    private int tabPosition;

    private TextView ToolBarFromLang;
    private TextView ToolBarToLang;

    private Timer timer = new Timer();
    private final long DELAY = 1000; // мс

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabLayout = (TabLayout) findViewById(R.id.home_tab_layout);
        translateView = (TextView) findViewById(R.id.tv_translate);
        Facade.getInstance().addObserver(this);
        editText = (EditText) findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {

            String text;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1) {
                   text = charSequence.toString();
                    if (timer != null)
                        timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() >= 2) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Facade.getInstance().TranslateText(text);
                        }
                    }, DELAY);
                }
            }
        });


        initTabHost();
        initTabLayout();
//        Facade.getInstance().RemoveHistory();/**/
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("История"));
        tabLayout.addTab(tabLayout.newTab().setText("Избранное"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0: {
                        // Отобразить список истории
                        List<History> history = Facade.getInstance().GetHistory();
                        PrintHistory(history);
                    } break;
                    case 1: {
                        // Отобразить список избранного
                        List<History> favorite = Facade.getInstance().GetFavorite();
                        PrintHistory(favorite);
                    } break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tvFromLangs = (TextView) findViewById(R.id.toolbar_from_langs);
        tvFromLangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LangsActivity.class);
                startActivityForResult(intent, Constant.FromLangCode);
            }
        });
        TextView tvToLangs = (TextView) findViewById(R.id.toolbar_to_langs);
        tvToLangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LangsActivity.class);
                startActivityForResult(intent, Constant.ToLangCode);
            }
        });
    }

    public void initTabHost() {

        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1");
        tabSpec.setContent(R.id.translate_layout);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab1_selector));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab2");
        tabSpec.setContent(R.id.history_fragment);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab2_selector));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab3");
        tabSpec.setContent(R.id.settings_layout);
        tabSpec.setIndicator("", ContextCompat.getDrawable(this, R.drawable.tab3_selector));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int i = tabHost.getCurrentTab();
                switch (i) {
                    case 0: {
//                        menuItem.setVisible(false);
                    } break;
                    case 1: {
//                        menuItem.setVisible(true);
                        MainActivity.this.PrintHistory(Facade.getInstance().GetHistory());
                    } break;
                    case 2: {
//                        menuItem.setVisible(false);
                    } break;
                }
            }
        });
    }


    public void PrintHistory(List<History> history) {
//        List<History> history = Facade.getInstance().GetHistory();
        adapter = new HistoryList(this, history);
        ListView lview = (ListView) findViewById(R.id.history_list);
        lview.setAdapter(adapter);
    }

    public void DeleteClick(View view) {
        editText.setText("");
        translateView.setText("");
    }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete, menu);
        menuItem = menu.findItem(R.id.menu_remove_all);
        menuItem.setVisible(false);
        return true;
    }*/

    public void RemoveHistoryClick(View view) {
        if (tabPosition == 0)
            Facade.getInstance().RemoveHistory();
        if (tabPosition == 1)
            Facade.getInstance().RemoveFaforite();
        PrintHistory(new ArrayList<History>());
    }


/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_remove_all) {
            Facade.getInstance().RemoveHistory();
            GetHistory();
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Constant.FromLangCode: {
                String from = data.getStringExtra(Constant.FromLangExtra);
            }break;
            case Constant.ToLangCode: {
                String to = data.getStringExtra(Constant.ToLangExtra);
            } break;
            default: return;
        }
    }

    @Override
    public void update(Observable observable, Object obj) {
        if (obj != null) {
            ResultObserver result = (ResultObserver)obj;
            if (result.getCode() == Constant.translate_save_complete) {
                translateView.setText(String.valueOf(result.getObj()));
            }
        }
    }

    public void GetLang(View view) {
        Log.e(LOG_TAG, "GetLang");
    }


}
