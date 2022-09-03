package com.rafaelpiccolo.lovecalculator.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.rafaelpiccolo.lovecalculator.R;
import com.rafaelpiccolo.lovecalculator.controller.RetrofitClient;
import com.rafaelpiccolo.lovecalculator.model.LoveResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout mainActivity;
    private LoveResult loveResult;
    private TextView percentText;
    private ImageButton resultButton;
    private EditText fname;
    private EditText sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = findViewById(R.id.main_activity);
        resultButton = findViewById(R.id.love_button);
        percentText = findViewById(R.id.percent);

        fname = findViewById(R.id.name_one);
        sname = findViewById(R.id.name_two);

        fname.addTextChangedListener(textWatcher);
        sname.addTextChangedListener(textWatcher);

        ConfigureButton();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(fname.getText()) || TextUtils.isEmpty(sname.getText())) {
                resultButton.setImageResource(R.drawable.heart_unpressed);
                mainActivity.setBackgroundColor(getResources().getColor(R.color.white));
                percentText.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void ConfigureButton(){
        resultButton.setOnClickListener(v -> {
            if(TextUtils.isEmpty(fname.getText())) {
                fname.setError("Fill me first!");
            }
            else if(TextUtils.isEmpty(sname.getText())) {
                sname.setError("Fill me first!");
            }
            else {
                resultButton.setImageResource(R.drawable.heart_pressed);
                mainActivity.setBackgroundColor(getResources().getColor(R.color.background_pink));
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
                int percentage = Integer.parseInt(loveResult.getPercentage());
                ValueAnimator animator = ValueAnimator.ofInt(0, percentage);
                animator.setDuration(2000);
                animator.addUpdateListener(animation -> percentText.setText(animation.getAnimatedValue().toString() + "%"));
                animator.start();
                Toast.makeText(MainActivity.this, loveResult.getResult(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoveResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}