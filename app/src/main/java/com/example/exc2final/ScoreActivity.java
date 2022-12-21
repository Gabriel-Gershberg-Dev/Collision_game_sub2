package com.example.exc2final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ScoreActivity extends AppCompatActivity {

    public static final String KEY_SCORE = "KEY_SCORE";
    private int NUM_OF_RECORDS=5;
    private SPV3 storage= SPV3.getInstance();
    private int score;
    private MaterialTextView score_TBL;
    private Map<String,Integer> currentStoredScores;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent previousIntent = getIntent();
        score= previousIntent.getExtras().getInt(KEY_SCORE);

        if(storage.getAll()!=null)
            currentStoredScores=storage.getAll();
        storeScore();
        currentStoredScores=storage.getAll();
        findViews();
        score_TBL.setText(geSortedScores().toString());


    }

    private void findViews(){
        score_TBL = findViewById(R.id.score_TBL);
    }

    private void storeScore(){
        if(currentStoredScores!=null) {
            if (currentStoredScores.size() >= NUM_OF_RECORDS) {
                Map.Entry<String,Integer> entry=findMin();
                    if (score > entry.getValue() && !scoreExists()) {
                        String tempkey = entry.getKey();
                        storage.remove(entry.getKey());
                        storage.putInt(tempkey, score);
                    }
                }

            else if (!scoreExists())
                storage.putInt(Integer.toString(currentStoredScores.size()),score);
        }
        else
            storage.putInt("0",score);



    }
    private Map.Entry<String,Integer> findMin(){
        Map.Entry<String, Integer> tempEntry=currentStoredScores.entrySet().iterator().next();
        for (Map.Entry<String, Integer> entry : currentStoredScores.entrySet()) {
            if (tempEntry.getValue() > entry.getValue())
                tempEntry = entry;
        }
        return tempEntry;
    }

    private boolean scoreExists(){
        if(currentStoredScores!=null){
            for (Map.Entry<String, Integer> entry : currentStoredScores.entrySet()) {
                if (entry.getValue() == score)
                    return true;
            }
            return false;
        }
        return false;
    }
    private TreeSet geSortedScores(){
        TreeSet sortedScores= new TreeSet();
        for (Map.Entry<String, Integer> entry : currentStoredScores.entrySet())
            sortedScores.add(entry.getValue());
        return sortedScores;


    }
}