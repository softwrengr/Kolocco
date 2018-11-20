package com.kooloco.ui.expereince.fragment;
/**
 * Created by hlink on 21/4/18.
 */

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.SchedulePrice;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.expereince.adapter.ExperienceSelectDateAndTimeAdapter;
import com.kooloco.ui.expereince.presenter.ExperienceSelectDateTimePresenter;
import com.kooloco.ui.expereince.view.ExperienceSelectDateTimeView;
import com.kooloco.util.DayDisableDecorator;
import com.kooloco.util.DayEnableDecorator;
import com.kooloco.util.DayMaximumDecorator;
import com.kooloco.util.DayOutRangeDecorator;
import com.kooloco.util.picaso.CircleTransform;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceSelectDateTimeFragment extends BaseFragment<ExperienceSelectDateTimePresenter, ExperienceSelectDateTimeView> implements ExperienceSelectDateTimeView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTextViewExpTitle)
    AppCompatTextView customTextViewExpTitle;
    @BindView(R.id.calendarViewAvailability)
    MaterialCalendarView calendarViewAvailability;
    @BindView(R.id.textViewTitle)
    AppCompatTextView textViewTitle;
    @BindView(R.id.recyclerSlots)
    RecyclerView recyclerSlots;
    List<SchedulePrice> schedulePrices;
    ExperienceSelectDateAndTimeAdapter experienceSelectDateAndTimeAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ExperienceBookingFlow experienceBookingFlow;
    List<CalendarDay> calendarDays;

    String year = "";
    String selectDate = "";
    Calendar calendarMaximumDate;

    Calendar calendarSelectDate;

    @Override
    protected int createLayout() {
        return R.layout.exp_select_date_time;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceSelectDateTimeView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        calendarDays = new ArrayList<>();

        schedulePrices = new ArrayList<>();
        experienceSelectDateAndTimeAdapter = new ExperienceSelectDateAndTimeAdapter(getActivity(), schedulePrices, schedulePrice -> {
            experienceBookingFlow.setSchedulePrice(schedulePrice);
            experienceBookingFlow.setSelectDate(selectDate);
            presenter.openExpAddParticipants(experienceBookingFlow);

        });

        recyclerSlots.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerSlots.setAdapter(experienceSelectDateAndTimeAdapter);


        Calendar calendar = Calendar.getInstance();

        if (calendarSelectDate == null) {
            calendarSelectDate = Calendar.getInstance();
        }

        //  textViewTitle.setText(new SimpleDateFormat("dd MMMM yyyy").format(calendar.getTime()));

        year = new SimpleDateFormat("yyyy").format(calendar.getTime());

        calendarViewAvailability.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);


        calendarViewAvailability.getSelectedDates();


        // calendarViewAvailability.setSelectedDate(calendar.getTime());

        // calendarViewAvailability.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar calendarMinimumDate = Calendar.getInstance();
        calendarViewAvailability.setMinimumDate(calendarMinimumDate);

        calendarViewAvailability.clearSelection();

        //This code for 87 days
        calendarMaximumDate = Calendar.getInstance();

        calendarMaximumDate.add(Calendar.DATE, 86);

        calendarViewAvailability.setMaximumDataCustom(calendarMaximumDate);


        calendarViewAvailability.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                textViewTitle.setText(new SimpleDateFormat("EEEE, dd MMM yyyy").format(date.getDate().getTime()));

                selectDate = new SimpleDateFormat("yyyy-MM-dd").format(date.getDate().getTime());
                calendarSelectDate.setTimeInMillis(date.getDate().getTime());

                experienceBookingFlow.setSelectDateExp(true);
                experienceBookingFlow.setDateExp(date.getDate());

                presenter.getData(experienceBookingFlow.getExpereinceNew(), selectDate);


            }
        });

        calendarViewAvailability.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                //Booking 87 days
                /*String newYear = new SimpleDateFormat("yyyy").format(date.getDate().getTime());
                if (!year.equalsIgnoreCase(newYear)) {
                    year = new SimpleDateFormat("yyyy").format(date.getDate().getTime());
                    presenter.getDataYear(experienceBookingFlow.getExpereinceNew(), year, false);
                }*/
            }
        });

        new Handler().postDelayed(() -> {
            if (presenter != null) {
                presenter.getDataYear(experienceBookingFlow.getExpereinceNew(), year, true);
                setHeadData();
            }
        }, 500);

        if (experienceBookingFlow.isSelectDateExp()) {
            calendarSelectDate = Calendar.getInstance();
            calendarSelectDate.setTimeInMillis(experienceBookingFlow.getDateExp().getTime());
            selectDate = new SimpleDateFormat("yyyy-MM-dd").format(calendarSelectDate.getTime().getTime());
        }

    }

    @OnClick({R.id.imageViewBack, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonNext:
                //presenter.openExpAddParticipants();
                break;
        }
    }

    @Override
    public void setData(List<SchedulePrice> listSchedulePrice) {
        schedulePrices.clear();
        schedulePrices.addAll(listSchedulePrice);

        if (experienceSelectDateAndTimeAdapter != null) {
            experienceSelectDateAndTimeAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setExperienceBooking(ExperienceBookingFlow experienceBookingFlow) {
        this.experienceBookingFlow = experienceBookingFlow;
    }

    @Override
    public void setCalData(List<CalendarDay> data, boolean isFirstTime) {

        if (calendarDays != null) {
            calendarDays.addAll(data);
        }

        if (calendarViewAvailability == null) {
            return;
        }

        calendarViewAvailability.addDecorator(new DayEnableDecorator(calendarDays, getActivity()));

        calendarViewAvailability.addDecorator(new DayDisableDecorator(calendarDays, getActivity()));

        calendarViewAvailability.addDecorator(new DayOutRangeDecorator(calendarDays, getActivity()));

        calendarViewAvailability.addAllDisableDays(calendarDays);

        if (calendarMaximumDate != null) {
            calendarViewAvailability.addDecorator(new DayMaximumDecorator(calendarMaximumDate, getActivity()));
        }

        if (isFirstTime) {
            if (!selectDate.isEmpty()) {
                selectDate = new SimpleDateFormat("yyyy-MM-dd").format(calendarSelectDate.getTime().getTime());
                presenter.getData(experienceBookingFlow.getExpereinceNew(), selectDate);
                textViewTitle.setText(new SimpleDateFormat("EEEE, dd MMM yyyy").format(calendarSelectDate.getTime().getTime()));
                calendarViewAvailability.setSelectedDate(calendarSelectDate);
                calendarViewAvailability.setCurrentDate(calendarSelectDate);

            }
        }

    }


    private void setHeadData() {
        customTextViewLocalName.setText(experienceBookingFlow.getLocalName());
        Picasso.with(getActivity()).load(experienceBookingFlow.getLocalProfile()).transform(new CircleTransform()).placeholder(R.drawable.user_round).error(R.drawable.user_round).into(imageViewProfile);

        customTextViewExpTitle.setText(experienceBookingFlow.getExpereinceNew().getTitle());
    }


}
