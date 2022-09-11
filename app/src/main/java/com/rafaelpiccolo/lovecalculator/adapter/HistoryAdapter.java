package com.rafaelpiccolo.lovecalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rafaelpiccolo.lovecalculator.R;
import com.rafaelpiccolo.lovecalculator.model.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    private final List<HistoryItem> items = new ArrayList<>();
    private final Context context;

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    public void reload(List<HistoryItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(HistoryItem item) {
        items.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View createdView = getInflate(parent);
        HistoryItem item = items.get(position);
        setInfo(createdView, item);
        return createdView;
    }

    private void setInfo(View view, HistoryItem item) {
        TextView nameOne = view.findViewById(R.id.history_name_one);
        nameOne.setText(item.getLoverOne());
        TextView nameTwo = view.findViewById(R.id.history_name_two);
        nameTwo.setText(item.getLoverTwo());
    }

    private View getInflate(ViewGroup parent) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.history_item, parent, false);
    }

}
