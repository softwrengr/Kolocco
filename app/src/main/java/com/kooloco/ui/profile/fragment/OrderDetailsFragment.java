package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 25/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Review;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.presenter.OrderDetailsPresenter;
import com.kooloco.ui.profile.view.OrderDetailsView;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsWithPriceAdapter;
import com.kooloco.util.ReadMore;
import com.kooloco.util.StaticMap;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class OrderDetailsFragment extends BaseFragment<OrderDetailsPresenter, OrderDetailsView> implements OrderDetailsView {


    List<Review> reviewsData;


    double priceActivity;
    double feesForBooking = 0;

    double tootalBooking = 0.0;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.imageViewChat)
    ImageView imageViewChat;
    @BindView(R.id.imageViewCall)
    ImageView imageViewCall;
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
    @BindView(R.id.ratingBarList)
    AppCompatRatingBar ratingBarList;
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
    @BindView(R.id.customTextViewRatingCount)
    AppCompatTextView customTextViewRatingCount;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.recyclerReviews)
    RecyclerView recyclerReviews;
    @BindView(R.id.textViewReadAll)
    AppCompatTextView textViewReadAll;
    @BindView(R.id.lineraLayoutPanding)
    LinearLayout lineraLayoutPanding;
    @BindView(R.id.imageViewProfileRat)
    ImageView imageViewProfileRat;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.ratingBarRat)
    AppCompatRatingBar ratingBarRat;
    @BindView(R.id.customTextViewDesc)
    AppCompatTextView customTextViewDesc;
    @BindView(R.id.lineraLayoutComplate)
    LinearLayout lineraLayoutComplate;
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
    @BindView(R.id.buttonCancel)
    AppCompatButton buttonCancel;
    @BindView(R.id.buttonPayNow)
    AppCompatButton buttonPayNow;
    @BindView(R.id.linearLayoutButton)
    LinearLayout linearLayoutButton;
    @BindView(R.id.customTextViewStatus)
    AppCompatTextView customTextViewStatus;
    @BindView(R.id.linearLayoutStatus)
    LinearLayout linearLayoutStatus;
    @BindView(R.id.linearLayoutPaymentStaus)
    LinearLayout linearLayoutPaymentStaus;
    @BindView(R.id.textViewTime)
    AppCompatTextView textViewTime;
    @BindView(R.id.textViewBookingFeesText)
    AppCompatTextView textViewBookingFeesText;

    @BindView(R.id.linearLayoutButtonRat)
    LinearLayout linearLayoutButtonRat;
    @BindView(R.id.customTextViewReason)
    AppCompatTextView customTextViewReason;
    @BindView(R.id.linerLayoutCancelReason)
    LinearLayout linerLayoutCancelReason;
    @BindView(R.id.viewCancellation)
    View viewCancellation;
    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;
    @BindView(R.id.customTextViewReferralValue)
    AppCompatTextView customTextViewReferralValue;
    @BindView(R.id.linearLayoutReferralAmount)
    LinearLayout linearLayoutReferralAmount;

    private Order order;

    private OrderDetails orderDetailsData;

    @Inject
    Session session;

    @Override
    protected int createLayout() {
        return R.layout.fragment_order_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OrderDetailsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (order != null) {
            presenter.getData(order.getId());
        }
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void setData(OrderDetails orderDetails) {

        String currencyVisitorBooking = orderDetails.getVisitorCurrency();

        setDataExperence(orderDetails.getExpereinceNew());

        orderDetailsData = orderDetails;
        Integer orderStatus = orderDetails.getStatus();

        reviewsData = new ArrayList<>();


        if (orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.ESCROW)) {
            customTextViewPaymentStatus.setText(getActivity().getResources().getString(R.string.order_history_escrow));
            customTextViewPaymentStatus.setTextColor(getActivity().getResources().getColor(R.color.yellow));
            linearLayoutButton.setVisibility(View.VISIBLE);
            imageViewCall.setVisibility((orderStatus != 0) ? View.VISIBLE : View.INVISIBLE);
            imageViewChat.setVisibility((orderStatus != 0) ? View.VISIBLE : View.INVISIBLE);
            linearLayoutStatus.setVisibility(View.VISIBLE);
            linearLayoutButtonRat.setVisibility(View.GONE);

        } else if (orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.CANCEL)) {
            customTextViewPaymentStatus.setText(R.string.order_status_cancelled);
            customTextViewPaymentStatus.setTextColor(getActivity().getResources().getColor(R.color.red));
            linearLayoutButton.setVisibility(View.GONE);
            imageViewCall.setVisibility(View.INVISIBLE);
            imageViewChat.setVisibility(View.INVISIBLE);
            linearLayoutStatus.setVisibility(View.GONE);
            linearLayoutButtonRat.setVisibility(View.GONE);

        } else if (orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.REFUND)) {
            customTextViewPaymentStatus.setText(getActivity().getResources().getString(R.string.order_payment_status_refunded));
            customTextViewPaymentStatus.setTextColor(getActivity().getResources().getColor(R.color.yellow));

            linearLayoutButton.setVisibility(View.GONE);
            imageViewCall.setVisibility(View.INVISIBLE);
            imageViewChat.setVisibility(View.INVISIBLE);
            linearLayoutStatus.setVisibility(View.VISIBLE);
            linearLayoutButtonRat.setVisibility(View.GONE);

        } else {
            customTextViewPaymentStatus.setText(getActivity().getResources().getString(R.string.order_history_paid));
            customTextViewPaymentStatus.setTextColor(getActivity().getResources().getColor(R.color.green));
            linearLayoutButton.setVisibility(View.GONE);
            imageViewCall.setVisibility(View.INVISIBLE);
            imageViewChat.setVisibility(View.INVISIBLE);
            linearLayoutStatus.setVisibility(View.GONE);
            linearLayoutButtonRat.setVisibility(orderDetails.getIsRated().equalsIgnoreCase("0") ? View.VISIBLE : View.GONE);
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
            case 4:
                status = Common.OrderDetails.DECLINE;
                statusText = getString(R.string.order_status_decline);
                break;
            case 5:
                status = Common.OrderDetails.CANCELED;
                statusText = getString(R.string.order_status_cancelled);
                break;
            default:
                status = Common.OrderDetails.PENDING;
                statusText = getString(R.string.order_status_pending);

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
        } else if (status.equalsIgnoreCase(Common.OrderDetails.REJECTED) || status.equalsIgnoreCase(Common.OrderDetails.DECLINE) || status.equalsIgnoreCase(Common.OrderDetails.CANCELED)) {
            customTextViewStatus.setTextColor(getActivity().getResources().getColor(R.color.red));
        }

        if (status.equalsIgnoreCase(Common.OrderDetails.DECLINE)) {
            linearLayoutPaymentStaus.setVisibility(View.GONE);

            linearLayoutButton.setVisibility(View.GONE);
            imageViewCall.setVisibility(View.INVISIBLE);
            imageViewChat.setVisibility(View.INVISIBLE);

        } else {
            linearLayoutPaymentStaus.setVisibility(View.VISIBLE);
        }

        if (orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.ESCROW)) {
            lineraLayoutPanding.setVisibility(View.GONE);
            lineraLayoutComplate.setVisibility(View.GONE);

/*            customTextViewRatingCount.setText("(" + reviewsData.size() + ")");
            textViewReadAll.setText("Read all " + reviewsData.size() + " reviews");

            List<Review> reviews = new ArrayList<>();

            if (reviewsData.size() >= 2) {
                for (int i = 0; i < 2; i++) {
                    reviews.add(reviewsData.get(i));
                }
            } else {
                reviews.addAll(reviewsData);
            }
            recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerReviews.setAdapter(new DashboardReviewAdapter(getActivity(), reviews));*/

        } else if (orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.CANCEL)) {
            lineraLayoutPanding.setVisibility(View.GONE);
            lineraLayoutComplate.setVisibility(View.GONE);
        } else {

            lineraLayoutPanding.setVisibility(View.GONE);
            lineraLayoutComplate.setVisibility(View.GONE);

            if (orderDetails.getIsReview().equalsIgnoreCase("1")) {
                lineraLayoutComplate.setVisibility(View.VISIBLE);
            } else {
                lineraLayoutComplate.setVisibility(View.GONE);
            }

        }


        customTextViewLocalName.setText(orderDetails.getFirstname() + " " + orderDetails.getLastname());

        Picasso.with(getActivity()).load(orderDetails.getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);

        imageViewProfile.setOnClickListener(view -> imageOpenZoom(orderDetails.getProfileImage()));
        customTextViewRatingValue.setText(orderDetails.getRate());

        //Set Meeting address section start

        String staticMapUrl = StaticMap.getUrl(getActivity(), orderDetails.getMeetingLatitude(), orderDetails.getMeetingLongitude(), URLFactory.MEETING);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);


        customTextViewMeetingLocation.setText(orderDetails.getMeetingAddress());


        String timeText = "";
        if (orderDetails.getInMultiDay().equalsIgnoreCase("1")) {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a");
        } else {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a") + " " + getString(R.string.sch_to) + " " +  TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getEndTime(), "HH:mm:ss", "hh:mm a");
        }

        textViewTime.setText(orderDetails.getInMultiDay().equalsIgnoreCase("1") ? getActivity().getResources().getString(R.string.fil_start_time) : getActivity().getResources().getString(R.string.appointment_summery_time));


        customTextViewTime.setText(timeText);

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


        linearLayoutRefAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5) ? View.VISIBLE : View.GONE);
        textViewBookingFeesText.setText(orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5) ? getString(R.string.booking_fee_adapted) : getString(R.string.booking_fee));

        //As per client comment gone visibility
        linearLayoutNewAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5) ? View.GONE : View.GONE);
        viewNewAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5) ? View.GONE : View.GONE);


        double refValueSet = 0.0;

        double referVal = 0.0;

        try {
            referVal = Double.parseDouble(orderDetails.getReferralAmount());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


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
        } else if ((orderStatus == 5)) {

            Double tootleAmount = Double.parseDouble(orderDetails.getActivityTootal());

            Double discountPer = Double.parseDouble(orderDetails.getCancelPer());

            refValueSet = roundDouble(tootleAmount * (discountPer / 100));

            customTextViewRefundValue.setText("- " + currencyVisitorBooking + " " + BaseFragment.convertFormat(refValueSet));

            referVal = roundDouble(referVal - roundDouble(referVal * (discountPer / 100)));
        }


        customTextViewNewAmountValue.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(roundDouble(tootalBooking - refValueSet)));


        if (orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5)) {


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


        if (orderDetails.getIsReferral().equalsIgnoreCase("1")) {
            linearLayoutReferralAmount.setVisibility(View.VISIBLE);
            customTextViewReferralValue.setText("- " + currencyVisitorBooking + " " + BaseFragment.convertFormat(referVal));
            tootalBooking = BaseFragment.roundDouble(tootalBooking - referVal);
        } else {
            linearLayoutReferralAmount.setVisibility(View.GONE);
        }

        customTextViewRecTotalBooking.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(tootalBooking));


        buttonPayNow.setVisibility((orderStatus == 2 && orderDetails.getIsReceipt().equalsIgnoreCase("1") && orderDetails.getPaymentStatus().equalsIgnoreCase("escrow")) ? View.VISIBLE : View.GONE);
        buttonCancel.setVisibility((orderStatus <= 1 && orderDetails.getPaymentStatus().equalsIgnoreCase("escrow")) ? View.VISIBLE : View.GONE);

        if (orderDetails.getIsReview().equalsIgnoreCase("1")) {
            if (orderDetails.getReview() != null) {
                Review review = orderDetails.getReview();

                if (!review.getImageUrl().isEmpty())
                    Picasso.with(getActivity()).load(review.getImageUrl()).transform(new CircleTransform()).error(R.drawable.user_round).placeholder(R.drawable.user_round).into(imageViewProfileRat);

                customTextViewName.setText(review.getName());
                customTextViewDesc.setText(review.getReview());

                Float rate = 0.0f;
                if (!review.getRate().isEmpty()) {
                    try {
                        rate = Float.parseFloat(review.getRate());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        rate = 0.0f;
                    }

                }

                ratingBarRat.setRating(rate);

                if (customTextViewDesc.length() >= 100) {
                    ReadMore.less = getActivity().getString(R.string.readless);
                    ReadMore.more = getActivity().getString(R.string.readmore);
                    ReadMore.makeTextViewResizable(customTextViewDesc, 2, getString(R.string.readmore), true);
                }

            }
        }


        if (orderStatus == 5) {
            linerLayoutCancelReason.setVisibility(orderDetailsData.getCancelReason().isEmpty() ? View.GONE : View.VISIBLE);
            viewCancellation.setVisibility(orderDetailsData.getCancelReason().isEmpty() ? View.GONE : View.VISIBLE);
            customTextViewReason.setText(orderDetails.getCancelReason());
        }

      /*  if (buttonPayNow.getVisibility() == View.VISIBLE) {
            if (orderDetails.getIsReceipt().equalsIgnoreCase("0")) {
                buttonPayNow.setVisibility(View.GONE);
            }
        }*/

        buttonPayNow.setText(getString(orderDetailsData.getIsObjected().equalsIgnoreCase("1") ? R.string.objection_has_been_raised : R.string.button_see_receipt));
    }

    @OnClick({R.id.textViewReadAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textViewReadAll:
                textViewReadAll.setVisibility(View.GONE);
                break;
        }
    }


    @OnClick({R.id.buttonCancel, R.id.buttonPayNow, R.id.buttonGiveRate})
    public void onViewClickedButton(View view) {
        switch (view.getId()) {
            case R.id.buttonCancel:
                presenter.openOrderCancel(order.getId());
                break;
            case R.id.buttonPayNow:
                //  presenter.openOrderCancel();
                //   showMessage(getActivity().getResources().getString(R.string.sucess));

                if (orderDetailsData.getIsObjected().equalsIgnoreCase("1")) {
                    presenter.openReciveObjectionDetails(orderDetailsData.getObjectionId());
                } else {
                    presenter.openReceipt(order.getId());
                }

                break;

            case R.id.buttonGiveRate:
                presenter.openRating(order.getId());
                break;
        }
    }


    @OnClick({R.id.imageViewBack, R.id.imageViewChat, R.id.imageViewCall})
    public void onViewClickedTool(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageViewChat:

                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(orderDetailsData.getLocalId());
                receiverData.setName(orderDetailsData.getFirstname() + " " + orderDetailsData.getLastname());
                receiverData.setImageUrl(orderDetailsData.getProfileImage());
                receiverData.setDeviceType(orderDetailsData.getDeviceType());
                receiverData.setDeviceToken(orderDetailsData.getDeviceToken());
                receiverData.setOrderId(order.getId());

                presenter.opnChat(receiverData);
                break;
            case R.id.imageViewCall:
                if (orderDetailsData != null) {
                    openCallIntent(orderDetailsData.getCountryCode(), orderDetailsData.getPhone());
                }
                break;
        }
    }

    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        presenter.openMapScreen(orderDetailsData);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventBusAction action) {
        if (action == EventBusAction.ORDERDETAILSVISITOR) {
            if (order != null) {
                presenter.getData(order.getId());
            }
        }
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
}
