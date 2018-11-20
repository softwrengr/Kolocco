package com.kooloco.ui.visitor.organizationDetails.presenter;/**
 * Created by hlink44 on 19/9/17.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;

@PerActivity
public class LocalVisitorMapPresenter extends BasePresenter<LocalVisitorMapView> {
    @Inject
    public LocalVisitorMapPresenter() {

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

    public void onBack() {
        navigator.goBack();
    }
}
