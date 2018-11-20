package com.kooloco.ui.visitor.organizationDetails.presenter;/**
 * Created by hlink44 on 6/10/17.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.visitor.organizationDetails.view.OrganizationReportView;

@PerActivity
public class OrganizationReportPresenter extends BasePresenter<OrganizationReportView> {
    @Inject
    public OrganizationReportPresenter() {

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
}
