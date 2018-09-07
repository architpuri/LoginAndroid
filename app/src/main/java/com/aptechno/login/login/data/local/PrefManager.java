package com.aptechno.login.login.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.aptechno.login.login.ui.BaseApp;

public class PrefManager {

    private final static String PREFERENCE_NAME = "com.aptechno.login";
    private final String FIRST_TIME_USER = "com.aptechno.login.first_time_user";
    private final String ACCESS_TOKEN = "com.aptechno.login.access_token";
    private final String USER_ID = "com.aptechno.login.user_id";
    private final String USER_EMAIL = "com.aptechno.login.user_email";

    private static SharedPreferences sharedPreferences;
    private static PrefManager prefManager;

    /**
     * constructor is made private to make this class singleton
     */
    private PrefManager() {
        sharedPreferences = BaseApp.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static PrefManager getInstance() {
        if (prefManager == null) {
            prefManager = new PrefManager();
        }
        return prefManager;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public boolean isFirstTimeUser() {
        return sharedPreferences.getBoolean(FIRST_TIME_USER, true);
    }

    public void setFirstTimeUser(boolean value) {
        sharedPreferences.edit().putBoolean(FIRST_TIME_USER, value).apply();
    }

    public void logoutUser() {
        //this will clear all the stored data but we need to keep fcm token

        sharedPreferences.edit().clear().apply();
    }


    public void setAccessToken(String accessToken) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public boolean isLoggedin() {
        return getAccessToken() != null;
    }

    private void setUserId(String userId) {
        sharedPreferences.edit().putString(USER_ID, userId).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "");
    }

    private void setUserEmail(String email) {
        sharedPreferences.edit().putString(USER_EMAIL, email).apply();
    }


}
