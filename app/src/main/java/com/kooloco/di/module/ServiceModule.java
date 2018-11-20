package com.kooloco.di.module;


import android.os.Build;

import com.kooloco.BuildConfig;
import com.kooloco.data.datasource.KoolocoDataSource;
import com.kooloco.data.datasource.KoolocoLocalDataSource;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.service.Service;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hlink21 on 12/5/17.
 */
@Module
public class ServiceModule {

    @Provides
    @Singleton
    Service provideService(Retrofit retrofit) {
        return retrofit.create(Service.class);
    }

    @Provides
    @Singleton
    KoolocoRepository provideKoolocoRepository(KoolocoDataSource koolocoDataSource, KoolocoLocalDataSource koolocoLocalDataSource) {
        return BuildConfig.isLocal ? koolocoLocalDataSource : koolocoDataSource;
    }

}
