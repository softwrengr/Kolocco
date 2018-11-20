package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 21/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.BookingFeeAndComision;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.expereince.adapter.ExperienceCancellationPolicyAdapter;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsWithPriceAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.AppointmentSummaryPresenter;
import com.kooloco.ui.visitor.dashboard.view.AppointmentSummaryView;
import com.kooloco.util.FirstCapEditText;
import com.kooloco.util.StaticMap;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AppointmentSummaryFragment extends BaseFragment<AppointmentSummaryPresenter, AppointmentSummaryView> implements AppointmentSummaryView {


    Double price = 0.0;

    double tootalBooking = 0.0, tootalActivityPrice = 0.0;
    double feesForBooking = 0.0, feesForBookingPer = 0.0;
    double commissionPer = 0.0, commissionValue = 0.0;


    BookingFeeAndComision bookingFeeAndComision;

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
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
    @BindView(R.id.customTextViewDate)
    AppCompatTextView customTextViewDate;
    @BindView(R.id.textViewTime)
    AppCompatTextView textViewTime;
    @BindView(R.id.customTextViewTime)
    AppCompatTextView customTextViewTime;
    @BindView(R.id.customTextViewDuration)
    AppCompatTextView customTextViewDuration;
    @BindView(R.id.customTextViewParticipants)
    AppCompatTextView customTextViewParticipants;
    @BindView(R.id.imageViewStaticMap)
    ImageView imageViewStaticMap;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.customTextViewMeetSpotLocation)
    AppCompatTextView customTextViewMeetSpotLocation;
    @BindView(R.id.customTexteditTextWriteNow)
    FirstCapEditText customTexteditTextWriteNow;
    @BindView(R.id.textViewAddParticipants)
    AppCompatTextView textViewAddParticipants;
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
    @BindView(R.id.customTextViewNote)
    AppCompatTextView customTextViewNote;
    @BindView(R.id.buttonConfirmBooking)
    AppCompatButton buttonConfirmBooking;
    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;
    @BindView(R.id.customTextViewRefundValue)
    AppCompatTextView customTextViewRefundValue;
    @BindView(R.id.linearLayoutRefAmount)
    LinearLayout linearLayoutRefAmount;
    @BindView(R.id.customTextViewCancelPolicyName)
    AppCompatTextView customTextViewCancelPolicyName;
    @BindView(R.id.recyclerViewCancellation)
    RecyclerView recyclerViewCancellation;

    private ExperienceBookingFlow visitorBooking;
    Double refferalAmount = 0.0;
    String isApplyReffearl = "0";

    @Inject
    Session session;

    @Override
    protected int createLayout() {
        return R.layout.fragment_appointment_history;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AppointmentSummaryView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        linearLayoutExpRoot.setClickable(false);
        presenter.getBookingFeesAndComision(visitorBooking.getExpereinceNew().getId(), visitorBooking);
    }

    @OnClick(R.id.buttonConfirmBooking)
    public void onViewClicked() {

        if (bookingFeeAndComision != null) {
            visitorBooking.setPrice("" + price);
            visitorBooking.setTootalPriceActivity("" + BaseFragment.convertFormat(tootalActivityPrice));
            visitorBooking.setTootalPrice("" + BaseFragment.convertFormat(roundDouble(tootalBooking)));

            visitorBooking.setFeesForBookingPer("" + bookingFeeAndComision.getFeesForBooking());
            visitorBooking.setFeesForBooking("" + BaseFragment.convertFormat(feesForBooking));
            visitorBooking.setCommisionPer(bookingFeeAndComision.getCommision());
            visitorBooking.setCommisionValue("" + BaseFragment.convertFormat(commissionValue));
            visitorBooking.setMoreAbout(customTexteditTextWriteNow.getText().toString().trim());
            visitorBooking.setIsRefferal("" + isApplyReffearl);
            visitorBooking.setRefferalAmount("" + refferalAmount);
            visitorBooking.setReferralPer("" + bookingFeeAndComision.getReferralPercentage());

            visitorBooking.setSetRefferalTotal((BaseFragment.convertFormat(roundDouble(tootalBooking - refferalAmount))));

            presenter.openPayment(visitorBooking);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarIsolatedAppointment(this.getClass().getSimpleName());
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClickedBack() {
        goBack();
    }


    @Override
    public void setBookingData(ExperienceBookingFlow visitorBooking) {
        this.visitorBooking = visitorBooking;
    }

    @Override
    public void setData(BookingFeeAndComision data) {

        bookingFeeAndComision = data;
        String text = "" + getActivity().getResources().getString(R.string.appointment_summery_note).replace("###", "<font color='" + getActivity().getResources().getColor(R.color.red) + "'>* </font>");
        customTextViewNote.setText(Html.fromHtml(text));

        customTextViewLocalName.setText(visitorBooking.getLocalName());

        Picasso.with(getActivity()).load(visitorBooking.getLocalProfile()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        customTextViewRatingValue.setText(visitorBooking.getLocalRating());

        //Set data Location strat section

        String staticMapUrl = StaticMap.getUrl(getActivity(), visitorBooking.getExpereinceNew().getActivityLatitude(), visitorBooking.getExpereinceNew().getActivityLongitude(), URLFactory.MEETING);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);

        customTextViewMeetSpotLocation.setText(visitorBooking.getExpereinceNew().getActivityAddress());

        customTextViewDate.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(visitorBooking.getSelectDate(), "yyyy-MM-dd", "dd MMM yyyy"));


        textViewTime.setText(visitorBooking.getSchedulePrice().getIsMultipleDay().equalsIgnoreCase("1") ? getActivity().getResources().getString(R.string.fil_start_time) : getActivity().getResources().getString(R.string.appointment_summery_time));

        String timeText = "";
        if (visitorBooking.getSchedulePrice().getIsMultipleDay().equalsIgnoreCase("1")) {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + visitorBooking.getSchedulePrice().getStartTime(), "HH:mm:ss", "hh:mm a");
        } else {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + visitorBooking.getSchedulePrice().getStartTime(), "HH:mm:ss", "hh:mm a") + " " + getString(R.string.sch_to) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal("" + visitorBooking.getSchedulePrice().getEndTime(), "HH:mm:ss", "hh:mm a");
        }

        customTextViewTime.setText(timeText);

        //Set Duration

        String durationText = "";
        if (visitorBooking.getSchedulePrice().getIsMultipleDay().equalsIgnoreCase("1")) {
            durationText = visitorBooking.getSchedulePrice().getDurationInDays() + " " + getActivity().getResources().getString(R.string.schdule_price_days);
        } else {
            durationText = visitorBooking.getSchedulePrice().getDuration() + " " + getActivity().getResources().getString(R.string.hours_exp);
        }


        customTextViewDuration.setText(durationText);


        customTextViewParticipants.setText("" + visitorBooking.getAddParticipantsList().size());

        setDataExperence(visitorBooking.getExpereinceNew());


        price = Double.parseDouble(visitorBooking.getSchedulePrice().getPrice());

        tootalActivityPrice =
                BaseFragment.roundDouble(price * visitorBooking.getAddParticipantsList().size());

        recyclerAddParticipants.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        List<AddParticipants> addParticipantsList = new ArrayList<>();

        int i = 1;

        for (AddParticipants addParticipants : visitorBooking.getAddParticipantsList()) {
            if (addParticipants.getEmail().equalsIgnoreCase(session.getUser().getEmail())) {
                AddParticipants addParticipantsTemp = new AddParticipants();

                if (i == 1) {
                    addParticipantsTemp.setEmail(session.getUser().getFirstname() + " " + session.getUser().getLastname());
                } else {
                    addParticipantsTemp.setEmail(getString(R.string.participant) + " " + i);
                }

                addParticipantsList.add(addParticipantsTemp);
            } else {
                AddParticipants addParticipantsTemp = new AddParticipants();
                addParticipantsTemp.setEmail(getString(R.string.participant) + " " + i);
                addParticipantsList.add(addParticipantsTemp);
            }
            i++;
        }


        recyclerAddParticipants.setAdapter(new AddParticipantsWithPriceAdapter(getActivity(), addParticipantsList, tootalActivityPrice));

        feesForBookingPer = Double.parseDouble(bookingFeeAndComision.getFeesForBooking());

        customTextViewRecTotalAct.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(tootalActivityPrice));

        feesForBooking = tootalActivityPrice * feesForBookingPer / 100;

        customTextViewRecFeesFBookingPrice.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(feesForBooking));


        isApplyReffearl = bookingFeeAndComision.getIsReferral();

        if (bookingFeeAndComision.getIsReferral().equalsIgnoreCase("1")) {
            linearLayoutRefAmount.setVisibility(View.VISIBLE);
            customTextViewRefundValue.setText("- " + BaseActivity.currency + " " + bookingFeeAndComision.getReferralAmount());
        }

        tootalBooking = (tootalActivityPrice + feesForBooking);

        if (bookingFeeAndComision.getIsReferral().equalsIgnoreCase("1")) {

            try {
                refferalAmount = Double.parseDouble(bookingFeeAndComision.getReferralAmount());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            customTextViewRecTotalBooking.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(roundDouble((tootalBooking - refferalAmount))));
        } else {
            customTextViewRecTotalBooking.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(roundDouble(tootalBooking)));
        }


        commissionPer = Double.parseDouble(bookingFeeAndComision.getCommision());

        commissionValue = roundDouble(tootalActivityPrice * commissionPer / 100);

        //Temp name cancellation policy
        customTextViewCancelPolicyName.setText(getActivity().getString(R.string.dashboard_cancellation_policy) + " (" + data.getCancelletionPolicyName() + ") ");
        //    customTextViewCancelPolicyDesc.setText(expDetails.getCancelletionPolicyDesc());

//        customTextViewCancelPolicyName.setText(expDetails.getCancelletionPolicyName());

        recyclerViewCancellation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        recyclerViewCancellation.setAdapter(new ExperienceCancellationPolicyAdapter(getActivity(), data.getCancelletionPolicyList(), new ExperienceCancellationPolicyAdapter.CallBack() {
            @Override
            public void onClickListener() {

            }
        }));

    }

    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        presenter.openMapScreen(visitorBooking);
    }

    private void setDataExperence(ExpereinceNew dataExperence) {

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

        if (dataExperence.getExperience_url1() == null) {
            dataExperence.setExperience_url1("");
        }

        Picasso.with(getActivity()).load(dataExperence.getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageViewExp);

    }

}
