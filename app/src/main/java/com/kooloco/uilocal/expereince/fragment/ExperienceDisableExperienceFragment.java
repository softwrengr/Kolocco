package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 17/4/18.
 */

import android.app.DatePickerDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.DisableExperience;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.presenter.ExperienceDisableExperiencePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceDisableExperienceView;
import com.kooloco.util.TimeConvertUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceDisableExperienceFragment extends BaseFragment<ExperienceDisableExperiencePresenter, ExperienceDisableExperienceView> implements ExperienceDisableExperienceView {

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
    @BindView(R.id.checkBoxNotification)
    CheckBox checkBoxNotification;
    @BindView(R.id.buttonSet)
    AppCompatButton buttonSet;

    Date selectStratDate, selectEndDate;
    @BindView(R.id.customTextViewStartTime1)
    AppCompatTextView customTextViewStartTime1;
    @BindView(R.id.customTextViewEndTime1)
    AppCompatTextView customTextViewEndTime1;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;
    private ExpereinceNew expNew;

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_disable_experience;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceDisableExperienceView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (expNew != null) {
            setDataExperence(expNew);
            presenter.getData(expNew);
        }

        checkBoxNotification.setOnClickListener(view -> setDisable());
    }

    private void setDataExperence(ExpereinceNew dataExperence) {

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
            // Picasso.with(getActivity()).load(dataExperence.getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageViewExp);
        } else {
            imageViewExp.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(dataExperence.getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageViewExp);
        }

    }


    @OnClick({R.id.imageViewBack, R.id.customTextViewStartTime1, R.id.customTextViewEndTime1, R.id.buttonSet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.customTextViewStartTime1:
                if (!checkBoxNotification.isChecked()) {
                    break;
                }
                calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime());


                        customTextViewStartTime1.setText(date);
                        customTextViewEndTime1.setText(date);

                        selectStratDate = calendar.getTime();
                        selectEndDate = calendar.getTime();

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
                datePickerDialog.show();

                break;
            case R.id.customTextViewEndTime1:
                if (!checkBoxNotification.isChecked()) {
                    break;
                }
                if (customTextViewStartTime1.getText().toString().isEmpty()) {
                    showMessage(getString(R.string.val_select_strat_date));
                    return;
                }

                //Solve crash issue for start time not coming

                calendar = Calendar.getInstance(); //THis line
                calendar.setTime(selectStratDate);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime());

                        customTextViewEndTime1.setText(date);
                        selectEndDate = calendar.getTime();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
                datePickerDialog.show();
                break;
            case R.id.buttonSet:

                if (checkBoxNotification.isChecked()) {
                    if (customTextViewStartTime1.getText().toString().isEmpty()) {
                        showMessage(getString(R.string.disable_exp_select_date));
                        return;
                    }
                }
                String startDate = "";
                String endDate = "";
                if (checkBoxNotification.isChecked()) {
                    startDate = TimeConvertUtils.dateTimeConvertLocalToLocal(customTextViewStartTime1.getText().toString(), "dd MMM, yyyy", "yyyy-MM-dd");
                    endDate = TimeConvertUtils.dateTimeConvertLocalToLocal(customTextViewEndTime1.getText().toString(), "dd MMM, yyyy", "yyyy-MM-dd");
                }

                presenter.callWs(expNew.getId(), startDate, endDate);

                break;
        }
    }

    private void setDisable() {
        customTextViewStartTime1.setAlpha(checkBoxNotification.isChecked() ? 1f : 0.75f);
        customTextViewEndTime1.setAlpha(checkBoxNotification.isChecked() ? 1f : 0.75f);
        customTextViewStartTime1.setText("");
        customTextViewEndTime1.setText("");
    }

    @Override
    public void setExpNew(ExpereinceNew expNew) {
        this.expNew = expNew;
    }

    @Override
    public void setData(DisableExperience data) {
        checkBoxNotification.setChecked(true);
        setDisable();
        customTextViewStartTime1.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(data.getStartDate(), "yyyy-MM-dd", "dd MMM, yyyy"));
        customTextViewEndTime1.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(data.getEndDate(), "yyyy-MM-dd", "dd MMM, yyyy"));

        selectStratDate = TimeConvertUtils.getDate(data.getStartDate(), "yyyy-MM-dd");
        selectEndDate = TimeConvertUtils.getDate(data.getEndDate(), "yyyy-MM-dd");
    }
}
