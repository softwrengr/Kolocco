package com.kooloco.util;

import android.content.Context;
import android.text.style.ForegroundColorSpan;

import com.kooloco.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by hlink on 18/4/18.
 */

public class DayMaximumDecorator implements DayViewDecorator {

    Calendar calendarMaxMonth;
    private Context context;

    public DayMaximumDecorator(Calendar calendarMaxMonth, Context context) {
        this.calendarMaxMonth = calendarMaxMonth;
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return calendarMaxMonth.getTime().before(day.getDate());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(false);
        view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.greyText_cal)));
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calender_out_range_circle));

        //view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calender_out_range_circle));
    }

}
