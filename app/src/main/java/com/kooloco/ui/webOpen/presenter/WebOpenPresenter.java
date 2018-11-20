package com.kooloco.ui.webOpen.presenter;
/**
 * Created by hlink on 22/8/18.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.webOpen.view.WebOpenView;

import javax.inject.Inject;


@PerActivity
public class WebOpenPresenter extends BasePresenter<WebOpenView> {

    @Inject
    public WebOpenPresenter() {
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