package com.kooloco.uilocal.home.presenter;/**
 * Created by hlink44 on 5/10/17.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.home.view.HomeView;

@PerActivity
public class HomePresenter extends BasePresenter<HomeView> {
    @Inject
    public HomePresenter() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void openNotification() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.NotificationLocal).start();
    }
}
