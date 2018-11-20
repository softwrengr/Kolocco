package com.kooloco.ui.presenter;

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.view.LoginView;

import javax.inject.Inject;

/**
 * Created by hlink21 on 14/7/17.
 */
@PerActivity
public class LoginPresenter extends BasePresenter<LoginView> {

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void resume() {

//        navigator.openChildLogin().addAsChild(false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
