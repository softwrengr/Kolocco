package com.kooloco.uilocal.setavability.fragment;
/**
 * Created by hlink44 on 11/10/17.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.SetSpecialAvability;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.setavability.presenter.SetSpecialAvabilitiesPresenter;
import com.kooloco.uilocal.setavability.view.SetSpecialAvabilitiesView;
import com.kooloco.util.TimeConvertUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SetSpecialAvabilitiesFragment extends BaseFragment<SetSpecialAvabilitiesPresenter, SetSpecialAvabilitiesView> implements SetSpecialAvabilitiesView {

    int flag = 1;
    String startDate = "";
    String endDate = "";
    int h = 0;
    int m = 0;
    int s = 0;
    @BindView(R.id.customTextViewStartTime1)
    AppCompatTextView customTextViewStartTime1;
    @BindView(R.id.customTextViewEndTime1)
    AppCompatTextView customTextViewEndTime1;
    @BindView(R.id.textViewDaysNameD)
    AppCompatTextView textViewDaysD;
    @BindView(R.id.checkboxChoose)
    AppCompatCheckBox checkboxChoose;
    Unbinder unbinder;

    @BindView(R.id.linearLayoutRow)
    LinearLayout linearLayoutRow;

    private String selectStartTime, selectEndTime = "";

    @BindView(R.id.calendarViewAvailability)
    MaterialCalendarView calendarViewAvailability;
    @BindView(R.id.textViewDays)
    AppCompatTextView textViewDays;
    @BindView(R.id.textViewMonth)
    AppCompatTextView textViewMonth;

    private String selectDate = "";

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_set_special_availabilities;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SetSpecialAvabilitiesView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        Calendar calendar = Calendar.getInstance();
        calendarViewAvailability.setCurrentDate(calendar);
        calendarViewAvailability.setSelectedDate(calendar);


        selectDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        presenter.getAvability(selectDate);

        textViewDays.setText("" + calendar.get(Calendar.DATE));
        textViewMonth.setText(new SimpleDateFormat("MMM").format(calendar.getTime()));
        textViewDaysD.setText(new SimpleDateFormat("EEE").format(calendar.getTime()));
        calendarViewAvailability.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                textViewDays.setText("" + date.getCalendar().get(Calendar.DATE));
                textViewMonth.setText(new SimpleDateFormat("MMM").format(date.getCalendar().getTime()));
                textViewDaysD.setText(new SimpleDateFormat("EEE").format(date.getCalendar().getTime()));

                //              textViewMonth.setText(date.getMonth());

                checkboxChoose.setChecked(false);

                textViewDaysD.setTextColor(getActivity().getResources().getColor(R.color.black));

                customTextViewEndTime1.setText("");
                customTextViewStartTime1.setText("");
                selectStartTime = "";
                selectEndTime = "";


                selectDate = new SimpleDateFormat("yyyy-MM-dd").format(date.getDate().getTime());

                new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

                presenter.getAvability(selectDate);

            }
        });
    }


    public void setTime(boolean isStratTime, AppCompatTextView appCompatTextViewStrat, AppCompatTextView appCompatTextViewEnd) {
        if (isStratTime) {
            flag = 1;
            openTimePicker(flag, appCompatTextViewStrat, appCompatTextViewEnd);
        } else {
            if (appCompatTextViewStrat.getText().toString().isEmpty()) {
                showMessage(getActivity().getResources().getString(R.string.valid_opening_time));
            } else {
                selectStartTime = appCompatTextViewStrat.getText().toString();
                flag = 2;
                openTimePicker(flag, appCompatTextViewStrat, appCompatTextViewEnd);
            }
        }
    }

    public void openTimePicker(final int flag, final AppCompatTextView appCompatTextViewStrat, final AppCompatTextView appCompatTextViewEnd) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                h = hourOfDay;
                m = minute;
                s = second;

                String formatedDate = "";
                String formatedDateP = "";
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
                Date dt;
                String time = "";

                try {
                    time = "" + hourOfDay + ":" + minute + ":" + "00";

                    dt = sdf.parse(time);
                    formatedDate = sdfs.format(dt);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //It is used to add two hours
                try {
                    time = "" + hourOfDay + ":" + minute + ":" + "00";
                    dt = sdf.parse(time);

                    Calendar calendarTemp = Calendar.getInstance();
                    calendarTemp.setTime(dt);
                    calendarTemp.add(Calendar.HOUR, 2);
                    dt = calendarTemp.getTime();

                    formatedDateP = sdfs.format(dt);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (flag == 1) {
                    startDate = time;
                    selectStartTime = formatedDate;
                    selectEndTime = formatedDateP;

                    appCompatTextViewStrat.setText(selectStartTime);
                    appCompatTextViewEnd.setText(selectEndTime);


                } else if (flag == 2) {
                    endDate = time;
                    selectEndTime = formatedDate;
                    appCompatTextViewEnd.setText(selectEndTime);
                }

            }
        };

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd;
        if (!DateFormat.is24HourFormat(getActivity())) {
            //12 hrs format
            tpd = TimePickerDialog.newInstance(
                    onTimeSetListener,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE), false);
            tpd.setMaxTime(21, 29, 00);
        } else {
            //24 hrs format
            tpd = TimePickerDialog.newInstance(onTimeSetListener,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE), true);
            tpd.setMaxTime(21, 29, 00);
        }
        if (flag == 2) {

            Date date = timeCastAdd(selectStartTime);

            //  tpd.setMinTime();
            if (m >= 30) {

                h = h + 1;
            } else {

            }
            tpd.setMaxTime(23, 29, 00);
            tpd.setMinTime(date.getHours(), date.getMinutes(), 00);
        }
        tpd.setAccentColor(R.color.colorAccent);
        tpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        tpd.setAccentColor(getActivity().getResources().getColor(R.color.colorAccent));
    }

    private Date timeCastAdd(String timeConvert) {
        java.text.DateFormat timeFormater = new SimpleDateFormat("hh:mm a"); //HH for hour of the day (0 - 23)
        try {
            Date time = timeFormater.parse(timeConvert);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.MINUTE, 120);
            time = calendar.getTime();
            java.text.DateFormat timeOutput = new SimpleDateFormat("HH:mm");
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @OnClick({R.id.imageViewBack, R.id.customTextViewStartTime1, R.id.customTextViewEndTime1, R.id.linearLayoutDayOff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.customTextViewStartTime1:
                if (!checkboxChoose.isChecked())
                    setTime(true, customTextViewStartTime1, customTextViewEndTime1);
                break;
            case R.id.customTextViewEndTime1:
                if (!checkboxChoose.isChecked()) {
                    setTime(false, customTextViewStartTime1, customTextViewEndTime1);
                }
                break;
            case R.id.linearLayoutDayOff:
                checkboxChoose.setChecked(!checkboxChoose.isChecked());
                linearLayoutRow.setAlpha(checkboxChoose.isChecked() ? 0.75f : 1f);
                textViewDaysD.setTextColor(!checkboxChoose.isChecked() ? getActivity().getResources().getColor(R.color.black) : getActivity().getResources().getColor(R.color.greyText));

                if (checkboxChoose.isChecked()) {
                    customTextViewEndTime1.setText("");
                    customTextViewStartTime1.setText("");
                    selectStartTime = "";
                    selectEndTime = "";
                }
                break;
        }
    }


    @OnClick(R.id.buttonSave)
    public void onViewClicked() {
        if (checkboxChoose.isChecked()) {
            presenter.callWs(selectDate, "00:00:00", "00:00:00", "1");
        } else {
            if (customTextViewStartTime1.getText().toString().isEmpty()) {
                showMessage(getString(R.string.select_st_end));
                return;
            }
            presenter.callWs(selectDate, TimeConvertUtils.dateTimeConvertLocalToLocal(customTextViewStartTime1.getText().toString().trim(), "hh:mm a", "HH:mm:ss"), TimeConvertUtils.dateTimeConvertLocalToLocal(customTextViewEndTime1.getText().toString().trim(), "hh:mm a", "HH:mm:ss"), "0");
        }
    }

    @Override
    public void setData(SetSpecialAvability data) {
        if (data.getIsAvailable().equalsIgnoreCase("1")) {
            checkboxChoose.setChecked(true);

            linearLayoutRow.setAlpha(checkboxChoose.isChecked() ? 0.75f : 1f);
            textViewDaysD.setTextColor(!checkboxChoose.isChecked() ? getActivity().getResources().getColor(R.color.black) : getActivity().getResources().getColor(R.color.greyText));

            if (checkboxChoose.isChecked()) {
                customTextViewEndTime1.setText("");
                customTextViewStartTime1.setText("");
                selectStartTime = "";
                selectEndTime = "";
            }

        } else {
            checkboxChoose.setChecked(false);
            linearLayoutRow.setAlpha(checkboxChoose.isChecked() ? 0.75f : 1f);
            textViewDaysD.setTextColor(!checkboxChoose.isChecked() ? getActivity().getResources().getColor(R.color.black) : getActivity().getResources().getColor(R.color.greyText));

            customTextViewStartTime1.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(data.getStartTime(), "HH:mm:ss", "hh:mm a"));

            customTextViewEndTime1.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(data.getEndTime(), "HH:mm:ss", "hh:mm a"));
        }
    }
}
