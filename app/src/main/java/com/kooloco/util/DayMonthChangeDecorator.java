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

public class DayMonthChangeDecorator implements DayViewDecorator {
    private CalendarDay calendarDaySelect;
    private Context context;

    public DayMonthChangeDecorator(CalendarDay calendarDay, Context context) {
        this.calendarDaySelect = calendarDay;
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return calendarDaySelect.getMonth() == day.getMonth();
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
        view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.green)));
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calender_out_range_circle));
    }

}
