package com.kooloco;


import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.google.firebase.FirebaseApp;
import com.kooloco.constant.Common;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.di.Injector;
import com.crashlytics.android.Crashlytics;
import com.kooloco.ui.MainActivity;
import com.kooloco.uilocal.MainLocalActivity;
import com.kooloco.util.LifecycleListener;

import java.util.Locale;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hlink21 on 27/6/17.
 */

public class Startup extends MultiDexApplication {
    SharedPreferences sharedPreferences;

    @Inject
    AppPreferences appPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        FirebaseApp.initializeApp(this);
        Fabric.with(this, new Crashlytics());
        Injector.INSTANCE.initAppComponent(this, "kooloco");
        Injector.INSTANCE.getApplicationComponent().inject(this);
        Realm.init(this);
        MultiDex.install(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        addLifecycle();

        String language = appPreferences.getString(Common.APPLANG);

        if (!language.isEmpty()) {
            changeAppLanguage(language);
        }
    }

    private void addLifecycle() {
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new LifecycleListener(this));
    }


    public void changeAppLanguage(String language) {


        Locale defaultLocale = Locale.getDefault();

        String languageCode;

        language = language.toLowerCase();

        switch (language) {
            case "german":
                languageCode = "de";
                break;
            case "spanish":
                languageCode = "es";
                break;
            case "french":
                languageCode = "fr";
                break;
            default:
                languageCode = "en";
                break;
        }


        if (!languageCode.equalsIgnoreCase(defaultLocale.getLanguage())) {
            Locale newLocal = new Locale(languageCode, defaultLocale.getCountry());
            Locale.setDefault(newLocal);
            Configuration configuration = getResources().getConfiguration();
            configuration.setLocale(newLocal);
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}
