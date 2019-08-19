package com.efulltech.etpspay.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataProccessor {

    public Context context;

    public DataProccessor(Context context){
        this.context = context;
    }

    public final static String PREFS_NAME = "com.efulltech.etpspay";

    public void setInt( String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public void setStr(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key,"DNF");
    }

    public void setBool(String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBool(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key,false);
    }
}
