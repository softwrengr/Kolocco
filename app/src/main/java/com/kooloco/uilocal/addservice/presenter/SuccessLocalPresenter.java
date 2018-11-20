package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 5/10/17.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.addservice.view.SuccessLocalView;

@PerActivity
public class SuccessLocalPresenter extends BasePresenter<SuccessLocalView> {
    @Inject
    public SuccessLocalPresenter() {

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

    public void openLocalApp() {

        navigator.startHomeLocalActivity().byFinishingAll().start();
    }
}
