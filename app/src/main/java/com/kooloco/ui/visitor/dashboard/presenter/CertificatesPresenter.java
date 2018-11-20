package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 25/8/17.
 */

import javax.inject.Inject;


import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.visitor.dashboard.view.CertificatesView;

@PerActivity
public class CertificatesPresenter extends BasePresenter<CertificatesView> {
    @Inject
    public CertificatesPresenter() {

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
