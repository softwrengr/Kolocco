package com.kooloco.uilocal.earnings.presenter;/**
 * Created by hlink44 on 9/10/17.
 */

import com.kooloco.di.PerActivity;
import com.kooloco.model.AllMonth;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.uilocal.earnings.view.AllMonthView;
import com.kooloco.uilocal.earnings.view.MonthEarningView;

@PerActivity
public class AllMonthPresenter extends BasePresenter<AllMonthView> {
    @Inject
    public AllMonthPresenter() {

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

    public void openMonthWise(final AllMonth allMonth) {
        navigator.openMonthEarningView().hasData(new Passable<MonthEarningView>() {
            @Override
            public void passData(MonthEarningView monthEarningView) {
                monthEarningView.setAllMonth(allMonth);
            }
        }).replace(true);
    }
}
