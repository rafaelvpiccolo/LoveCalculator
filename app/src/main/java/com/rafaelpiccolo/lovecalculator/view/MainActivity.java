package com.rafaelpiccolo.lovecalculator.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.rafaelpiccolo.lovecalculator.R;
import com.rafaelpiccolo.lovecalculator.controller.RetrofitClient;
import com.rafaelpiccolo.lovecalculator.model.LoveResult;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private boolean buttonPressed = false;

    private ConstraintLayout mainActivity;
    private LoveResult loveResult;
    private TextView percentText;
    private ImageButton resultButton;
    private TextInputLayout fnameLayout;
    private TextInputLayout snameLayout;
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
        fnameLayout = findViewById(R.id.name_one_layout);
        snameLayout = findViewById(R.id.name_two_layout);

        fname.addTextChangedListener(textWatcher);
        sname.addTextChangedListener(textWatcher);

        ConfigureButton();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.history_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.history_button) {
            startActivity(new Intent(this, HistoryActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(buttonPressed){
                if(TextUtils.isEmpty(fname.getText()) || TextUtils.isEmpty(sname.getText())) {
                    resultButton.setImageResource(R.drawable.heart_unpressed);
                    mainActivity.setBackgroundColor(getResources().getColor(R.color.white));

                    fnameLayout.setBoxBackgroundColor(getResources().getColor(R.color.white));
                    fnameLayout.setBoxStrokeColorStateList(ColorStateList.valueOf(getResources().getColor(R.color.text_input_custom)));
                    fnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.hint_pink)));
                    fnameLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.hint_pink)));

                    snameLayout.setBoxBackgroundColor(getResources().getColor(R.color.white));
                    snameLayout.setBoxStrokeColorStateList(ColorStateList.valueOf(getResources().getColor(R.color.text_input_custom)));
                    snameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.hint_pink)));
                    snameLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.hint_pink)));

                    fname.setTextColor(getResources().getColor(R.color.text_pink));
                    sname.setTextColor(getResources().getColor(R.color.text_pink));

                    percentText.setText("");
                    buttonPressed = false;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void ConfigureButton(){
        resultButton.setOnClickListener(v -> {
            if(TextUtils.isEmpty(fname.getText())) {
                fnameLayout.setError("Fill me first!");
            }
            else if(TextUtils.isEmpty(sname.getText())) {
                fnameLayout.setError(null);
                snameLayout.setError("Fill me too!");
            }
            else {
                buttonPressed = true;
                resultButton.setImageResource(R.drawable.heart_pressed);
                mainActivity.setBackgroundColor(getResources().getColor(R.color.background_pink));

                fnameLayout.setError(null);
                fnameLayout.setBoxBackgroundColor(getResources().getColor(R.color.secondary_text_box));
                fnameLayout.setBoxStrokeColorStateList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                fnameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                fnameLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));

                snameLayout.setError(null);
                snameLayout.setBoxBackgroundColor(getResources().getColor(R.color.secondary_text_box));
                snameLayout.setBoxStrokeColorStateList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                snameLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                snameLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));

                fname.setTextColor(getResources().getColor(R.color.white));
                sname.setTextColor(getResources().getColor(R.color.white));
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