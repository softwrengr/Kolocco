package com.kooloco.ui.expereince.fragment;
/**
 * Created by hlink on 18/4/18.
 */

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.expereince.presenter.DemoCalenderPresenter;
import com.kooloco.ui.expereince.view.DemoCalenderView;
import com.kooloco.util.DayDisableDecorator;
import com.kooloco.util.DayEnableDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by hlink on 8/1/18.
 */

public class DemoCalenderFragment extends BaseFragment<DemoCalenderPresenter, DemoCalenderView> implements DemoCalenderView {

    @BindView(R.id.calendarViewAvailability)
    MaterialCalendarView calendarViewAvailability;

    @Override
    protected int createLayout() {
        return R.layout.demo_cal;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected DemoCalenderView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        calendarViewAvailability.setShowOtherDates(MaterialCalendarView.SHOW_DECORATED_DISABLED);

        ArrayList<CalendarDay> enabledDates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        enabledDates.add(new CalendarDay(Calendar.getInstance().getTime()));

        calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, 3);
        enabledDates.add(new CalendarDay(calendar.getTime()));

        calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, 5);

        enabledDates.add(new CalendarDay(calendar.getTime()));


        calendarViewAvailability.addDecorator(new DayEnableDecorator(enabledDates,getActivity()));

        calendarViewAvailability.addDecorator(new DayDisableDecorator(enabledDates,getActivity()));

    }


}
