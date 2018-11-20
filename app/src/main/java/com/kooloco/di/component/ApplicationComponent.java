package com.kooloco.di.component;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.kooloco.Startup;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.datasource.DatabaseCacheDataSource;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.module.ApplicationModule;
import com.kooloco.di.module.NetModule;
import com.kooloco.di.module.ServiceModule;
import com.kooloco.util.Validator;

import java.io.File;
import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by hlink21 on 9/5/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class,
        NetModule.class,
        ServiceModule.class
})
public interface ApplicationComponent {

    Context context();

    AppPreferences appPreferences();

    @Named("cache")
    File provideCacheDir();

    Resources provideResources();

    Locale provideCurrentLocale();

    Session session();

    Gson gson();

    KoolocoRepository provideDemoRepository();

    Validator validator();

    DatabaseCacheDataSource provideDatabaseCacheDataSource();

    void inject(Startup startup);

    @Component.Builder
    interface ApplicationComponentBuilder {

        @BindsInstance
        ApplicationComponentBuilder apiKey(@Named("api-key") String apiKey);

        @BindsInstance
        ApplicationComponentBuilder application(Application application);

        ApplicationComponent build();
    }

}
