package com.dinhtrongdat.pomodoro_app;

import android.content.Context;

public class DataManager {
    private static DataManager instance;
    private MySharePreferences mySPef;
    private static final String  PREF_TIME = "PREF_TIME";

    public static void init(Context context){
        instance = new DataManager();
        instance.mySPef = new MySharePreferences(context);
    }
    public static DataManager getInstance(){
        if(instance == null)
            instance = new DataManager();
        return instance;
    }
    public static void setTime(Long time){
        DataManager.getInstance().mySPef.putLongValue(PREF_TIME,time);
    }

    public static long getTime(){
        return DataManager.getInstance().mySPef.getongValue(PREF_TIME);
    }
}
