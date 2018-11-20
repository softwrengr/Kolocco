package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 5/10/17.
 */

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.SetAvailability;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.presenter.SetAvailabilitiesPresenter;
import com.kooloco.uilocal.addservice.view.SetAvailabilitiesView;
import com.kooloco.util.TimeConvertUtils;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SetAvailabilitiesFragment extends BaseFragment<SetAvailabilitiesPresenter, SetAvailabilitiesView> implements SetAvailabilitiesView {

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
    @BindView(R.id.customTextViewStartTime2)
    AppCompatTextView customTextViewStartTime2;
    @BindView(R.id.customTextViewEndTime2)
    AppCompatTextView customTextViewEndTime2;
    @BindView(R.id.customTextViewStartTime3)
    AppCompatTextView customTextViewStartTime3;
    @BindView(R.id.customTextViewEndTime3)
    AppCompatTextView customTextViewEndTime3;
    @BindView(R.id.customTextViewStartTime4)
    AppCompatTextView customTextViewStartTime4;
    @BindView(R.id.customTextViewEndTime4)
    AppCompatTextView customTextViewEndTime4;
    @BindView(R.id.customTextViewStartTime5)
    AppCompatTextView customTextViewStartTime5;
    @BindView(R.id.customTextViewEndTime5)
    AppCompatTextView customTextViewEndTime5;
    @BindView(R.id.customTextViewStartTime6)
    AppCompatTextView customTextViewStartTime6;
    @BindView(R.id.customTextViewEndTime6)
    AppCompatTextView customTextViewEndTime6;
    @BindView(R.id.customTextViewStartTime7)
    AppCompatTextView customTextViewStartTime7;
    @BindView(R.id.customTextViewEndTime7)
    AppCompatTextView customTextViewEndTime7;

    private String selectStartTime, selectEndTime = "";

    @Inject
    Session session;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_set_availabilities;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SetAvailabilitiesView createView() {
        return this;
    }

    @Override
    protected void bindData() {

    }

    @OnClick({R.id.imageViewBack, R.id.customTextViewStartTime1, R.id.customTextViewEndTime1, R.id.customTextViewStartTime2, R.id.customTextViewEndTime2, R.id.customTextViewStartTime3, R.id.customTextViewEndTime3, R.id.customTextViewStartTime4, R.id.customTextViewEndTime4, R.id.customTextViewStartTime5, R.id.customTextViewEndTime5, R.id.customTextViewStartTime6, R.id.customTextViewEndTime6, R.id.customTextViewStartTime7, R.id.customTextViewEndTime7, R.id.buttonNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.customTextViewStartTime1:
                setTime(true, customTextViewStartTime1, customTextViewEndTime1);
                break;
            case R.id.customTextViewEndTime1:
                setTime(false, customTextViewStartTime1, customTextViewEndTime1);
                break;
            case R.id.customTextViewStartTime2:
                setTime(true, customTextViewStartTime2, customTextViewEndTime2);
                break;
            case R.id.customTextViewEndTime2:
                setTime(false, customTextViewStartTime2, customTextViewEndTime2);
                break;
            case R.id.customTextViewStartTime3:
                setTime(true, customTextViewStartTime3, customTextViewEndTime3);
                break;
            case R.id.customTextViewEndTime3:
                setTime(false, customTextViewStartTime3, customTextViewEndTime3);
                break;
            case R.id.customTextViewStartTime4:
                setTime(true, customTextViewStartTime4, customTextViewEndTime4);
                break;
            case R.id.customTextViewEndTime4:
                setTime(false, customTextViewStartTime4, customTextViewEndTime4);
                break;
            case R.id.customTextViewStartTime5:
                setTime(true, customTextViewStartTime5, customTextViewEndTime5);
                break;
            case R.id.customTextViewEndTime5:
                setTime(false, customTextViewStartTime5, customTextViewEndTime5);
                break;
            case R.id.customTextViewStartTime6:
                setTime(true, customTextViewStartTime6, customTextViewEndTime6);
                break;
            case R.id.customTextViewEndTime6:
                setTime(false, customTextViewStartTime6, customTextViewEndTime6);
                break;
            case R.id.customTextViewStartTime7:
                setTime(true, customTextViewStartTime7, customTextViewEndTime7);
                break;
            case R.id.customTextViewEndTime7:
                setTime(false, customTextViewStartTime7, customTextViewEndTime7);
                break;
            case R.id.buttonNext:
                presenter.callWs(getSetAvailability());
                break;
        }
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

    private List<SetAvailability> getSetAvailability() {
        List<SetAvailability> setAvailabilities = new ArrayList<>();

        SetAvailability setAvailabilityMon = setAvailability(customTextViewStartTime1, customTextViewEndTime1, "Mon");
        if (setAvailabilityMon != null) {
            setAvailabilities.add(setAvailabilityMon);
        }

        SetAvailability setAvailabilityTue = setAvailability(customTextViewStartTime2, customTextViewEndTime2, "Tue");
        if (setAvailabilityTue != null) {
            setAvailabilities.add(setAvailabilityTue);
        }

        SetAvailability setAvailabilityWed = setAvailability(customTextViewStartTime3, customTextViewEndTime3, "Wed");
        if (setAvailabilityWed != null) {
            setAvailabilities.add(setAvailabilityWed);
        }

        SetAvailability setAvailabilityThu = setAvailability(customTextViewStartTime4, customTextViewEndTime4, "Thu");
        if (setAvailabilityThu != null) {
            setAvailabilities.add(setAvailabilityThu);
        }

        SetAvailability setAvailabilityFri = setAvailability(customTextViewStartTime5, customTextViewEndTime5, "Fri");
        if (setAvailabilityFri != null) {
            setAvailabilities.add(setAvailabilityFri);
        }

        SetAvailability setAvailabilitySat = setAvailability(customTextViewStartTime6, customTextViewEndTime6, "Sat");
        if (setAvailabilitySat != null) {
            setAvailabilities.add(setAvailabilitySat);
        }

        SetAvailability setAvailabilitySun = setAvailability(customTextViewStartTime7, customTextViewEndTime7, "Sun");
        if (setAvailabilitySun != null) {
            setAvailabilities.add(setAvailabilitySun);
        }

        return setAvailabilities;
    }

    private SetAvailability setAvailability(AppCompatTextView appCompatTextViewStart, AppCompatTextView appCompatTextViewEnd, String dayName) {
        if (!appCompatTextViewStart.getText().toString().isEmpty() && !appCompatTextViewEnd.getText().toString().isEmpty()) {
            SetAvailability setAvailabilit = new SetAvailability();
            setAvailabilit.setDay(dayName);
            setAvailabilit.setStartTime(TimeConvertUtils.dateTimeConvertLocalToLocal(appCompatTextViewStart.getText().toString(), "hh:mm a", "HH:mm:ss"));
            setAvailabilit.setEndTime(TimeConvertUtils.dateTimeConvertLocalToLocal(appCompatTextViewEnd.getText().toString(), "hh:mm a", "HH:mm:ss"));
            setAvailabilit.setUserId(session.getUser().getId());
            return setAvailabilit;
        } else {
            return null;
        }
    }


    @OnClick({R.id.imageViewClose1, R.id.imageViewClose2, R.id.imageViewClose3, R.id.imageViewClose4, R.id.imageViewClose5, R.id.imageViewClose6, R.id.imageViewClose7})
    public void onViewClickedClose(View view) {
        switch (view.getId()) {
            case R.id.imageViewClose1:
                clearValue(customTextViewStartTime1, customTextViewEndTime1);
                break;
            case R.id.imageViewClose2:
                clearValue(customTextViewStartTime2, customTextViewEndTime2);
                break;
            case R.id.imageViewClose3:
                clearValue(customTextViewStartTime3, customTextViewEndTime3);
                break;
            case R.id.imageViewClose4:
                clearValue(customTextViewStartTime4, customTextViewEndTime4);
                break;
            case R.id.imageViewClose5:
                clearValue(customTextViewStartTime5, customTextViewEndTime5);
                break;
            case R.id.imageViewClose6:
                clearValue(customTextViewStartTime6, customTextViewEndTime6);
                break;
            case R.id.imageViewClose7:
                clearValue(customTextViewStartTime7, customTextViewEndTime7);
                break;
        }
    }

    private void clearValue(AppCompatTextView appCompatTextViewStrat, AppCompatTextView appCompatTextViewEnd) {
        if (appCompatTextViewStrat != null) {
            appCompatTextViewStrat.setText("");
        }
        if (appCompatTextViewEnd != null) {
            appCompatTextViewEnd.setText("");
        }

    }
}
