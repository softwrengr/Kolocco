package com.kooloco.ui.expereince.presenter;
/**
 * Created by hlink on 18/4/18.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.expereince.view.DemoCalenderView;

import javax.inject.Inject;


@PerActivity
public class DemoCalenderPresenter extends BasePresenter<DemoCalenderView> {

    @Inject
    public DemoCalenderPresenter() {
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