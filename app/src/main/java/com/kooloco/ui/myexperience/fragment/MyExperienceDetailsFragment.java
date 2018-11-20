package com.kooloco.ui.myexperience.fragment;
/**
 * Created by hlink44 on 28/9/17.
 */

import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.myexperience.presenter.MyExperienceDetailsPresenter;
import com.kooloco.ui.myexperience.view.MyExperienceDetailsView;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsWithPriceAdapter;
import com.kooloco.util.StaticMap;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MyExperienceDetailsFragment extends BaseFragment<MyExperienceDetailsPresenter, MyExperienceDetailsView> implements MyExperienceDetailsView {


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
    @BindView(R.id.imageViewCall)
    ImageView imageViewCall;
    @BindView(R.id.imageViewChat)
    ImageView imageViewChat;
    @BindView(R.id.buttonShareYourExperience)
    AppCompatButton buttonShareYourExperience;
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
    @BindView(R.id.customTextViewPaymentStatus)
    AppCompatTextView customTextViewPaymentStatus;
    @BindView(R.id.customTextViewCheckEquipmets)
    AppCompatTextView customTextViewCheckEquipmets;
    @BindView(R.id.customTextViewSportEquipmentsValue)
    AppCompatTextView customTextViewSportEquipmentsValue;
    @BindView(R.id.customTextViewDate)
    AppCompatTextView customTextViewDate;
    @BindView(R.id.customTextViewTime)
    AppCompatTextView customTextViewTime;
    @BindView(R.id.customTextViewDuration)
    AppCompatTextView customTextViewDuration;
    @BindView(R.id.customTextViewParticipants)
    AppCompatTextView customTextViewParticipants;
    @BindView(R.id.customTextViewMeetingLocation)
    AppCompatTextView customTextViewMeetingLocation;
    @BindView(R.id.imageViewStaticMap)
    ImageView imageViewStaticMap;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.lineraLayoutPanding)
    LinearLayout lineraLayoutPanding;
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
    @BindView(R.id.customTextViewRefundValue)
    AppCompatTextView customTextViewRefundValue;
    @BindView(R.id.linearLayoutRefAmount)
    LinearLayout linearLayoutRefAmount;
    @BindView(R.id.customTextViewNewAmountValue)
    AppCompatTextView customTextViewNewAmountValue;
    @BindView(R.id.linearLayoutNewAmount)
    LinearLayout linearLayoutNewAmount;
    @BindView(R.id.viewNewAmount)
    View viewNewAmount;
    @BindView(R.id.customTextViewStatus)
    AppCompatTextView customTextViewStatus;
    @BindView(R.id.linearLayoutStatus)
    LinearLayout linearLayoutStatus;
    @BindView(R.id.textViewTime)
    AppCompatTextView textViewTime;
    @BindView(R.id.linearLayoutSportEquipments)
    LinearLayout linearLayoutSportEquipments;
    @BindView(R.id.viewEquipment)
    View viewEquipment;
    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;
    @BindView(R.id.customTextViewReferralValue)
    AppCompatTextView customTextViewReferralValue;
    @BindView(R.id.linearLayoutReferralAmount)
    LinearLayout linearLayoutReferralAmount;

    private ExperienceDetails experienceDetails;
    double pricePartActivity;
    double priceActivity;
    double feesForBooking = 0;

    double tootalBooking = 0.0;

    OrderDetails orderDetailsData;

    @BindView(R.id.textViewBookingFeesText)
    AppCompatTextView textViewBookingFeesText;

    @Inject
    Session session;


    @Override
    protected int createLayout() {
        return R.layout.fragment_my_experience_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected MyExperienceDetailsView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        boolean isExpStatusComplate = experienceDetails.getPaymentStatus().equalsIgnoreCase(getString(R.string.order_history_paid));

        imageViewCall.setVisibility(isExpStatusComplate ? View.GONE : View.VISIBLE);
        imageViewChat.setVisibility(isExpStatusComplate ? View.GONE : View.VISIBLE);

        // customTextViewCheckEquipmets.setVisibility(isExpStatusComplate ? View.GONE : View.VISIBLE);

        // buttonShareYourExperience.setVisibility(isExpStatusComplate ? View.VISIBLE : View.GONE);
        //customTextViewSportEquipmentsValue.setVisibility(isExpStatusComplate ? View.VISIBLE : View.GONE);

        presenter.getData(experienceDetails.getId());


        // buttonShareYourExperience.setText(experienceDetails.getIsPublished().equalsIgnoreCase("1") ? getActivity().getResources().getString(R.string.edit_you_experience) : getActivity().getResources().getString(R.string.share_you_experience));

    }

    @OnClick({R.id.imageViewBack, R.id.imageViewCall, R.id.imageViewChat, R.id.buttonShareYourExperience})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageViewCall:
                if (orderDetailsData != null) {
                    openCallIntent(orderDetailsData.getCountryCode(), orderDetailsData.getPhone());
                }
                //presenter.openBlogList();
                break;
            case R.id.imageViewChat:
                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(orderDetailsData.getLocalId());
                receiverData.setName(orderDetailsData.getFirstname() + " " + orderDetailsData.getLastname());
                receiverData.setImageUrl(orderDetailsData.getProfileImage());
                receiverData.setDeviceType(orderDetailsData.getDeviceType());
                receiverData.setDeviceToken(orderDetailsData.getDeviceToken());
                receiverData.setOrderId(orderDetailsData.getId());

                presenter.opnChat(receiverData);
                break;
            case R.id.buttonShareYourExperience:
                presenter.openCreateBlog(experienceDetails);
                break;
        }
    }

    @OnClick(R.id.customTextViewCheckEquipmets)
    public void onViewClicked() {
        ReceiverData receiverData = new ReceiverData();
        receiverData.setUser_id(orderDetailsData.getLocalId());
        receiverData.setName(orderDetailsData.getFirstname() + " " + orderDetailsData.getLastname());
        receiverData.setImageUrl(orderDetailsData.getProfileImage());
        receiverData.setDeviceType(orderDetailsData.getDeviceType());
        receiverData.setDeviceToken(orderDetailsData.getDeviceToken());
        receiverData.setOrderId(orderDetailsData.getId());

        String chatStatus = "";

        if (orderDetailsData.getStatus() == 2 && orderDetailsData.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.PAID)) {
            chatStatus = "2";
        } else {
            chatStatus = "1";
        }

        String orderStatus = (orderDetailsData.getStatus() == 2) ? "2" : "1";

        presenter.openCheckEquipments(orderDetailsData.getId(), receiverData, orderStatus, chatStatus);
    }


    @Override
    public void setExpDetails(ExperienceDetails experienceDetails) {
        this.experienceDetails = experienceDetails;
    }

    @Override
    public void setData(OrderDetails orderDetails) {
        orderDetailsData = orderDetails;

        String currencyVisitorBooking = orderDetails.getVisitorCurrency();

        setDataExperence(orderDetails.getExpereinceNew());

        orderDetailsData = orderDetails;
        Integer orderStatus = orderDetails.getStatus();
        if (orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.ESCROW)) {
            customTextViewPaymentStatus.setText(getActivity().getResources().getString(R.string.order_history_escrow));
            customTextViewPaymentStatus.setTextColor(getActivity().getResources().getColor(R.color.yellow));
        } else {
            customTextViewPaymentStatus.setText(getActivity().getResources().getString(R.string.order_history_paid));
            customTextViewPaymentStatus.setTextColor(getActivity().getResources().getColor(R.color.green));
        }


        if (orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.ESCROW)) {
            linearLayoutStatus.setVisibility(View.VISIBLE);
        } else if (orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.CANCEL)) {
            linearLayoutStatus.setVisibility(View.GONE);
        } else {
            linearLayoutStatus.setVisibility(View.VISIBLE);
        }


        String status = "";

        String statusText = "";

        switch (orderStatus) {
            case 0:
                status = getString(R.string.pending_for_approval);
                statusText = getString(R.string.pending_for_approval);
                break;
            case 1:
                status = getString(R.string.ready_start);
                statusText = getString(R.string.ready_start);
                break;
            case 2:
                status = Common.OrderDetails.COMPLETED;
                statusText = getString(R.string.order_status_completed);
                break;
            case 3:
                status = Common.OrderDetails.REJECTED;
                statusText = getString(R.string.order_status_rejected);
                break;
            default:
                status = Common.OrderDetails.PENDING;
                statusText = getString(R.string.pending_for_approval);
                break;

        }


        statusText = statusText.substring(0, 1).toUpperCase() + statusText.substring(1);

        customTextViewStatus.setText(statusText);


        status = status.substring(0, 1).toUpperCase() + status.substring(1);

        customTextViewStatus.setTextColor(getActivity().getResources().getColor(R.color.yellow));

        if (status.equalsIgnoreCase(getString(R.string.pending_for_approval))) {
            customTextViewStatus.setTextColor(getActivity().getResources().getColor(R.color.yellow));
        } else if (status.equalsIgnoreCase(getString(R.string.ready_start)) || status.equalsIgnoreCase(Common.OrderDetails.COMPLETED)) {
            customTextViewStatus.setTextColor(getActivity().getResources().getColor(R.color.green));
        } else if (status.equalsIgnoreCase(Common.OrderDetails.REJECTED)) {
            customTextViewStatus.setTextColor(getActivity().getResources().getColor(R.color.red));
        }


        customTextViewLocalName.setText(orderDetails.getFirstname() + " " + orderDetails.getLastname());

        Picasso.with(getActivity()).load(orderDetails.getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);

        imageViewProfile.setOnClickListener(view -> imageOpenZoom(orderDetails.getProfileImage()));
        customTextViewRatingValue.setText(orderDetails.getRate());

        //Set Meeting address section start

        String staticMapUrl = StaticMap.getUrl(getActivity(), orderDetails.getMeetingLatitude(), orderDetails.getMeetingLongitude(), URLFactory.MEETING);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);


        customTextViewMeetingLocation.setText(orderDetails.getMeetingAddress());

        frameLayoutMap.setVisibility(orderDetails.getIsLocalSetMeetingLoc().equalsIgnoreCase("1") ? View.INVISIBLE : View.VISIBLE);

        String timeText = "";
        if (orderDetails.getInMultiDay().equalsIgnoreCase("1")) {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a");
        } else {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a") + " " + getString(R.string.sch_to) + " " +  TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getEndTime(), "HH:mm:ss", "hh:mm a");
        }

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


        customTextViewParticipants.setText(""+orderDetails.getParticipant().size() );

        priceActivity = roundDouble(Double.parseDouble(orderDetails.getPrice()) * orderDetails.getParticipant().size());

        customTextViewRecTotalAct.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(priceActivity));


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
        recyclerAddParticipants.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerAddParticipants.setAdapter(new AddParticipantsWithPriceAdapter(getActivity(), addParticipantsList, roundDouble(priceActivity), currencyVisitorBooking));


        linearLayoutRefAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") ? View.VISIBLE : View.GONE);
        textViewBookingFeesText.setText(orderDetails.getIsObjDetails().equalsIgnoreCase("1") ? getString(R.string.booking_fee_adapted) : getString(R.string.booking_fee));

        //As per client comment visibility gone this

        linearLayoutNewAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") ? View.GONE : View.GONE);
        viewNewAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") ? View.GONE : View.GONE);

        double refValueSet = 0.0;

        if (orderDetails.getIsObjDetails().equalsIgnoreCase("1")) {

            ObjectionDetails data = orderDetailsData.getObjectionDetails();

            Double tootleAmount = Double.parseDouble(orderDetails.getActivityTootal());


            if (data.getObjectionType().equalsIgnoreCase("TR")) {

                refValueSet = tootleAmount;

            } else if (data.getObjectionType().equalsIgnoreCase("PR")) {

                Double discountPer = Double.parseDouble(data.getObjectionPerc());

                refValueSet = roundDouble(tootleAmount * (discountPer / 100));

            } else if (data.getObjectionType().equalsIgnoreCase("FA")) {

                Double discountFlat = Double.parseDouble(data.getObjectionAmount());
                refValueSet = discountFlat;
            }

            customTextViewRefundValue.setText("- " + currencyVisitorBooking + " " + BaseFragment.convertFormat(refValueSet));
        }


        double referVal = 0.0;

        try {
            referVal = Double.parseDouble(orderDetails.getReferralAmount());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        if (orderDetails.getIsObjDetails().equalsIgnoreCase("1")) {


            double bookingFeesPer = Double.parseDouble(orderDetails.getFeesForBookingPerc());


            double tempTotal = roundDouble(priceActivity - refValueSet);


            feesForBooking = roundDouble(((tempTotal * bookingFeesPer) / 100));

            tootalBooking = roundDouble(tempTotal + feesForBooking);

        } else {


            double bookingFeesPer = Double.parseDouble(orderDetails.getFeesForBookingPerc());

            feesForBooking = Double.parseDouble(orderDetails.getFeesForBooking());


            feesForBooking = roundDouble(((priceActivity * bookingFeesPer) / 100));

            tootalBooking = roundDouble(priceActivity + feesForBooking);
        }


        customTextViewRecFeesFBookingPrice.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(feesForBooking));


        customTextViewNewAmountValue.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(roundDouble(tootalBooking - refValueSet)));


        if (orderDetails.getIsReferral().equalsIgnoreCase("1")) {
            linearLayoutReferralAmount.setVisibility(View.VISIBLE);
            customTextViewReferralValue.setText("- " + currencyVisitorBooking + " " + BaseFragment.convertFormat(referVal));
            tootalBooking = BaseFragment.roundDouble(tootalBooking - referVal);
        } else {
            linearLayoutReferralAmount.setVisibility(View.GONE);
        }


        customTextViewRecTotalBooking.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(tootalBooking));


        String equipmentStatus = orderDetailsData.getCheckListStatus().equalsIgnoreCase("1") ? getString(R.string.check_equipment) :
                orderDetailsData.getCheckListStatus().equalsIgnoreCase("2") ? getString(R.string.check_equipment_missing_equipment) :
                        orderDetailsData.getCheckListStatus().equalsIgnoreCase("3") ? getString(R.string.check_equipment_completed) : getString(R.string.check_equipment);

        if (orderStatus == 2) {
            equipmentStatus = getString(R.string.check_equipment_read);
        }

        customTextViewCheckEquipmets.setText(equipmentStatus);

        Drawable drawable = orderDetailsData.getCheckListStatus().equalsIgnoreCase("1") ? getResources().getDrawable(R.drawable.drawable_select_date_select_time) :
                orderDetailsData.getCheckListStatus().equalsIgnoreCase("2") ? getResources().getDrawable(R.drawable.drawable_select_date_select_time_yello) :
                        orderDetailsData.getCheckListStatus().equalsIgnoreCase("3") ? getResources().getDrawable(R.drawable.drawable_select_date_select_time_green) : getResources().getDrawable(R.drawable.drawable_select_date_select_time);

        if (orderStatus == 2) {
            drawable = getResources().getDrawable(R.drawable.drawable_select_date_select_time_green);
        }

        customTextViewCheckEquipmets.setBackgroundDrawable(drawable);

        int color = orderDetailsData.getCheckListStatus().equalsIgnoreCase("1") ? getResources().getColor(R.color.buttonColor) :
                orderDetailsData.getCheckListStatus().equalsIgnoreCase("2") ? getResources().getColor(R.color.yellow) :
                        orderDetailsData.getCheckListStatus().equalsIgnoreCase("3") ? getResources().getColor(R.color.green) : getResources().getColor(R.color.buttonColor);

        if (orderStatus == 2) {
            color = getResources().getColor(R.color.green);
        }


        customTextViewCheckEquipmets.setTextColor(color);
    }

    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        presenter.openMapScreen(orderDetailsData);
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
                presenter.openExpDetails(experienceDetails.getExperience());
            }
        });
    }
}
