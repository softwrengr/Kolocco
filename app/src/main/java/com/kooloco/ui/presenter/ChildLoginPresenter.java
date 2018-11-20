package com.kooloco.ui.presenter;

import com.kooloco.di.PerFragment;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.view.LoginView;

import javax.inject.Inject;

/**
 * Created by hlink21 on 1/8/17.
 */
@PerFragment
public class ChildLoginPresenter extends BasePresenter<LoginView> {

    @Inject
    public ChildLoginPresenter() {
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
