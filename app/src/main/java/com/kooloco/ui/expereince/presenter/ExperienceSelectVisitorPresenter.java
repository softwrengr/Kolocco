package com.kooloco.ui.expereince.presenter;
/**
 * Created by hlink on 21/4/18.
 */

import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.expereince.view.ExperienceSelectVisitorView;

import javax.inject.Inject;


@PerActivity
public class ExperienceSelectVisitorPresenter extends BasePresenter<ExperienceSelectVisitorView> {

    @Inject
    public ExperienceSelectVisitorPresenter() {
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

    public void getData() {
        view.setData(Temp.getListExpNew());
    }

    public void openExpSelectDate(ExperienceBookingFlow experienceBookingFlow) {
        navigator.openExperienceSelectDateTimeView().hasData(experienceSelectDateTimeView -> experienceSelectDateTimeView.setExperienceBooking(experienceBookingFlow)).replace(true);
    }
}