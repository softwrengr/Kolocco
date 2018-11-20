package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 25/8/17.
 */

import android.os.Bundle;


import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.view.CertificatesView;
import com.kooloco.ui.visitor.dashboard.presenter.CertificatesPresenter;

public class CertificatesFragment extends BaseFragment<CertificatesPresenter, CertificatesView> implements CertificatesView {

    @Override
    protected int createLayout() {
        return R.layout.fragment_visitor_certificates;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    @Override
    protected CertificatesView createView() {
        return this;
    }

    @Override
    protected void bindData() {
    }
}
