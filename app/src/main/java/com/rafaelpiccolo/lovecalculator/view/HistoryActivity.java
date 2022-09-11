package com.rafaelpiccolo.lovecalculator.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.rafaelpiccolo.lovecalculator.R;
import com.rafaelpiccolo.lovecalculator.adapter.HistoryAdapter;
import com.rafaelpiccolo.lovecalculator.controller.HistoryController;

import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    HistoryController historyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        historyController = new HistoryController(this);
        setTitle("History");
        configureList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void configureList() {
        ListView list = findViewById(R.id.history_list);
        historyController.configureAdapter(list);
    }
}