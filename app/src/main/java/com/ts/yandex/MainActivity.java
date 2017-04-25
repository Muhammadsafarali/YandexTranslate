package com.ts.yandex;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ts.yandex.API.Facade;
import com.ts.yandex.Adapter.HistoryList;
import com.ts.yandex.model.History;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private TabHost tabHost;
    private TabHost tabHostHistory;
    private TabLayout tabLayout;
    private EditText editText;
    private static MenuItem menuItem;  // Кнопка меню в toolbar удаления истории
    private HistoryList adapter;
    private TextView translateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        tabHost = (TabHost) findViewById(R.id.tabhost);
//        tabHostHistory = (TabHost) findViewById(R.id.tabhost_history);
        tabLayout = (TabLayout) findViewById(R.id.home_tab_layout);
        translateView = (TextView) findViewById(R.id.tv_translate);
        Facade.getInstance().addObserver(this);
        editText = (EditText) findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1) {

                    // Запрос к yandex api
                    Facade.getInstance().TranslateText(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });


        initTabHost();
//        initHistoryTabHost();
        initTabLayout();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Running Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Order History"));
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        int count = tabLayout.getTabCount();
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tvFromLangs = (TextView) findViewById(R.id.toolbar_from_langs);
        tvFromLangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("LOG_MAIN_ACTIVITY", "Get From Langs");
            }
        });
        TextView tvToLangs = (TextView) findViewById(R.id.toolbar_to_langs);
        tvToLangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("LOG_MAIN_ACTIVITY", "Get To Langs");
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
                        menuItem.setVisible(false);
                    } break;
                    case 1: {
                        menuItem.setVisible(true);
//                        MainActivity.this.GetHistory();
                    } break;
                    case 2: {
                        menuItem.setVisible(false);
                    } break;
                }
            }
        });
    }

    public void initHistoryTabHost() {

       /* tabHostHistory.setup();
        TabHost.TabSpec tabSpec = tabHostHistory.newTabSpec("tab1");
//        tabSpec.setContent(null);
        tabSpec.setIndicator("История");
        tabHostHistory.addTab(tabSpec);

        tabSpec = tabHostHistory.newTabSpec("tab2");
//        tabSpec.setContent(R.id.history_layout);
        tabSpec.setIndicator("Избранное");
        tabHostHistory.addTab(tabSpec);

        tabHostHistory.setCurrentTab(0);*/

        /*tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int i = tabHost.getCurrentTab();
                switch (i) {
                    case 0: {
                        menuItem.setVisible(false);
                    } break;
                    case 1: {
                        menuItem.setVisible(true);
//                        MainActivity.this.GetHistory();
                    } break;
                }
            }
        });*/
    }

    public void GetHistory() {
        List<History> history = Facade.getInstance().GetHistory();
        adapter = new HistoryList(this, history);
        ListView lview = (ListView) findViewById(R.id.history_list);
        lview.setAdapter(adapter);
    }

    public void DeleteClick(View view) {
        editText.setText("");
        translateView.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete, menu);
        menuItem = menu.findItem(R.id.menu_remove_all);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_remove_all) {
            Facade.getInstance().RemoveHistory();
            GetHistory();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void update(Observable observable, Object obj) {
        if (obj != null) {
            translateView.setText(String.valueOf(obj));
        }
    }


    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    LangsFragment tab1 = new LangsFragment();
                    return tab1;
                case 1:
                    LangsFragment tab2 = new LangsFragment();
                    return tab2;
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
