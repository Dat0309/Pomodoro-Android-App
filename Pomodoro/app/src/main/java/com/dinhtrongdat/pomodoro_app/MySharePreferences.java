package com.dinhtrongdat.pomodoro_app;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreferences {
    private static final String MY_SHARED_PREFERENCED = "my_sref";
    private Context mContex;

    public MySharePreferences(Context mContex) {
        this.mContex = mContex;
    }

    public void putLongValue(String key,long value){
        SharedPreferences sharedPreferences = mContex.getSharedPreferences(MY_SHARED_PREFERENCED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getongValue(String key){
        SharedPreferences sharedPreferences = mContex.getSharedPreferences(MY_SHARED_PREFERENCED, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 1500000);
    }
}
