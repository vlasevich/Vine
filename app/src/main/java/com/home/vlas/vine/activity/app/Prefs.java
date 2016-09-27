package com.home.vlas.vine.activity.app;


import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    public static final String ONE = "1";
    public static final String ZERO = "0";
    public static final int PAIR = 2;
    public static final String AUTH_URL = "/api/v1/auth";
    public static final String DASHBOARD_URL = "/api/v1/dashboard";
    private static final String PRE_LOAD = "preLoad";
    private static final String PREFS_NAME = "prefs";
    public static String POST_TOKEN_URL = "http://wine-cellar.biznestext.com";
    private static Prefs instance;
    private final SharedPreferences sharedPreferences;

    public Prefs(Context context) {

        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static Prefs with(Context context) {

        if (instance == null) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public boolean getPreLoad() {
        return sharedPreferences.getBoolean(PRE_LOAD, false);
    }

    public void setPreLoad(boolean totalTime) {

        sharedPreferences
                .edit()
                .putBoolean(PRE_LOAD, totalTime)
                .apply();
    }
}
