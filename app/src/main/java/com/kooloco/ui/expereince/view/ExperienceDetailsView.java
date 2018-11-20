package com.kooloco.ui.expereince.view;

import com.kooloco.model.ExpDetails;
import com.kooloco.model.Review;
import com.kooloco.ui.base.RootView;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;

/**
 * Created by hlink on 18/4/18.
 */

public interface ExperienceDetailsView extends RootView {

    void setData(ExpDetails expDetails);

    void setDataRating(List<Review> list, int pageRate, int count);

    void setIsLocalApp(boolean isLocalApp);

    void setExpId(String expId);

    void setDataCal(ExpDetails data);

    void setCalData(List<CalendarDay> calendarDays);
}