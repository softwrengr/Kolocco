package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 17/4/18.
 */

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.SchedulePrice;
import com.kooloco.model.Time;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.adapter.ScheduleAndPriceAdapter;
import com.kooloco.uilocal.expereince.adapter.ScheduleAndPriceDayAdapter;
import com.kooloco.uilocal.expereince.presenter.ExperienceSetSpecialAvailabilitesPresenter;
import com.kooloco.uilocal.expereince.view.ExperienceSetSpecialAvailabilitesView;
import com.kooloco.util.InputFilterMinMax;
import com.kooloco.util.InputFilterMinMaxDouble;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.Validator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.squareup.picasso.Picasso;
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
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceSetSpecialAvailabilitesFragment extends BaseFragment<ExperienceSetSpecialAvailabilitesPresenter, ExperienceSetSpecialAvailabilitesView> implements ExperienceSetSpecialAvailabilitesView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageView)
    PorterShapeImageView imageView;
    @BindView(R.id.customTextViewTitle)
    AppCompatTextView customTextViewTitle;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.textViewRateCount)
    AppCompatTextView textViewRateCount;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.imageViewExp)
    ImageView imageViewExp;
    @BindView(R.id.textViewExpPrice)
    AppCompatTextView textViewExpPrice;
    @BindView(R.id.linearLayoutExpRoot)
    LinearLayout linearLayoutExpRoot;
    @BindView(R.id.calendarViewAvailability)
    MaterialCalendarView calendarViewAvailability;
    @BindView(R.id.textViewDays)
    AppCompatTextView textViewDays;
    @BindView(R.id.textViewMonth)
    AppCompatTextView textViewMonth;
    @BindView(R.id.textViewTitle)
    AppCompatTextView textViewTitle;
    @BindView(R.id.recyclerSlots)
    RecyclerView recyclerSlots;
    @BindView(R.id.imageViewAddSport)
    ImageView imageViewAddSport;
    @BindView(R.id.linearLayoutAddPicture)
    LinearLayout linearLayoutAddPicture;
    @BindView(R.id.checkboxChoose)
    AppCompatCheckBox checkboxChoose;
    @BindView(R.id.customEditTextSetName)
    AppCompatTextView customEditTextSetName;
    @BindView(R.id.linearLayoutDayOff)
    LinearLayout linearLayoutDayOff;
    @BindView(R.id.buttonSave)
    AppCompatButton buttonSave;
    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;


    private String selectStartTime, selectEndTime = "";
    private String selectDate = "";
    int flag = 1;
    String startDate = "";
    String endDate = "";
    int h = 0;
    int m = 0;
    int s = 0;
    List<SchedulePrice> schedulePrices;
    private ScheduleAndPriceAdapter scheduleAndPriceAdapter;

    Dialog showTimeDialog;

    String sloatId = "";

    ExpereinceNew expereinceNew;

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_set_special_availabilities;
    }

    @Inject
    Validator validator;


    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceSetSpecialAvailabilitesView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        linearLayoutExpRoot.setClickable(false);
        setDataExperence(expereinceNew);

        schedulePrices = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendarViewAvailability.setCurrentDate(calendar);
        calendarViewAvailability.setSelectedDate(calendar);


        buttonSave.setVisibility(View.INVISIBLE);

        selectDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());


        textViewDays.setText("" + calendar.get(Calendar.DATE));
        textViewMonth.setText(new SimpleDateFormat("MMM").format(calendar.getTime()));


        calendarViewAvailability.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                textViewDays.setText("" + date.getCalendar().get(Calendar.DATE));
                textViewMonth.setText(new SimpleDateFormat("MMM").format(date.getCalendar().getTime()));


                selectDate = new SimpleDateFormat("yyyy-MM-dd").format(date.getDate().getTime());
                new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                setTitle();
                getData();


            }
        });


        scheduleAndPriceAdapter = new ScheduleAndPriceAdapter(getActivity(), schedulePrices, new ScheduleAndPriceAdapter.CallBack() {
            @Override
            public void onClickEdit(SchedulePrice schedulePrice) {
                showDialogDeleteWithAnimation(getString(R.string.are_you_sure_you_want_to_edit), getString(R.string.edit_exp_schdule), isSuccess -> {
                    if (isSuccess) {
                        showSchduleDialog(schedulePrice);
                    }
                });
            }

            @Override
            public void onClickDelete(SchedulePrice schedulePrice) {

                showDialogDeleteWithAnimation(getString(R.string.delete_exp_schdule), isSuccess -> {
                    if (isSuccess) {
                        presenter.callWsDelete(schedulePrice, selectDate);
                    }
                });

            }
        });

        recyclerSlots.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerSlots.setAdapter(scheduleAndPriceAdapter);
        setTitle();
        getData();
    }

    public void showSchduleDialog(SchedulePrice data) {

        if (showTimeDialog != null) {
            if (showTimeDialog.isShowing()) {
                showTimeDialog.dismiss();
            }
        }


        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_schedule_price, null, false);

        LinearLayout linearLayoutMultiPale = view.findViewById(R.id.linearLayoutMultiPale);

        LinearLayout linearLayoutDays = view.findViewById(R.id.linearLayoutDays);

        AppCompatButton buttonOk = view.findViewById(R.id.buttonOk);

        CheckBox checkBoxSelectRules = view.findViewById(R.id.checkBoxSelectRules);

        AppCompatTextView customTextViewStartTime = view.findViewById(R.id.customTextViewStartTime);

        AppCompatTextView customTextViewEndTime = view.findViewById(R.id.customTextViewEndTime);

        AppCompatTextView editTextValueMultiStartTime = view.findViewById(R.id.editTextValueMultiStartTime);

        AppCompatEditText editTextValueMultiDay = view.findViewById(R.id.editTextValueMultiDay);

        AppCompatEditText editTextValuePrice = view.findViewById(R.id.editTextValuePrice);

        // AppCompatEditText editTextValuePart = view.findViewById(R.id.editTextValuePart);

        AppCompatTextView textViewDaysNameD = view.findViewById(R.id.textViewDaysNameD);

        textViewDaysNameD.setText(getDayNameSelected());

        editTextValuePrice.setHint(BaseActivity.currency + " 0.0");

        editTextValueMultiStartTime.setOnClickListener(view1 -> {
            setTime(true, editTextValueMultiStartTime, customTextViewEndTime);
        });
        customTextViewStartTime.setOnClickListener(view1 -> {
            setTime(true, customTextViewStartTime, customTextViewEndTime);
        });
        customTextViewEndTime.setOnClickListener(view1 -> {
            setTime(false, customTextViewStartTime, customTextViewEndTime);
        });

        editTextValueMultiDay.setFilters(new InputFilter[]{new InputFilterMinMax(1, 10000)});

        editTextValuePrice.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(0.00, 1000000.00)});

        linearLayoutDays.setVisibility(checkBoxSelectRules.isChecked() ? View.GONE : View.VISIBLE);
        linearLayoutMultiPale.setVisibility(checkBoxSelectRules.isChecked() ? View.VISIBLE : View.GONE);

        checkBoxSelectRules.setOnClickListener(view1 -> {
            linearLayoutDays.setVisibility(checkBoxSelectRules.isChecked() ? View.GONE : View.VISIBLE);
            linearLayoutMultiPale.setVisibility(checkBoxSelectRules.isChecked() ? View.VISIBLE : View.GONE);
            customTextViewStartTime.setText("");
            customTextViewEndTime.setText("");
            selectStartTime = "";
            selectEndTime = "";
            editTextValueMultiStartTime.setText("");
            editTextValueMultiDay.setText("");
        });


        ImageView imageViewClose = view.findViewById(R.id.imageViewClose);

        imageViewClose.setOnClickListener(view1 -> {
            customTextViewStartTime.setText("");
            customTextViewEndTime.setText("");
            selectStartTime = "";
            selectEndTime = "";

        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (checkBoxSelectRules.isChecked()) {
                        validator.submit(editTextValueMultiDay).checkEmpty().errorMessage(R.string.val_duration_day).check();
                        validator.submit(editTextValueMultiStartTime).checkEmpty().errorMessage(R.string.val_duration_strat_time).check();
                    } else {
                        validator.submit(customTextViewStartTime).checkEmpty().errorMessage(R.string.val_duration_start_end_time).check();
                    }
                    validator.submit(editTextValuePrice).checkEmpty().errorMessage(R.string.val_duration_price).check();

                    String isNumberOfDay = "0";
                    String isMultiPalDay = "0";
                    String startTime = "00:00:00";
                    String endTime = "00:00:00";

                    if (checkBoxSelectRules.isChecked()) {
                        isMultiPalDay = "1";
                        isNumberOfDay = editTextValueMultiDay.getText().toString();
                    }


                    if (!checkBoxSelectRules.isChecked()) {
                        endTime = TimeConvertUtils.dateTimeConvertLocalToLocal(customTextViewEndTime.getText().toString(), "hh:mm a", "HH:mm:ss");
                        startTime = TimeConvertUtils.dateTimeConvertLocalToLocal(customTextViewStartTime.getText().toString(), "hh:mm a", "HH:mm:ss");
                    } else {
                        startTime = TimeConvertUtils.dateTimeConvertLocalToLocal(editTextValueMultiStartTime.getText().toString(), "hh:mm a", "HH:mm:ss");
                    }


                    if (checkBoxSelectRules.isChecked()) {
                        String finalEndTime = endTime;
                        String finalStartTime = startTime;
                        String finalIsMultiPalDay = isMultiPalDay;
                        String finalIsNumberOfDay = isNumberOfDay;
                        showDialogDeleteWithAnimation(sloatId.isEmpty() ? getString(R.string.are_you_sure_you_want_to_add) : getString(R.string.are_you_sure_you_want_to_edit), getString(R.string.add_multiday_exp_schdule), isSuccess -> {
                            if (isSuccess) {
                                presenter.callWsSetAvability(sloatId, expereinceNew.getId(), TimeConvertUtils.dateTimeConvertLocalToLocalDay(getDayNameSelected(), "EEEE", "EEE"), selectDate, "0", finalIsNumberOfDay, finalIsMultiPalDay, finalStartTime, finalEndTime, editTextValuePrice.getText().toString().trim(), (isScuess, message) -> {
                                    if (isScuess) {
                                        showTimeDialog.dismiss();
                                    } else {
                                        showSnackBar(editTextValueMultiDay, message);
                                    }
                                });
                            }
                        });

                    } else {
                        presenter.callWsSetAvability(sloatId, expereinceNew.getId(), TimeConvertUtils.dateTimeConvertLocalToLocalDay(getDayNameSelected(), "EEEE", "EEE"), selectDate, "0", isNumberOfDay, isMultiPalDay, startTime, endTime, editTextValuePrice.getText().toString().trim(), (isScuess, message) -> {
                            if (isScuess) {
                                showTimeDialog.dismiss();
                            } else {
                                showSnackBar(editTextValueMultiDay, message);
                            }
                        });

                    }
                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showSnackBar(customTextViewStartTime, e.getMessage());
                }

            }
        });

        showTimeDialog = new Dialog(getActivity());

        showTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        showTimeDialog.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        showTimeDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        showTimeDialog.setContentView(view);

        showTimeDialog.show();


        if (data == null) {
            sloatId = "";
            buttonOk.setText(getActivity().getResources().getString(R.string.button_add));
        } else {
            sloatId = data.getId();
            buttonOk.setText(getActivity().getResources().getString(R.string.button_done));

            if (data.getIsMultipleDay().equalsIgnoreCase("1")) {
                checkBoxSelectRules.setChecked(true);
                editTextValueMultiDay.setText(data.getDurationInDays());
                editTextValueMultiStartTime.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(data.getStartTime(), "HH:mm:ss", "hh:mm a"));
                editTextValuePrice.setText(data.getPrice());

                customTextViewStartTime.setText("");
                customTextViewEndTime.setText("");

            } else {
                checkBoxSelectRules.setChecked(false);
                editTextValueMultiDay.setText("");
                editTextValueMultiStartTime.setText("");

                customTextViewStartTime.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(data.getStartTime(), "HH:mm:ss", "hh:mm a"));
                customTextViewEndTime.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(data.getEndTime(), "HH:mm:ss", "hh:mm a"));
                editTextValuePrice.setText(data.getPrice());
            }
            linearLayoutDays.setVisibility(checkBoxSelectRules.isChecked() ? View.GONE : View.VISIBLE);
            linearLayoutMultiPale.setVisibility(checkBoxSelectRules.isChecked() ? View.VISIBLE : View.GONE);
        }


    }


    public void setTime(boolean isStratTime, AppCompatTextView appCompatTextViewStrat, AppCompatTextView appCompatTextViewEnd) {
        if (isStratTime) {
            flag = 1;
            openTimePicker(flag, appCompatTextViewStrat, appCompatTextViewEnd);
        } else {
            if (appCompatTextViewStrat.getText().toString().isEmpty()) {
                showSnackBar(appCompatTextViewEnd, getActivity().getResources().getString(R.string.valid_opening_time));
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
                    calendarTemp.add(Calendar.MINUTE, 30);
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
            tpd.setMaxTime(23, 29, 00);
            tpd.setSelectableTimes(generateTimepoints(23.25, 15));

        } else {
            //24 hrs format
            tpd = TimePickerDialog.newInstance(onTimeSetListener,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE), true);

            tpd.setSelectableTimes(generateTimepoints(23.25, 15));

            tpd.setMaxTime(23, 29, 00);
        }
        if (flag == 2) {

            Date date = timeCastAdd(selectStartTime);

            //  tpd.setMinTime();
            if (m >= 30) {

                h = h + 1;
            } else {

            }


            double startTime;

            startTime = date.getHours();

            if (date.getMinutes() == 15) {
                startTime = startTime + 0.25;
            } else if (date.getMinutes() == 30) {
                startTime = startTime + 0.50;
            } else if (date.getMinutes() == 45) {
                startTime = startTime + 0.75;
            }


            tpd.setSelectableTimes(generateTimepointsEnds(startTime, 23.75, 15));

            tpd.setMaxTime(23, 59, 00);
            tpd.setMinTime(date.getHours(), date.getMinutes(), 00);
        }
        tpd.setAccentColor(R.color.colorAccent);
        tpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        tpd.setAccentColor(getActivity().getResources().getColor(R.color.colorAccent));
    }

    private Date timeCastAdd(String timeConvert) {
        java.text.DateFormat timeFormater = new SimpleDateFormat("hh:mm a");
        try {
            Date time = timeFormater.parse(timeConvert);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.MINUTE, 30);
            time = calendar.getTime();
            java.text.DateFormat timeOutput = new SimpleDateFormat("HH:mm");
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void setTitle() {
        textViewTitle.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "dd MMM yyyy") + " " + getActivity().getResources().getString(R.string.days_availabilities_and_price));
    }

    @OnClick({R.id.imageViewBack, R.id.linearLayoutDayOff, R.id.linearLayoutAddPicture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.linearLayoutDayOff:

                if (!checkboxChoose.isChecked()) {
                    showDialogDeleteWithAnimation(getString(R.string.are_you_sure_you_want_to_is_not_available), getString(R.string.edit_exp_schdule), isSuccess -> {
                        if (isSuccess) {
                            checkboxChoose.setChecked(!checkboxChoose.isChecked());
                            linearLayoutAddPicture.setAlpha(checkboxChoose.isChecked() ? 0.75f : 1f);
                            linearLayoutAddPicture.setClickable(!checkboxChoose.isChecked());

                            presenter.callWsIsNotAvailable(expereinceNew.getId(), TimeConvertUtils.dateTimeConvertLocalToLocalDay(getDayNameSelected(), "EEEE", "EEE"), selectDate, checkboxChoose.isChecked() ? "1" : "0");

                        }
                    });
                } else {
                    checkboxChoose.setChecked(!checkboxChoose.isChecked());
                    linearLayoutAddPicture.setAlpha(checkboxChoose.isChecked() ? 0.75f : 1f);
                    linearLayoutAddPicture.setClickable(!checkboxChoose.isChecked());

                    presenter.callWsIsNotAvailable(expereinceNew.getId(), TimeConvertUtils.dateTimeConvertLocalToLocalDay(getDayNameSelected(), "EEEE", "EEE"), selectDate, checkboxChoose.isChecked() ? "1" : "0");
                }

                break;
            case R.id.linearLayoutAddPicture:
                if (isCheckMultiDay()) {
                    showMessage(getString(R.string.error_message_add_info_schdule));
                    return;
                }
                if (schedulePrices.size() == 12) {
                    showMessage(getString(R.string.error_maximum_slots));
                    return;
                }
                showSchduleDialog(null);
                break;
        }
    }


    @OnClick({R.id.imageViewBack, R.id.buttonSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                break;
            case R.id.buttonSave:
                break;
        }
    }


    private void setDataExperence(ExpereinceNew dataExperence) {


        imageView.setVisibility(View.GONE);
        Picasso.with(getActivity()).load(dataExperence.getImage_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageView);

        customTextViewTitle.setText(dataExperence.getTitle());
        customTextViewTitle.setMinLines(1);

        float rate = 0.0f;

        try {
            rate = Float.parseFloat(dataExperence.getRate());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ratingBar.setRating(rate);

        textViewRateCount.setText("(" + dataExperence.getRateCount() + ")");

        if (dataExperence.getCity().isEmpty() && dataExperence.getCountry().isEmpty()) {
            customTextViewLocation.setText(R.string.set_meeting_spot);
        } else {
            customTextViewLocation.setText(dataExperence.getLocation());
        }

        textExpCurrency.setText(BaseActivity.currency);

        textViewExpPrice.setText(dataExperence.getPrice());

        if (dataExperence.getExperience_url().isEmpty()) {
            imageViewExp.setVisibility(View.GONE);
        } else {
            imageViewExp.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(dataExperence.getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageViewExp);
        }

    }


    @Override
    public void setData(List<SchedulePrice> slot) {
        schedulePrices.clear();
        schedulePrices.addAll(slot);

        if (scheduleAndPriceAdapter != null) {
            scheduleAndPriceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setExpNew(ExpereinceNew expNew) {
        this.expereinceNew = expNew;
    }

    @Override
    public void setIsNotAvability(boolean isNotAvability) {
        checkboxChoose.setChecked(isNotAvability);
        linearLayoutAddPicture.setAlpha(checkboxChoose.isChecked() ? 0.75f : 1f);
        linearLayoutAddPicture.setClickable(!checkboxChoose.isChecked());
    }

    private void getData() {
        presenter.callWs(expereinceNew.getId(), TimeConvertUtils.dateTimeConvertLocalToLocalDay(getDayNameSelected(), "EEEE", "EEE"), selectDate);
    }

    private boolean isCheckMultiDay() {
        boolean isCheckMultiDay = false;
        for (SchedulePrice schedulePrice : schedulePrices) {
            if (schedulePrice.getIsMultipleDay().equalsIgnoreCase("1")) {
                isCheckMultiDay = true;
                break;
            }
        }
        return isCheckMultiDay;
    }

    private String getDayNameSelected() {
        return TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "EEE");
    }
}
