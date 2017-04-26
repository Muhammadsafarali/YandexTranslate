package com.ts.yandex.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ts.yandex.API.Facade;
import com.ts.yandex.R;
import com.ts.yandex.model.History;

import java.util.List;

/**
 * Created by root on 24.04.2017.
 */

public class HistoryList extends BaseAdapter {

    private static final String LOG_TAG = "HISTORY_LIST";

    private Context ctx;
    LayoutInflater inflater;
    List<History> list;

    public HistoryList(Context context, List<History> _list) {
        ctx = context;
        list = _list;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    History getHistory(int pos) {
        return ((History) getItem(pos));
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item, parent, false);
        }

        History h = getHistory(position);

        ((TextView) view.findViewById(R.id.from_text)).setText(h.getFrom_lang());
        ((TextView) view.findViewById(R.id.to_text)).setText(h.getTo_lang());
        ((TextView) view.findViewById(R.id.lang)).setText(h.getLang());
        final CheckBox cb_favorite = (CheckBox) view.findViewById(R.id.favorite);
        cb_favorite.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                getHistory(position).setFavorite(isChecked);
                Log.e(LOG_TAG, "isChecked");
            }
        });

        // пишем позицию
        cb_favorite.setTag(position);
        if (getHistory(position).getFavorite()) {
            cb_favorite.setChecked(true);
        }
        else {
            cb_favorite.setChecked(false);
        }

        // присваиваем чекбоксу обработчик
        cb_favorite.setOnCheckedChangeListener(myCheckChangeList);
        return view;
    }

    // Обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            int position = (Integer) buttonView.getTag();
            History history = getHistory(position);
            getHistory(position).setFavorite(isChecked);
            Facade.getInstance().MarkFavorite(history, isChecked);
            Log.e(LOG_TAG, "isChecked");
        }
    };


}
