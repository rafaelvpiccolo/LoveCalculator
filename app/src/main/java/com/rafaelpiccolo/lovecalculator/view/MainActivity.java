package com.rafaelpiccolo.lovecalculator.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rafaelpiccolo.lovecalculator.R;
import com.rafaelpiccolo.lovecalculator.controller.RetrofitClient;
import com.rafaelpiccolo.lovecalculator.model.LoveResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private LoveResult loveResult;
    private Button resultButton;
    private EditText fname;
    private EditText sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultButton = findViewById(R.id.love_button);
        fname = findViewById(R.id.name_one);
        sname = findViewById(R.id.name_two);
        ConfigureButton();
    }

    private void ConfigureButton(){
        resultButton.setOnClickListener(v -> {
            if(TextUtils.isEmpty(fname.getText()) || TextUtils.isEmpty(sname.getText())) {
                Toast.makeText(this, "Fill the names before you calculate", Toast.LENGTH_SHORT).show();
            }
            else {
                getResults();
            }
        });
    }

    private void getResults() {
        Call<LoveResult> call = RetrofitClient.getInstance()
                .getApi()
                .getResult(fname.getText().toString(), sname.getText().toString());

        call.enqueue(new Callback<LoveResult>() {
            @Override
            public void onResponse(Call<LoveResult> call, Response<LoveResult> response) {
                loveResult = response.body();
                Log.i("log", "Message: " + response.message());
                Toast.makeText(MainActivity.this, loveResult.getResult(), Toast.LENGTH_SHORT).show();
                resultButton.setText(loveResult.getPercentage() + "%");
            }

            @Override
            public void onFailure(Call<LoveResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}