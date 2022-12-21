package com.example.exc2final;
import android.content.Context;
import android.content.SharedPreferences;

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

    public  Map getAll(){
        if(preferences.getAll().isEmpty())
            return null;
        else
            return preferences.getAll();
    }

    public void remove(String key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public String getString(String key, String def) {

        return preferences.getString(key, def);
    }

}