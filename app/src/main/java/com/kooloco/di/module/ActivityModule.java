package com.kooloco.di.module;

import android.support.v4.app.FragmentManager;

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.manager.FragmentHandler;
import com.kooloco.ui.navigation.AppNavigator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hlink21 on 9/5/16.
 */
@Module
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    BaseActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    AppNavigator navigator(BaseActivity activity) {
        return (AppNavigator) activity;
    }

    @Provides
    @PerActivity
    FragmentManager fragmentManager(BaseActivity baseActivity) {
        return baseActivity.getSupportFragmentManager();
    }

    @Provides
    @PerActivity
    @Named("placeholder")
    int placeHolder(BaseActivity baseActivity) {
        return baseActivity.findFragmentPlaceHolder();
    }

    @Provides
    @PerActivity
    FragmentHandler fragmentHandler(com.kooloco.ui.manager.FragmentManager fragmentManager) {
        return fragmentManager;
    }


}
