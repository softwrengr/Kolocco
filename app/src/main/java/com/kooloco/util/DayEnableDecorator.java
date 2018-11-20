package com.kooloco.util;

import android.content.Context;

import com.kooloco.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by hlink on 18/4/18.
 */

public class DayEnableDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private Context context;

    public DayEnableDecorator(Collection<CalendarDay> dates, Context context) {
        this.dates = new HashSet<>(dates);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return !dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(false);
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calender_ava_circle));
    }

}
