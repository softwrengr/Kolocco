package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 22/9/17.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.OrderDetails;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsWithPriceAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.ReceiptPresenter;
import com.kooloco.ui.visitor.dashboard.view.ReceiptView;
import com.kooloco.util.FirstCapEditText;
import com.kooloco.util.InputFilterMinMaxDouble;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.Validator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceiptFragment extends BaseFragment<ReceiptPresenter, ReceiptView> implements ReceiptView {


    String orderId = "0";


    double pricePartActivity;
    double priceActivity;
    double feesForBooking = 0;

    double tootalBooking = 0.0;

    @Inject
    Validator validator;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
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
    @BindView(R.id.customTextViewLocationMeeting)
    AppCompatTextView customTextViewLocationMeeting;
    @BindView(R.id.imageViewExp)
    ImageView imageViewExp;
    @BindView(R.id.textViewExpPrice)
    AppCompatTextView textViewExpPrice;
    @BindView(R.id.linearLayoutExpRoot)
    LinearLayout linearLayoutExpRoot;
    @BindView(R.id.customTextViewDate)
    AppCompatTextView customTextViewDate;
    @BindView(R.id.customTextViewTime)
    AppCompatTextView customTextViewTime;
    @BindView(R.id.customTextViewDuration)
    AppCompatTextView customTextViewDuration;
    @BindView(R.id.customTextViewParticipants)
    AppCompatTextView customTextViewParticipants;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.textView)
    AppCompatTextView textView;
    @BindView(R.id.recyclerAddParticipants)
    RecyclerView recyclerAddParticipants;
    @BindView(R.id.customTextViewRecTotalAct)
    AppCompatTextView customTextViewRecTotalAct;
    @BindView(R.id.customTextViewRecFeesFBookingPrice)
    AppCompatTextView customTextViewRecFeesFBookingPrice;
    @BindView(R.id.customTextViewRecTotalBooking)
    AppCompatTextView customTextViewRecTotalBooking;
    @BindView(R.id.checkboxChoose)
    AppCompatCheckBox checkboxChoose;
    @BindView(R.id.linearLayoutRaiseObj)
    LinearLayout linearLayoutRaiseObj;
    @BindView(R.id.radioButtonRaiseTotal)
    RadioButton radioButtonRaiseTotal;
    @BindView(R.id.linearLayoutRefundTotal)
    LinearLayout linearLayoutRefundTotal;
    @BindView(R.id.radioButtonRaisePersentage)
    RadioButton radioButtonRaisePersentage;
    @BindView(R.id.customEditTextPersntageDiscount)
    AppCompatEditText customEditTextPersntageDiscount;
    @BindView(R.id.linearLayoutRefundPercentage)
    LinearLayout linearLayoutRefundPercentage;
    @BindView(R.id.radioButtonFlatAmount)
    RadioButton radioButtonFlatAmount;
    @BindView(R.id.customEdiTextFlatAmount)
    AppCompatEditText customEdiTextFlatAmount;
    @BindView(R.id.linearLayoutRefundFlatAmount)
    LinearLayout linearLayoutRefundFlatAmount;
    @BindView(R.id.customTexteditTextWriteNow)
    FirstCapEditText customTexteditTextWriteNow;
    @BindView(R.id.linearLayoutRaiseObjection)
    LinearLayout linearLayoutRaiseObjection;
    @BindView(R.id.buttonSendObjection)
    AppCompatButton buttonSendObjection;
    @BindView(R.id.customEdiTextFlatAmountText)
    AppCompatTextView customEdiTextFlatAmountText;
    @BindView(R.id.textViewTime)
    AppCompatTextView textViewTime;

    @Inject
    Session session;
    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;
    @BindView(R.id.customTextViewReferralValue)
    AppCompatTextView customTextViewReferralValue;
    @BindView(R.id.linearLayoutReferralAmount)
    LinearLayout linearLayoutReferralAmount;
    private OrderDetails orderDetails;


    @Override
    protected int createLayout() {
        return R.layout.fragment_receipt;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ReceiptView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        presenter.getData(orderId);


    }

    @OnClick({R.id.linearLayoutRaiseObj, R.id.linearLayoutRefundTotal, R.id.linearLayoutRefundPercentage, R.id.linearLayoutRefundFlatAmount, R.id.buttonSendObjection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearLayoutRaiseObj:
                checkboxChoose.setChecked(!checkboxChoose.isChecked());
                linearLayoutRaiseObjection.setVisibility(checkboxChoose.isChecked() ? View.VISIBLE : View.GONE);
                radioButtonRaiseTotal.setChecked(true);
                radioButtonRaisePersentage.setChecked(false);
                radioButtonFlatAmount.setChecked(false);
                if (checkboxChoose.isChecked()) {
                    buttonSendObjection.setText(getActivity().getResources().getString(R.string.button_send_objection));
                } else {
                    buttonSendObjection.setText(getActivity().getResources().getString(R.string.button_pay_now));
                }
                setEditable();
                break;
            case R.id.linearLayoutRefundTotal:
                radioButtonRaiseTotal.setChecked(true);
                radioButtonRaisePersentage.setChecked(false);
                radioButtonFlatAmount.setChecked(false);
                hideKeyBoard();
                setEditable();
                break;
            case R.id.linearLayoutRefundPercentage:
                radioButtonRaisePersentage.setChecked(true);
                radioButtonRaiseTotal.setChecked(false);
                radioButtonFlatAmount.setChecked(false);
                hideKeyBoard();
                customEditTextPersntageDiscount.requestFocus();
                focusKeyBoard(customEditTextPersntageDiscount);
                setEditable();
                break;
            case R.id.linearLayoutRefundFlatAmount:
                radioButtonFlatAmount.setChecked(true);
                radioButtonRaiseTotal.setChecked(false);
                radioButtonRaisePersentage.setChecked(false);
                hideKeyBoard();
                customEdiTextFlatAmount.requestFocus();
                focusKeyBoard(customEdiTextFlatAmount);
                setEditable();
                break;
            case R.id.buttonSendObjection:
                if (checkboxChoose.isChecked()) {
                    try {
                        String type = "TR";
                        String objectionValue = "";

                        if (radioButtonRaisePersentage.isChecked()) {
                            validator.submit(customEditTextPersntageDiscount).checkEmpty().errorMessage(R.string.val_rasi_per).check();
                            type = "PR";
                            objectionValue = customEditTextPersntageDiscount.getText().toString().trim();
                        } else if (radioButtonFlatAmount.isChecked()) {
                            validator.submit(customEdiTextFlatAmount).checkEmpty().errorMessage(R.string.val_f_amount_discount).check();
                            type = "FA";

                            Double dObjValue = 0.00;

                            try {
                                dObjValue = Double.parseDouble(customEdiTextFlatAmount.getText().toString().trim());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                            double finePer = dObjValue * 100 / priceActivity;

                            objectionValue = "" + finePer;

                        }

                        validator.submit(customTexteditTextWriteNow).checkEmpty().errorMessage(R.string.val_w_reason).check();

                        presenter.callSendObjection(orderId, type, objectionValue, customTexteditTextWriteNow.getText().toString().trim());

                    } catch (ApplicationException e) {
                        e.printStackTrace();
                        showMessage(e.getMessage());
                    }

                } else {
                    presenter.callSendPayment(orderId);
                }
                break;
        }
    }

    private void setEditable() {

        if (!radioButtonRaisePersentage.isChecked()) {
            customEditTextPersntageDiscount.setText("");

        } else if (!radioButtonFlatAmount.isChecked()) {
            customEdiTextFlatAmount.setText("");
        }

        customEditTextPersntageDiscount.setEnabled(radioButtonRaisePersentage.isChecked());
        customEditTextPersntageDiscount.setSelected(radioButtonRaisePersentage.isChecked());

        customEdiTextFlatAmount.setEnabled(radioButtonFlatAmount.isChecked());
        customEdiTextFlatAmount.setSelected(radioButtonFlatAmount.isChecked());

    }


    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        presenter.onBack();
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void setData(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;

        if (orderDetails.getIsObjected().equalsIgnoreCase("1")) {
            goBack();
            return;
        }

        String currencyVisitorBooking = orderDetails.getVisitorCurrency();

        setDataEditText(currencyVisitorBooking);

        customTextViewLocationMeeting.setText(orderDetails.getMeetingAddress());

        String timeText = "";
        if (orderDetails.getInMultiDay().equalsIgnoreCase("1")) {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a");
        } else {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a") + " " + getString(R.string.sch_to) + " " +  TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getEndTime(), "HH:mm:ss", "hh:mm a");
        }


        linearLayoutRaiseObj.setVisibility(orderDetails.getIsReferral().equalsIgnoreCase("1") ? View.GONE : View.VISIBLE);

        customTextViewTime.setText(timeText);

        textViewTime.setText(orderDetails.getInMultiDay().equalsIgnoreCase("1") ? getActivity().getResources().getString(R.string.fil_start_time) : getActivity().getResources().getString(R.string.appointment_summery_time));


        customTextViewDate.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(orderDetails.getBookingDate(), "yyyy-MM-dd", "dd MMMM, yyyy"));

        //Set Duration

        String durationText = "";
        if (orderDetails.getInMultiDay().equalsIgnoreCase("1")) {
            durationText = orderDetails.getDuration() + " " + getActivity().getResources().getString(R.string.schdule_price_days);
        } else {
            durationText = orderDetails.getDuration() + " " + getActivity().getResources().getString(R.string.hours_exp);
        }

        customTextViewDuration.setText(durationText);

        customTextViewParticipants.setText("" + orderDetails.getParticipant().size());

        priceActivity = roundDouble(Double.parseDouble(orderDetails.getPrice()) * orderDetails.getParticipant().size());

        double bookingFeesPer = Double.parseDouble(orderDetails.getFeesForBookingPerc());

        feesForBooking = Double.parseDouble(orderDetails.getFeesForBooking());

        feesForBooking = roundDouble(((priceActivity * bookingFeesPer) / 100));

        customTextViewRecTotalAct.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(priceActivity));

        tootalBooking = roundDouble(priceActivity + feesForBooking);

        customTextViewRecFeesFBookingPrice.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(feesForBooking));


        double referVal = 0.0;

        try {
            referVal = Double.parseDouble(orderDetails.getReferralAmount());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        if (orderDetails.getIsReferral().equalsIgnoreCase("1")) {
            linearLayoutReferralAmount.setVisibility(View.VISIBLE);
            customTextViewReferralValue.setText("- " + currencyVisitorBooking + " " + BaseFragment.convertFormat(referVal));
            tootalBooking = BaseFragment.roundDouble(tootalBooking - referVal);
        } else {
            linearLayoutReferralAmount.setVisibility(View.GONE);
        }

        customTextViewRecTotalBooking.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(tootalBooking));


        List<AddParticipants> addParticipantsList = new ArrayList<>();

        int i = 1;

        for (AddParticipants addParticipants : orderDetails.getParticipant()) {
            if (addParticipants.getUserId().equalsIgnoreCase(orderDetails.getUserId())) {

                addParticipants.setEmail(session.getUser().getFirstname() + " " + session.getUser().getLastname());

                addParticipantsList.add(addParticipants);
            } else {
                addParticipants.setEmail(getString(R.string.participant) + " " + i);
                addParticipantsList.add(addParticipants);
            }
            i++;
        }

        setDataExperence(orderDetails.getExpereinceNew());

        recyclerAddParticipants.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerAddParticipants.setAdapter(new AddParticipantsWithPriceAdapter(getActivity(), addParticipantsList, roundDouble(priceActivity), currencyVisitorBooking));

        customEditTextPersntageDiscount.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(0.00, 100.00)});
        customEdiTextFlatAmount.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(0.00, roundDouble(priceActivity))});

    }


    private void setDataExperence(ExpereinceNew dataExperence) {

        imageView.setVisibility(View.VISIBLE);
        Picasso.with(getActivity()).load(dataExperence.getImage_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageView);

        customTextViewTitle.setText(dataExperence.getTitle());

        float rate = 0.0f;

        try {
            rate = Float.parseFloat(dataExperence.getRate());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ratingBar.setRating(rate);

        textViewRateCount.setText("(" + dataExperence.getRateCount() + ")");

        customTextViewLocation.setText(dataExperence.getLocation());

        textExpCurrency.setText(BaseActivity.currency);

        textViewExpPrice.setText(dataExperence.getPrice());

        Picasso.with(getActivity()).load(dataExperence.getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageViewExp);

        linearLayoutExpRoot.setOnClickListener(view -> {
            if (checkIsDeleteOrActive(dataExperence)) {
                presenter.openExpDetails(dataExperence);
            }
        });
    }

    private void setDataEditText(String currency) {
        if (customEdiTextFlatAmountText != null) {
            customEdiTextFlatAmountText.setText(getActivity().getResources().getString(R.string.receipt_do_you_want_flat_amount_discount_new).replace("$", currency));
        }
        if (customEdiTextFlatAmount != null) {
            customEdiTextFlatAmount.setHint(currency + "00");
        }

    }
}
