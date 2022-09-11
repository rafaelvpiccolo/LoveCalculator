package com.rafaelpiccolo.lovecalculator.controller;

import android.content.Context;
import android.widget.ListView;
import androidx.annotation.NonNull;

import com.rafaelpiccolo.lovecalculator.adapter.HistoryAdapter;

public class HistoryController {
    private final Context context;
    private HistoryAdapter adapter;

    public HistoryController(Context context) {
        this.context = context;
    }

    public void configureAdapter(@NonNull ListView list) {
        adapter = new HistoryAdapter(context);
        list.setAdapter(adapter);
    }
}
