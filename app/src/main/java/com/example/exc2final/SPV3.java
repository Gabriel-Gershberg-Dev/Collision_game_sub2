package com.example.exc2final;


import static com.example.exc2final.ScoreActivity.SP_KEY_NAME;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class SPV3 {

    private static final String DB_FILE = "DB_FILE";


    private static SPV3 mySPV3 = null;
    private SharedPreferences preferences;

    public static SPV3 getInstance() {
        return mySPV3;
    }

    public static void init(Context context) {
        if (mySPV3 == null) {
            mySPV3 = new SPV3(context);
        }
    }

    private SPV3(Context context) {

        preferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public PlayersList loadList() {
        PlayersList playersList = new PlayersList();
        playersList.setPlayersList(new ArrayList<>());
        String json = getString(SP_KEY_NAME, "0");
        if (json != null) {
            playersList = new Gson().fromJson(json, PlayersList.class);
        }
        return playersList;

    }

    public void storePlayersList(PlayersList playersList) {
        String json = new Gson().toJson(playersList);
        putString(SP_KEY_NAME, json);
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }


    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public int getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public Map getAll() {
        if (preferences.getAll().isEmpty())
            return null;
        else
            return preferences.getAll();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public String getString(String key, String def) {
        if (preferences.getAll().isEmpty())
            return null;
        else
            return preferences.getString(key, def);
    }

}