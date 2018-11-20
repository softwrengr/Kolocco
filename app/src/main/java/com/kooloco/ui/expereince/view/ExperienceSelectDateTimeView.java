package com.kooloco.ui.expereince.view;

import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.SchedulePrice;
import com.kooloco.ui.base.RootView;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;

/**
 * Created by hlink on 21/4/18.
 */

public interface ExperienceSelectDateTimeView extends RootView {

    void setData(List<SchedulePrice> listSchedulePrice);

    void setExperienceBooking(ExperienceBookingFlow experienceBookingFlow);

    void setCalData(List<CalendarDay> calData, boolean isFirstTime);
}