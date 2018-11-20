package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 16/4/18.
 */

import android.app.Dialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.SchedulePrice;
import com.kooloco.model.SchedulePriceData;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.adapter.ScheduleAndPriceAdapter;
import com.kooloco.uilocal.expereince.adapter.ScheduleAndPriceDayAdapter;
import com.kooloco.uilocal.expereince.presenter.ScheduleAndPricePresenter;
import com.kooloco.uilocal.expereince.view.ScheduleAndPriceView;
import com.kooloco.util.InputFilterMinMax;
import com.kooloco.util.InputFilterMinMaxDouble;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.Validator;
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

public class ScheduleAndPriceFragment extends BaseFragment<ScheduleAndPricePresenter, ScheduleAndPriceView> implements ScheduleAndPriceView {

    @BindView(R.id.recyclerDays)
    RecyclerView recyclerDays;
    @BindView(R.id.textViewTitle)
    AppCompatTextView textViewTitle;
    @BindView(R.id.recyclerSlots)
    RecyclerView recyclerSlots;
    List<SchedulePrice> schedulePrices;

    ScheduleAndPriceAdapter scheduleAndPriceAdapter;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    boolean isEdit;

    List<SchedulePrice> schedulePricesDays;

    List<SchedulePrice> schedulePricesDaysFull;

    List<SchedulePrice> schedulePricesList;

    SchedulePriceData schedulePriceData;

    Dialog showTimeDialog;

    int selectPosition = 0;

    int flag = 1;
    String startDate = "";
    String endDate = "";
    int h = 0;
    int m = 0;
    int s = 0;
    @BindView(R.id.editTextValuePart)
    AppCompatEditText editTextValuePart;
    private String selectStartTime, selectEndTime = "";
    String sloatId = "";


    @Inject
    Validator validator;
    private String expId = "";

    List<String> fillSlot;

    ScheduleAndPriceDayAdapter scheduleAndPriceDayAdapter;

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_scuedule_price;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ScheduleAndPriceView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        buttonNext.setText(isEdit ? getActivity().getResources().getString(R.string.button_done) : getActivity().getResources().getString(R.string.button_next));

        editTextValuePart.setFilters(new InputFilter[]{new InputFilterMinMax(1, 10000)});

        fillSlot = new ArrayList<>();

        schedulePriceData = new SchedulePriceData();

        schedulePrices = new ArrayList<>();

        schedulePricesDays = new ArrayList<>();

        schedulePricesDays.addAll(Temp.schedulePriceDay());

        schedulePricesDaysFull = new ArrayList<>();

        schedulePricesDaysFull.addAll(Temp.schedulePriceDayFull());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE");

        String dayName = sdf.format(new Date());

        int tempI = 0;
        for (SchedulePrice dataTemp : schedulePricesDays) {

            if (dayName.equalsIgnoreCase(dataTemp.getDay())) {
                selectPosition = tempI;
                break;
            }

            tempI++;
        }

        setTitle();
        scheduleAndPriceDayAdapter = new ScheduleAndPriceDayAdapter(getActivity(), schedulePricesDays, fillSlot, selectPosition, new ScheduleAndPriceDayAdapter.CallBack() {
            @Override
            public void onSelect(SchedulePrice schedulePrice, int position) {
                if (selectPosition != position) {
                    selectPosition = position;
                    ///Set Data
                    setTitle();
                    //getData();
                    setDataSelect();
                }
            }
        });

        recyclerDays.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerDays.setAdapter(scheduleAndPriceDayAdapter);

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
                        presenter.callWsDelete(schedulePrice);
                    }
                });

            }
        });

        recyclerSlots.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerSlots.setAdapter(scheduleAndPriceAdapter);


        getData();

    }

    @OnClick({R.id.imageViewBack, R.id.linearLayoutAddPicture, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
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
            case R.id.buttonNext:
                try {
                    validator.submit(editTextValuePart).checkEmpty().errorMessage(R.string.val_duration_part).check();

                    if (presenter.checkSchedulePriceData(schedulePriceData)) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_set_one_day_schedule));
                        throw applicationException;
                    }

                    presenter.callWsMaxParticipants(expId, editTextValuePart.getText().toString(), isEdit);

                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
                break;
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
    }

    @Override
    public void setData(SchedulePriceData data) {
        schedulePriceData = data;
        setDataSelect();
        setDataDay();
    }

    @Override
    public void setDataParticipants(String maximumParticipant) {
        if (editTextValuePart != null) {
            editTextValuePart.setText(maximumParticipant);
        }
    }

    @Override
    public void onStart() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onStart();
    }

    @Override
    public void onStop() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onStop();
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

        textViewDaysNameD.setText(schedulePricesDays.get(selectPosition).getDay());

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
                        showDialogDeleteWithAnimation(sloatId.isEmpty() ? getString(R.string.are_you_sure_want_to_add_another_time_slots) : getString(R.string.are_you_sure_you_want_to_edit), getString(R.string.add_multiday_exp_schdule), isSuccess -> {
                            if (isSuccess) {
                                presenter.callWsSetAvability(sloatId, expId, TimeConvertUtils.dateTimeConvertLocalToLocalDay(schedulePricesDaysFull.get(selectPosition).getDay(), "EEEE", "EEE"), finalIsNumberOfDay, finalIsMultiPalDay, finalStartTime, finalEndTime, editTextValuePrice.getText().toString().trim(), (isScuess, message) -> {
                                    if (isScuess) {
                                        showTimeDialog.dismiss();
                                    } else {
                                        showSnackBar(editTextValueMultiDay, message);
                                    }
                                });
                            }
                        });

                    } else {
                        presenter.callWsSetAvability(sloatId, expId, TimeConvertUtils.dateTimeConvertLocalToLocalDay(schedulePricesDaysFull.get(selectPosition).getDay(), "EEEE", "EEE"), isNumberOfDay, isMultiPalDay, startTime, endTime, editTextValuePrice.getText().toString().trim(), (isScuess, message) -> {
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

            tpd.setSelectableTimes(generateTimepoints(23.25, 15));

            tpd.setMaxTime(23, 15, 00);
        } else {
            //24 hrs format
            tpd = TimePickerDialog.newInstance(onTimeSetListener,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE), true);

            tpd.setSelectableTimes(generateTimepoints(23.25, 15));

            tpd.setMaxTime(23, 15, 00);
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
            tpd.setMaxTime(23, 45, 00);

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
        textViewTitle.setText(schedulePricesDaysFull.get(selectPosition).getDay() + " " + getActivity().getResources().getString(R.string.days_availabilities_and_price));
    }


    private void getData() {
        presenter.callWs(expId, TimeConvertUtils.dateTimeConvertLocalToLocalDay(schedulePricesDaysFull.get(selectPosition).getDay(), "EEEE", "EEE"));
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

    private void setDataSelect() {

        schedulePrices.clear();
        schedulePrices.addAll(presenter.getSchedulrPriceList(TimeConvertUtils.dateTimeConvertLocalToLocalDay(schedulePricesDaysFull.get(selectPosition).getDay(), "EEEE", "EEE"), schedulePriceData));

        if (scheduleAndPriceAdapter != null) {
            scheduleAndPriceAdapter.notifyDataSetChanged();
        }
    }

    private void setDataDay() {
        fillSlot.clear();
        fillSlot.addAll(presenter.getSelectDayNames(schedulePriceData));

        if (scheduleAndPriceDayAdapter != null) {
            scheduleAndPriceDayAdapter.notifyDataSetChanged();
        }

    }
}
