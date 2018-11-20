package com.kooloco.uilocal.organaization.presenter;/**
 * Created by hlink44 on 11/10/17.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.organaization.view.PerAddBankView;

@PerActivity
public class PerAddBankPresenter extends BasePresenter<PerAddBankView> {
    @Inject
    public PerAddBankPresenter() {

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

    public void getOrganizationDashboard() {
        navigator.openOrgniztionDashboardView().replace(true);
    }
}
