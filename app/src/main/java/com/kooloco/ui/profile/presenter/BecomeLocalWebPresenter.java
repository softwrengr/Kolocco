package com.kooloco.ui.profile.presenter;
/**
 * Created by hlink on 19/3/18.
 */


import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.profile.view.BecomeLocalWebView;

import javax.inject.Inject;

@PerActivity
public class BecomeLocalWebPresenter extends BasePresenter<BecomeLocalWebView> {

    @Inject
    public BecomeLocalWebPresenter() {
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

    public void openBecomeLocalScreen() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.BecomeLocal).byFinishingCurrent().start();
    }

}