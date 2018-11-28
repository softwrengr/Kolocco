package com.kooloco.di.module;


import com.kooloco.core.AESCryptoInterceptor;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.exception.AuthenticationException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hlink21 on 11/5/17.
 */


@Module(includes = ApplicationModule.class)
public class NetModule {


    public NetModule() {
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(@Named("header") Interceptor headerInterceptor, @Named("pre_validation") Interceptor networkInterceptor) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(networkInterceptor)
                .connectTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.newBuilder().addInterceptor(interceptor).build();
        return new Retrofit.Builder()
                .baseUrl(URLFactory.provideHttpUrl())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    @Named("header")
    Interceptor provideHeaderInterceptor(final Session session) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("API-KEY", session.getApiKey())
                        .addHeader("token", session.getUserSession())
                        .addHeader("Content-Language", session.getAppLanguage())
                        .build();
                return chain.proceed(build);
            }
        };
    }

    @Provides
    @Singleton
    @Named("pre_validation")
    Interceptor provideNetworkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response proceed = chain.proceed(chain.request());
                int code = proceed.code();

                if (code == 401 || code == 403)
                    throw new AuthenticationException();

                return proceed;
            }
        };
    }

    @Provides
    @Singleton
    @Named("aes")
    Interceptor provideAesCryptoInterceptor(AESCryptoInterceptor aesCryptoInterceptor) {
        return aesCryptoInterceptor;
    }


}
