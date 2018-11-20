package com.kooloco.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;

import com.kooloco.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by hlink on 18/4/18.
 */

public class DayDisableDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private Context context;

    public DayDisableDecorator(Collection<CalendarDay> dates, Context context) {
        this.dates = new HashSet<>(dates);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.setDaysDisabled(true);
        view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)));
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calender_dis_circle));

        view.setSelectionDrawable(context.getResources().getDrawable(R.drawable.calender_dis_circle_select));

    }

}
