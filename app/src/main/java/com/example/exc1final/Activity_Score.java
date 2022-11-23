package com.example.exc1final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textview.MaterialTextView;

public class Activity_Score extends AppCompatActivity {

    public static final String KEY_STATUS = "KEY_STATUS";
    public static final String KEY_SCORE = "KEY_SCORE";

    private MaterialTextView score_LBL_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViews();

        Intent previousIntent = getIntent();
        String status = previousIntent.getExtras().getString(KEY_STATUS);
        int score = previousIntent.getExtras().getInt(KEY_SCORE);
        score_LBL_score.setText(status + "\n" + score);
    }

    private void findViews() {
        score_LBL_score = findViewById(R.id.score_LBL_score);
    }
}