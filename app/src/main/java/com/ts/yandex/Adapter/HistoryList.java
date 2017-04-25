package com.ts.yandex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ts.yandex.R;
import com.ts.yandex.model.History;

import java.util.List;

/**
 * Created by root on 24.04.2017.
 */

public class HistoryList extends BaseAdapter {

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item, parent, false);
        }

        History h = getHistory(position);

        ((TextView) view.findViewById(R.id.from_text)).setText(h.getFrom_lang());
        ((TextView) view.findViewById(R.id.to_text)).setText(h.getTo_lang());
        ((TextView) view.findViewById(R.id.lang)).setText(h.getLang());
        CheckBox cb_favorite = (CheckBox) view.findViewById(R.id.favorite);
        // присваиваем чекбоксу обработчик
        cb_favorite.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cb_favorite.setTag(position);
        cb_favorite.setChecked(h.getFavorite());
        return view;
    }

    // Обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getHistory((Integer) buttonView.getTag()).setFavorite(isChecked);
        }
    };
}
