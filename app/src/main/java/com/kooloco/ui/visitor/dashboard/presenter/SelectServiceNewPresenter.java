package com.kooloco.ui.visitor.dashboard.presenter;
/**
 * Created by hlink on 8/2/18.
 */


import com.kooloco.di.PerActivity;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.visitor.dashboard.view.SelectServiceNewView;

import javax.inject.Inject;

@PerActivity
public class SelectServiceNewPresenter extends BasePresenter<SelectServiceNewView> {

    @Inject
    public SelectServiceNewPresenter() {
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

    public void openSelectActivity(VisitorBooking visitorBooking) {
        navigator.openSelectActivityNew().hasData(selectActivityNewView -> {
            selectActivityNewView.setVisitorBooking(visitorBooking);
        }).replace(true);
    }
}