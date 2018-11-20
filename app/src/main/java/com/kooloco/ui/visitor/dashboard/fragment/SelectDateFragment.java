package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 21/9/17.
 */

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Time;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.SelectDateTimeAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.SelectDatePresenter;
import com.kooloco.ui.visitor.dashboard.view.SelectDateView;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SelectDateFragment extends BaseFragment<SelectDatePresenter, SelectDateView> implements SelectDateView {

    @BindView(R.id.recyclerTimeSlots)
    RecyclerView recyclerTimeSlots;
    Unbinder unbinder;
    @BindView(R.id.selectDateSelectDate)
    AppCompatEditText selectDateSelectDate;
    String dateText = "";

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.customTextViewServiceType)
    AppCompatTextView customTextViewServiceType;
    @BindView(R.id.customTextViewServiceTypeValue)
    AppCompatTextView customTextViewServiceTypeValue;
    @BindView(R.id.customTextViewServiceHour)
    AppCompatTextView customTextViewServiceHour;
    @BindView(R.id.webView)
    WebView webViewHelp;
    @BindView(R.id.imageViewStartTime)
    ImageView imageViewStartTime;
    @BindView(R.id.linearLayoutStartTime)
    LinearLayout linearLayoutStartTime;
    @BindView(R.id.imageViewEndTime)
    ImageView imageViewEndTime;
    @BindView(R.id.linearLayoutEndTime)
    LinearLayout linearLayoutEndTime;
    @BindView(R.id.textViewTitle)
    AppCompatTextView textViewTitle;
    @BindView(R.id.checkboxChoose)
    AppCompatCheckBox checkboxChoose;
    private VisitorBooking visitorBooking;

    List<Time> startTime, endTime;
    Time selectStartTime, selectEndTime;
    private int selectStartIndex = -1, selectEndIndex = -1;

    SelectDateTimeAdapter selectDateTimeEndAdapter;


    @Override
    protected int createLayout() {
        return R.layout.fragment_visitor_select_date;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SelectDateView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        WebSettings webSettings = webViewHelp.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webViewHelp.setWebChromeClient(new WebChromeClient());
        String url = URLFactory.GETWATHER + visitorBooking.getWather();

        webViewHelp.loadUrl(url);

        if (!visitorBooking.getLocalImage().isEmpty()) {
            Picasso.with(getActivity()).load(visitorBooking.getLocalImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }

        customTextViewName.setText(visitorBooking.getLocalName());
        customTextViewServiceType.setText(" ");


        customTextViewServiceTypeValue.setText(visitorBooking.getSportName() + " " + visitorBooking.getExperienceTitle() + " with ");

        customTextViewServiceHour.setText(visitorBooking.getDurationExperience() + " " + getActivity().getResources().getString(R.string.hr));

        Calendar calendar = Calendar.getInstance();

        dateText = new SimpleDateFormat("dd MMMM, yyyy").format(calendar.getTime());
        selectDateSelectDate.setText(new SimpleDateFormat("dd MMMM, yyyy").format(calendar.getTime()));

        visitorBooking.setDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

        //  presenter.getStartTime(visitorBooking);

        startTime = new ArrayList<>();
        endTime = new ArrayList<>();

        //It is used to clear selection
        selectEndTime = null;
        selectStartTime = null;
        selectStartIndex = -1;
        selectEndIndex = -1;

        setStartTimeEndTime(0);

        String text = "" + getActivity().getResources().getString(R.string.duration_let) + "<font color='" + getActivity().getResources().getColor(R.color.buttonColor) + "'> " + visitorBooking.getLocalName() + " </font>" + getActivity().getResources().getString(R.string.duration_let_choose);

        checkboxChoose.setText(Html.fromHtml(text));
        checkboxChoose.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarIsolatedAppointment(this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        // selectDateSelectDate.setText(new SimpleDateFormat("dd MMMM, yyyy").format(calendar.getTime()));
        selectDateSelectDate.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(visitorBooking.getDate(), "yyyy-MM-dd", "dd MMMM, yyyy"));

    }

    @OnClick(R.id.selectDateSelectDate)
    public void onViewClicked() {
        hideKeyBoard();
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateText = new SimpleDateFormat("dd MMMM, yyyy").format(calendar.getTime());
                selectDateSelectDate.setText(new SimpleDateFormat("dd MMMM, yyyy").format(calendar.getTime()));

                selectStartTime = null;
                selectEndTime = null;
                visitorBooking.setStartTime(selectStartTime);
                visitorBooking.setEndTime(selectEndTime);
                selectStartIndex = -1;
                selectEndIndex = -1;
                visitorBooking.setDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                //  presenter.getStartTime(visitorBooking);
                setStartTimeEndTime(0);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @OnClick(R.id.buttonNext)
    public void onViewClickedNext() {
        if (dateText.isEmpty()) {
            showMessage(getString(R.string.val_select_date));
            return;
        }
        if (selectStartTime == null) {
            showMessage(getString(R.string.val_select_start_time));
            return;
        }

        if (!checkboxChoose.isChecked()) {
            if (selectEndTime == null) {
                showMessage(getString(R.string.val_end_Tiem));
                return;
            }
        }

        visitorBooking.setLocalSelectTime(checkboxChoose.isChecked());

        presenter.openMettingLocation(visitorBooking);
    }

    @Override
    public void setData(List<Time> data) {

    }

    @Override
    public void setVisitorBooking(VisitorBooking visitorBooking) {
        this.visitorBooking = visitorBooking;
    }

    @Override
    public void setDataStartTime(List<Time> data) {
        startTime.clear();
        startTime.addAll(data);
        recyclerTimeSlots.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerTimeSlots.setAdapter(new SelectDateTimeAdapter(getActivity(), startTime, selectStartIndex, new SelectDateTimeAdapter.CallBack() {
            @Override
            public void onClick(Time time, int position) {
                selectStartIndex = position;
                selectStartTime = time;
                visitorBooking.setStartTime(selectStartTime);
                selectEndIndex = -1;
                visitorBooking.setEndTime(null);
                checkboxChoose.setChecked(false);
                setStartTimeEndTime(1);
            }
        }));

        if (getStatus()) {
            String text = "<font color='" + getActivity().getResources().getColor(R.color.buttonColor) + "'> " + visitorBooking.getLocalName() + " </font>" + getActivity().getResources().getString(R.string.has_no_ava) + "<br>" + getActivity().getResources().getString(R.string.pleas_select_another_Date);
            textViewTitle.setText(Html.fromHtml(text));
        }
    }

    private boolean getStatus() {
        boolean isNoFound = true;

        for (Time time : startTime) {
            if (time.getIsDisable().equalsIgnoreCase("0")) {
                if (time.getIsVisible().equalsIgnoreCase("0")) {
                    isNoFound = false;
                }
            }
        }
        return isNoFound;
    }

    @Override
    public void setDataEndTime(List<Time> data) {
        endTime.clear();
        endTime.addAll(data);

        setEndTimeData(endTime);
    }


    private void setEndTimeData(List<Time> endTimeDataTemp) {

        selectDateTimeEndAdapter = new SelectDateTimeAdapter(getActivity(), endTimeDataTemp, selectEndIndex, new SelectDateTimeAdapter.CallBack() {
            @Override
            public void onClick(Time time, int position) {
                selectEndIndex = position;
                selectEndTime = time;
                visitorBooking.setEndTime(selectEndTime);
                try {
                    textViewTitle.setText(getActivity().getResources().getString(R.string.the_activity_duration) + " " + getTimeDiffrent(selectStartTime.getTime(), selectEndTime.getTime()) + " hours.");
                    visitorBooking.setUserSelectTime(getTimeDiffrent(selectStartTime.getTime(), selectEndTime.getTime()));
                    setSelectedEndTime(selectStartTime, selectEndTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


        });
        recyclerTimeSlots.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerTimeSlots.setAdapter(selectDateTimeEndAdapter);
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClickedBack() {
        goBack();
    }


    @OnClick({R.id.linearLayoutStartTime, R.id.linearLayoutEndTime, R.id.checkboxChoose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearLayoutStartTime:
                setStartTimeEndTime(0);
                break;
            case R.id.linearLayoutEndTime:
                if (selectStartTime == null) {
                    showMessage(getString(R.string.val_start_time));
                    return;
                }
                selectEndIndex = -1;
                visitorBooking.setEndTime(null);
                checkboxChoose.setChecked(false);
                setStartTimeEndTime(1);
                break;
            case R.id.checkboxChoose:
//                setDisallow(checkboxChoose.isChecked());

                visitorBooking.setLocalSelectTime(checkboxChoose.isChecked());

                if (checkboxChoose.isChecked()) {
                    textViewTitle.setText(R.string.choose_your_end_time_or_you_can);
                    setEndTimeData(new ArrayList<Time>());

                    selectEndTime = getEndTiemSelect();

                    visitorBooking.setEndTime(selectEndTime);

                    try {

                        visitorBooking.setUserSelectTime(getTimeDiffrent(selectStartTime.getTime(), selectEndTime.getTime()));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {

                    selectEndTime = null;
                    visitorBooking.setEndTime(null);
                    selectEndIndex = -1;

                    setSelectedEndTimeClear();
                    setEndTimeData(endTime);
                }

                break;
        }
    }

    private void setStartTimeEndTime(int visibility) {
        imageViewStartTime.setVisibility((visibility == 0) ? View.VISIBLE : View.INVISIBLE);
        imageViewEndTime.setVisibility((visibility == 1) ? View.VISIBLE : View.INVISIBLE);
        if (0 == visibility) {
            textViewTitle.setText(R.string.choose_your_strat_time);
            checkboxChoose.setVisibility(View.GONE);
        } else {
            textViewTitle.setText(R.string.choose_your_end_time_or_you_can);
            checkboxChoose.setVisibility(View.VISIBLE);
        }

        if (visibility == 0) {
            presenter.getStartTime(visitorBooking);
        } else {
            presenter.getEndTime(visitorBooking);
        }
    }

    /**
     * It is used to select value start time end time
     *
     * @param selectStartTime
     * @param selectEndTime
     */
    private void setSelectedEndTime(Time selectStartTime, Time selectEndTime) {
        boolean isSelected = false;

        List<Time> endTimeTemp = new ArrayList<>();

        for (Time time : endTime) {

            if (time.getTime().equalsIgnoreCase(selectStartTime.getTime())) {
                isSelected = true;
            }
            if (time.getTime().equalsIgnoreCase(selectEndTime.getTime())) {
                isSelected = false;
            }

            time.setIsSelectedStTEn(isSelected ? "1" : "0");

            endTimeTemp.add(time);
        }

        endTime.clear();
        endTime.addAll(endTimeTemp);

        if (selectDateTimeEndAdapter != null) {
            selectDateTimeEndAdapter.notifyDataSetChanged();
        }
    }


    /**
     * Clear all select time for booking
     */
    private void setSelectedEndTimeClear() {
        boolean isSelected = false;

        List<Time> endTimeTemp = new ArrayList<>();

        for (Time time : endTime) {

            time.setIsSelectedStTEn(isSelected ? "1" : "0");

            endTimeTemp.add(time);
        }

        endTime.clear();
        endTime.addAll(endTimeTemp);

        if (selectDateTimeEndAdapter != null) {
            selectDateTimeEndAdapter.notifyDataSetChanged();
        }
    }

    /**
     * This method used to get minimum select time
     *
     * @return
     */
    private Time getEndTiemSelect() {

        Time timeSelect = new Time();
        for (Time time : endTime) {
            if (time.getIsDisable().equalsIgnoreCase("0") && time.getIsVisible().equalsIgnoreCase("0")) {
                return time;
            }
        }

        return timeSelect;
    }


    /**
     * It is used to disallow to select and click
     *
     * @param isChecked
     */
    private void setDisallow(boolean isChecked) {

        List<Time> endTimeTemp = new ArrayList<>();

        for (Time time : endTime) {

            time.setIsDisallow(isChecked ? "1" : "0");

            time.setIsSelectedStTEn("0");

            endTimeTemp.add(time);
        }

        endTime.clear();
        endTime.addAll(endTimeTemp);
        selectEndIndex = -1;
        selectEndTime = null;
        visitorBooking.setEndTime(selectEndTime);

        if (selectDateTimeEndAdapter != null) {
            selectDateTimeEndAdapter.setSelectedIndex(selectEndIndex);
            selectDateTimeEndAdapter.notifyDataSetChanged();
        }
    }
}
