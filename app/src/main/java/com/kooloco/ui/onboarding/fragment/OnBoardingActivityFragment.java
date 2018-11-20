package com.kooloco.ui.onboarding.fragment;
/**
 * Created by hlink on 18/5/18.
 */

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.onboarding.presenter.OnBoardingActivityPresenter;
import com.kooloco.ui.onboarding.view.OnBoardingActivityView;

import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class OnBoardingActivityFragment extends BaseFragment<OnBoardingActivityPresenter, OnBoardingActivityView> implements OnBoardingActivityView {

    private String onBoardingData = "";

    @Override
    protected int createLayout() {
        return R.layout.fragment_onboarding_activity;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OnBoardingActivityView createView() {
        return this;
    }

    @Override
    protected void bindData() {

    }


    @OnClick(R.id.buttonLetsStart)
    public void onClick() {
        presenter.callWs(onBoardingData);
    }

    @Override
    public void setOnBoardingData(String onBoardingData) {
        this.onBoardingData = onBoardingData;
    }
}
