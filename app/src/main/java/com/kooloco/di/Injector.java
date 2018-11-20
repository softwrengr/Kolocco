package com.kooloco.di;


import android.app.Application;

import com.kooloco.di.component.ApplicationComponent;
import com.kooloco.di.component.DaggerApplicationComponent;

/**
 * Created by hlink21 on 9/5/16.
 */
public enum Injector {
    INSTANCE;
    ApplicationComponent applicationComponent;

    Injector() {
    }

    public void initAppComponent(Application application, String apiKey) {
        applicationComponent = DaggerApplicationComponent.builder()
                .application(application)
                .apiKey(apiKey)
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
