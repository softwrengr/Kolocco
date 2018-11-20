package com.kooloco.di.component;


import com.google.gson.Gson;
import com.kooloco.di.PerActivity;
import com.kooloco.di.module.ActivityModule;
import com.kooloco.di.module.FragmentModule;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.authantication.AuthActivity;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.isolated.IsolatedFullActivity;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.splash.SplashActivity;
import com.kooloco.uilocal.MainLocalActivity;

import dagger.Component;

/**
 * Created by hlink21 on 9/5/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    BaseActivity activity();

    AppNavigator navigator();

    FragmentComponent plus(FragmentModule fragmentModule);

    void inject(AuthActivity authActivity);

    void inject(MainActivity mainActivity);

    void inject(IsolatedFullActivity isolatedActivity);

    void inject(MainLocalActivity mainLocalActivity);

    void inject(SplashActivity splashActivity);
}
