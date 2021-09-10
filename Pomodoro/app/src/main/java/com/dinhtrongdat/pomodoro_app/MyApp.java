package com.dinhtrongdat.pomodoro_app;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.init(getApplicationContext());
    }
}
