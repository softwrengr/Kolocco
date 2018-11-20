package com.kooloco.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import com.google.gson.Gson;
import com.kooloco.BuildConfig;
import com.kooloco.core.AppSession;
import com.kooloco.core.Session;

import java.io.File;
import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hlink21 on 9/5/16.
 */
@Module
public class ApplicationModule {

    @Provides
    @Named("cache")
    File provideCacheDir(Application application) {
        return application.getCacheDir();
    }

    @Provides
    @Singleton
    Resources provideResources(Application application) {
        return application.getResources();
    }

    @Provides
    @Singleton
    Locale provideCurrentLocale(Resources resources) {

        Locale locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = resources.getConfiguration().getLocales().get(0);
        } else {
            locale = resources.getConfiguration().locale;
        }

        return locale;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Session provideSession(AppSession appSession) {
        return appSession;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    @Named("aes-key")
    String provideAESKey() {
        return BuildConfig.aesKey;
    }

}
