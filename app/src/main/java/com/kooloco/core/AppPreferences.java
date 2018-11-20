package com.kooloco.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by hlink21 on 31/5/16.
 */
@Singleton
public class AppPreferences {


    private final SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Inject
    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Common.SHARED_PREF_NAME, Context.MODE_PRIVATE);

    }


    @SuppressLint("CommitPrefEdits")
    public void putString(String name, String value) {
        editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }


    @SuppressLint("CommitPrefEdits")
    public void putBoolean(String name, boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public boolean getBoolean(String name) {
        return sharedPreferences.getBoolean(name, false);
    }

    public String getString(String name) {
        return sharedPreferences.getString(name, "");
    }

    public int getInt(String name) {
        return sharedPreferences.getInt(name, 0);
    }

    public void putTutorial(String name, boolean value) {
        editor.putBoolean(name, value);
        editor.apply();
    }

    public void clearAll() {
        sharedPreferences.edit()
                .clear()
                .apply();
    }

    public void putFloat(String name, float value) {

        editor = sharedPreferences.edit();
        editor.putFloat(name, value);
        editor.apply();
    }

    public float getFloat(String name) {
        return sharedPreferences.getFloat(name, 0f);
    }


}
