package com.kooloco.util;

import android.content.Context;
import android.graphics.Color;
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

public class DayOutRangeDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private Context context;

    public DayOutRangeDecorator(Collection<CalendarDay> dates, Context context) {
        this.dates = new HashSet<>(dates);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime().after(day.getCalendar().getTime());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
        view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.greyText_cal)));
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calender_out_range_circle));
    }

}
