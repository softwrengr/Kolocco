package com.kooloco.uilocal.setavability.presenter;/**
 * Created by hlink44 on 11/10/17.
 */

import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.setavability.view.DisableSportView;

@PerActivity
public class DisableSportPresenter extends BasePresenter<DisableSportView> {
    @Inject
    public DisableSportPresenter() {

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

    public void getData() {
        view.setData(Temp.getDisableSport());
    }
}
