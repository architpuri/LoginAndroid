package com.aptechno.login.login.ui;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.aptechno.login.login.BuildConfig;

public class BaseApp extends Application {

    private static BaseApp INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (BuildConfig.DEBUG) {
            //Timber.plant(new Timber.DebugTree());
        }
    }


    public static BaseApp getContext() {
        return INSTANCE;
    }
}
