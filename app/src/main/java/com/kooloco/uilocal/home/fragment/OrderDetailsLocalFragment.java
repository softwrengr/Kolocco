package com.kooloco.uilocal.home.fragment;
/**
 * Created by hlink44 on 5/10/17.
 */

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.constant.EventBusAction;
import com.kooloco.constant.LocalOrderAction;
import com.kooloco.data.URLFactory;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.model.Review;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsWithPriceAdapter;
import com.kooloco.ui.visitor.dashboard.adapter.RateReviewAdapter;
import com.kooloco.uilocal.home.presenter.OrderDetailsLocalPresenter;
import com.kooloco.uilocal.home.view.OrderDetailsLocalView;
import com.kooloco.util.ReadMore;
import com.kooloco.util.StaticMap;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class OrderDetailsLocalFragment extends BaseFragment<OrderDetailsLocalPresenter, OrderDetailsLocalView> implements OrderDetailsLocalView {

    List<Review> reviewsData;
    Dialog dialog;
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
    @BindView(R.id.customTextViewDate)
    AppCompatTextView customTextViewDate;
    @BindView(R.id.customTextViewTime)
    AppCompatTextView customTextViewTime;
    @BindView(R.id.customTextViewDurationEdit)
    AppCompatTextView customTextViewDurationEdit;
    @BindView(R.id.customTextViewDuration)
    AppCompatTextView customTextViewDuration;
    @BindView(R.id.customTextViewParticipants)
    AppCompatTextView customTextViewParticipants;
    @BindView(R.id.customTextViewMeetingAddressEdit)
    AppCompatTextView customTextViewMeetingAddressEdit;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.imageViewStaticMap)
    ImageView imageViewStaticMap;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.linearLayoutSportEquipments)
    LinearLayout linearLayoutSportEquipments;
    @BindView(R.id.customTextViewRatingCount)
    AppCompatTextView customTextViewRatingCount;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.recyclerReviews)
    RecyclerView recyclerReviews;
    @BindView(R.id.buttonResetFilter)
    AppCompatButton buttonResetFilter;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    @BindView(R.id.textViewReadAll)
    AppCompatTextView textViewReadAll;
    @BindView(R.id.viewHide)
    View viewHide;
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
    @BindView(R.id.customTextViewNTMY)
    AppCompatTextView customTextViewNTMY;
    @BindView(R.id.linearLayoutNice)
    LinearLayout linearLayoutNice;
    @BindView(R.id.customTextViewAboutMe)
    AppCompatTextView customTextViewAboutMe;
    @BindView(R.id.linearLayoutAboutMe)
    LinearLayout linearLayoutAboutMe;
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
    @BindView(R.id.customTextAccept)
    AppCompatTextView customTextAccept;
    @BindView(R.id.customTextDecline)
    AppCompatTextView customTextDecline;
    @BindView(R.id.customTextModify)
    AppCompatTextView customTextModify;
    @BindView(R.id.linearLayoutPendingButton)
    LinearLayout linearLayoutPendingButton;
    @BindView(R.id.customTextChat)
    AppCompatTextView customTextChat;
    @BindView(R.id.customTextComplete)
    AppCompatTextView customTextComplete;
    @BindView(R.id.customTextCancel)
    AppCompatTextView customTextCancel;
    @BindView(R.id.linearLayoutAcceptButton)
    LinearLayout linearLayoutAcceptButton;
    @BindView(R.id.customTextChatNew)
    AppCompatTextView customTextChatNew;
    @BindView(R.id.imageViewSendPayment)
    ImageView imageViewSendPayment;
    @BindView(R.id.customTextSendPaymentReq)
    AppCompatTextView customTextSendPaymentReq;
    @BindView(R.id.linearLayoutComplateButton)
    LinearLayout linearLayoutComplateButton;
    @BindView(R.id.imageView)
    PorterShapeImageView imageView;
    @BindView(R.id.customTextViewTitle)
    AppCompatTextView customTextViewTitle;
    @BindView(R.id.textViewRateCount)
    AppCompatTextView textViewRateCount;
    @BindView(R.id.imageViewExp)
    ImageView imageViewExp;
    @BindView(R.id.textViewExpPrice)
    AppCompatTextView textViewExpPrice;
    @BindView(R.id.linearLayoutExpRoot)
    LinearLayout linearLayoutExpRoot;
    @BindView(R.id.customTextViewLocationActivity)
    AppCompatTextView customTextViewLocationActivity;
    Unbinder unbinder;
    @BindView(R.id.ratingBarList)
    AppCompatRatingBar ratingBarList;
    @BindView(R.id.textViewTime)
    AppCompatTextView textViewTime;
    @BindView(R.id.linearLayoutChatBlock)
    LinearLayout linearLayoutChatBlock;
    @BindView(R.id.customTextViewCheckEquipmets)
    AppCompatTextView customTextViewCheckEquipmets;
    @BindView(R.id.imageViewCall)
    ImageView imageViewCall;

    @BindView(R.id.imageViewChat)
    ImageView imageViewChat;

    @BindView(R.id.viewEquipment)
    View viewEquipment;

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


    private Order order;

    double priceActivity;
    double commission = 0;
    double tootalBooking = 0.0;

    OrderDetails orderDetailsData;
    int pageRate = 1;
    int tootalCount = 0;
    boolean isCallApi = true;
    RateReviewAdapter rateReviewAdapter;
    List<Review> reviews;


    @Override
    protected int createLayout() {
        return R.layout.fragment_local_order_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OrderDetailsLocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (order != null) {
            presenter.getData(order.getId());
            //It is used to set review data
            if (reviews == null) {
                reviews = new ArrayList<>();
            }
            rateReviewAdapter = new RateReviewAdapter(getActivity(), reviews);
            recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerReviews.setAdapter(rateReviewAdapter);

        }
    }

    @OnClick({R.id.textViewReadAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textViewReadAll:
                callApi(false);
                break;
        }
    }


    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }


    @OnClick({R.id.customTextAccept, R.id.customTextDecline, R.id.customTextModify, R.id.customTextChat, R.id.customTextComplete, R.id.customTextCancel, R.id.customTextChatNew, R.id.customTextSendPaymentReq, R.id.imageViewSendPayment})
    public void onViewClickedAction(View view) {
        switch (view.getId()) {
            case R.id.customTextAccept:
                localOrderAction(order, LocalOrderAction.ACCEPT, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        EventBus.getDefault().post(EventBusAction.PENDINGREFRESE);
                        EventBus.getDefault().post(EventBusAction.ACCEPTREFRESE);
                        presenter.openAcceptOrderDetails(order);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
                break;
            case R.id.customTextDecline:
                localOrderAction(order, LocalOrderAction.REJECT, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        deleteRecentChat(order.getId());
                        EventBus.getDefault().post(EventBusAction.PENDINGREFRESE);
                        goBack();
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
                break;
            case R.id.customTextModify:
                showModifyDialog(order);
                break;
            case R.id.customTextChat:
            case R.id.customTextChatNew:

                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(orderDetailsData.getUserId());
                receiverData.setName(orderDetailsData.getFirstname() + " " + orderDetailsData.getLastname());
                receiverData.setImageUrl(orderDetailsData.getProfileImage());
                receiverData.setDeviceType(orderDetailsData.getDeviceType());
                receiverData.setDeviceToken(orderDetailsData.getDeviceToken());
                receiverData.setOrderId(order.getId());

                presenter.openChat(receiverData);
                break;
            case R.id.customTextComplete:
                localOrderAction(order, LocalOrderAction.COMPLETE, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        EventBus.getDefault().post(EventBusAction.ACCEPTREFRESE);
                        presenter.openRating(order);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
                break;
            case R.id.customTextCancel:
                showDialog();
                break;
            case R.id.customTextSendPaymentReq:
            case R.id.imageViewSendPayment:
                if (orderDetailsData != null) {
                    if (orderDetailsData.getIsObjected().equalsIgnoreCase("1")) {
                        presenter.openReceiveObjectionDetails(orderDetailsData.getObjectionId());
                    } else {
                        localOrderAction(order, LocalOrderAction.SHOWPAYMENTREQUEST, new CallBackOrderAction() {
                            @Override
                            public void onSuccess(Response response) {
                                showSendPaymentRequestDialog();
                            }

                            @Override
                            public void onError(String message) {

                            }
                        });
                    }
                }
                break;
        }
    }

    public void showDialog() {

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_cancel, null, false);

        AppCompatButton appCompatButtonNo = view.findViewById(R.id.buttonNo);
        AppCompatButton appCompatButtonYes = view.findViewById(R.id.buttonYes);

        appCompatButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        appCompatButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                localOrderAction(order, LocalOrderAction.CANCEL, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        dialog.dismiss();
                        EventBus.getDefault().post(EventBusAction.ACCEPTREFRESE);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });

        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialog.setContentView(view);

        dialog.show();


    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void setData(OrderDetails orderDetails) {
        orderDetailsData = orderDetails;
        Integer orderStatus = orderDetails.getStatus();
        reviewsData = Temp.getReviews();
        if (orderStatus == 0) {
            lineraLayoutPanding.setVisibility(View.VISIBLE);

            ratingBarList.setRating(Float.parseFloat(orderDetails.getVisitorRate()));

            isCallApi = true;
            callApi(true);

        } else {
            lineraLayoutPanding.setVisibility(View.GONE);
        }

        linearLayoutAboutMe.setVisibility((orderStatus == 0) ? View.VISIBLE : View.GONE);

//        linearLayoutNice.setVisibility((orderStatus == 2 || orderStatus == 1) ? View.VISIBLE : View.GONE);

        //linearLayoutSportEquipments.setVisibility((orderStatus == 1 || orderStatus == 2) ? View.VISIBLE : View.GONE);

        if ((orderStatus == 2 && orderDetails.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.PAID)) || orderStatus == 5) {
            linearLayoutChatBlock.setVisibility(View.GONE);

            linearLayoutButtonRat.setVisibility(orderDetails.getIsRated().equalsIgnoreCase("0") ? View.VISIBLE : View.GONE);

            if (orderStatus == 5) {
                linearLayoutButtonRat.setVisibility(View.GONE);
            }

        } else {
            linearLayoutChatBlock.setVisibility((orderStatus == 0) ? View.GONE : View.VISIBLE);

            linearLayoutButtonRat.setVisibility(View.GONE);
        }


        linearLayoutSportEquipments.setVisibility((orderStatus == 0) ? View.GONE : View.VISIBLE);
        viewEquipment.setVisibility((orderStatus == 0) ? View.GONE : View.VISIBLE);


        String moreAbout = orderDetails.getMoreAbout().trim();

        if (moreAbout.isEmpty()) {
            linearLayoutAboutMe.setVisibility(View.GONE);
        } else {
            linearLayoutAboutMe.setVisibility(View.VISIBLE);
        }

        customTextViewAboutMe.setText(orderDetails.getMoreAbout());

        linearLayoutPendingButton.setVisibility((orderStatus == 0) ? View.VISIBLE : View.GONE);
        linearLayoutAcceptButton.setVisibility((orderStatus == 1) ? View.VISIBLE : View.GONE);
        linearLayoutComplateButton.setVisibility((orderStatus == 2) ? View.VISIBLE : View.GONE);

        customTextViewLocalName.setText(orderDetails.getFirstname() + " " + orderDetails.getLastname());

        Picasso.with(getActivity()).load(orderDetails.getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        imageViewProfile.setOnClickListener(view -> imageOpenZoom(orderDetails.getProfileImage()));

        customTextViewRatingValue.setText(orderDetails.getVisitorRate());


        //Set Meeting address section start

        String staticMapUrl = StaticMap.getUrl(getActivity(), orderDetails.getMeetingLatitude(), orderDetails.getMeetingLongitude(), URLFactory.MEETING);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);

        customTextViewLocationActivity.setText(orderDetails.getMeetingAddress());


        //Set Meeting address section end

        //Set Duration section start


        String timeText = "";
        if (orderDetails.getInMultiDay().equalsIgnoreCase("1")) {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a");
        } else {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a") + " " + getString(R.string.sch_to) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getEndTime(), "HH:mm:ss", "hh:mm a");
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


        //Set Duration section end

        setDataExperence(orderDetails.getExpereinceNew());


        customTextViewParticipants.setText("" + orderDetails.getParticipant().size());

        priceActivity = roundDouble(Double.parseDouble(orderDetails.getPrice()) * orderDetails.getParticipant().size());

        customTextViewRecTotalAct.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(priceActivity));


        recyclerAddParticipants.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        List<AddParticipants> addParticipantsList = new ArrayList<>();

        int i = 1;

        for (AddParticipants addParticipants : orderDetails.getParticipant()) {
            if (addParticipants.getUserId().equalsIgnoreCase(orderDetails.getUserId())) {

                addParticipants.setEmail(orderDetails.getFirstname() + " " + orderDetails.getLastname());

                addParticipantsList.add(addParticipants);
            } else {
                addParticipants.setEmail(getString(R.string.participant) + " " + i);
                addParticipantsList.add(addParticipants);
            }
            i++;
        }

        recyclerAddParticipants.setAdapter(new AddParticipantsWithPriceAdapter(getActivity(), addParticipantsList, roundDouble(priceActivity)));

        linearLayoutRefAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5) ? View.VISIBLE : View.GONE);

        //As per client comment gone visibility
        linearLayoutNewAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5) ? View.GONE : View.GONE);
        viewNewAmount.setVisibility(orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5) ? View.GONE : View.GONE);


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

            customTextViewRefundValue.setText("- " + BaseActivity.currency + " " + BaseFragment.convertFormat(refValueSet));
        } else if ((orderStatus == 5)) {

            Double tootleAmount = Double.parseDouble(orderDetails.getActivityTootal());

            Double discountPer = Double.parseDouble(orderDetails.getCancelPer());

            refValueSet = roundDouble(tootleAmount * (discountPer / 100));

            customTextViewRefundValue.setText("- " + BaseActivity.currency + " " + BaseFragment.convertFormat(refValueSet));

        }


        if (orderDetails.getIsObjDetails().equalsIgnoreCase("1") || (orderStatus == 5)) {


            double commissionPer = Double.parseDouble(orderDetails.getCommisionPerc());


            double tempTotal = roundDouble(priceActivity - refValueSet);


            commission = roundDouble(((tempTotal * commissionPer) / 100));

            tootalBooking = roundDouble(tempTotal - commission);


        } else {

            commission = Double.parseDouble(orderDetails.getCommisionValue());

            tootalBooking = roundDouble(priceActivity - commission);
        }


        customTextViewRecFeesFBookingPrice.setText("- " + BaseActivity.currency + " " + BaseFragment.convertFormat(commission));

        customTextViewRecTotalBooking.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(tootalBooking));


        customTextViewNewAmountValue.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(roundDouble(roundDouble(priceActivity) - refValueSet)));

        if (orderStatus == 2 && orderDetails.getPaymentStatus().equalsIgnoreCase("Paid")) {
            linearLayoutPendingButton.setVisibility(View.GONE);
            linearLayoutAcceptButton.setVisibility(View.GONE);
            linearLayoutComplateButton.setVisibility(View.GONE);
        }

        EventBus.getDefault().post(EventBusAction.ACCEPTREFRESE);
        EventBus.getDefault().post(EventBusAction.PENDINGREFRESE);

        if (orderDetails.getIsReview().equalsIgnoreCase("1")) {
            lineraLayoutComplate.setVisibility(View.VISIBLE);
        } else {
            lineraLayoutComplate.setVisibility(View.GONE);
        }


        if (orderDetails.getIsReview().equalsIgnoreCase("1")) {
            if (orderDetails.getReview() != null) {
                Review review = orderDetails.getReview();

                if (!review.getImageUrl().isEmpty())
                    Picasso.with(getActivity()).load(review.getImageUrl()).transform(new CircleTransform()).into(imageViewProfileRat);

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


        String equipmentStatus = orderDetailsData.getCheckListStatus().equalsIgnoreCase("1") ? getString(R.string.check_equipment_local) :
                orderDetailsData.getCheckListStatus().equalsIgnoreCase("2") ? getString(R.string.check_equipment_missing_equipment_local) :
                        orderDetailsData.getCheckListStatus().equalsIgnoreCase("3") ? getString(R.string.check_equipment_completed_local) : getString(R.string.check_equipment_local);

        if (orderStatus == 2 || orderStatus == 5) {
            equipmentStatus = getString(R.string.check_equipment_read);
        }

        customTextViewCheckEquipmets.setText(equipmentStatus);


        Drawable drawable = orderDetailsData.getCheckListStatus().equalsIgnoreCase("1") ? getResources().getDrawable(R.drawable.drawable_select_date_select_time) :
                orderDetailsData.getCheckListStatus().equalsIgnoreCase("2") ? getResources().getDrawable(R.drawable.drawable_select_date_select_time_yello) :
                        orderDetailsData.getCheckListStatus().equalsIgnoreCase("3") ? getResources().getDrawable(R.drawable.drawable_select_date_select_time_green) : getResources().getDrawable(R.drawable.drawable_select_date_select_time);

        if (orderStatus == 2 || orderStatus == 5) {
            drawable = getResources().getDrawable(R.drawable.drawable_select_date_select_time_green);
        }

        customTextViewCheckEquipmets.setBackgroundDrawable(drawable);

        int color = orderDetailsData.getCheckListStatus().equalsIgnoreCase("1") ? getResources().getColor(R.color.buttonColor) :
                orderDetailsData.getCheckListStatus().equalsIgnoreCase("2") ? getResources().getColor(R.color.yellow) :
                        orderDetailsData.getCheckListStatus().equalsIgnoreCase("3") ? getResources().getColor(R.color.green) : getResources().getColor(R.color.buttonColor);

        if (orderStatus == 2 || orderStatus == 5) {
            color = getResources().getColor(R.color.green);
        }


        if (orderStatus == 5) {
            linerLayoutCancelReason.setVisibility(orderDetailsData.getCancelReason().isEmpty() ? View.GONE : View.VISIBLE);
            viewCancellation.setVisibility(orderDetailsData.getCancelReason().isEmpty() ? View.GONE : View.VISIBLE);
            customTextViewReason.setText(orderDetails.getCancelReason());
        }

        customTextViewCheckEquipmets.setTextColor(color);

        if (linearLayoutComplateButton.getVisibility() == View.VISIBLE) {
            customTextSendPaymentReq.setText(getResources().getString((orderDetails.getIsObjected().equalsIgnoreCase("1")) ? R.string.objection_has_been_raised : (orderDetails.getIsReceipt().equalsIgnoreCase("1")) ? R.string.payment_request_sent : R.string.send_payment_req_new));
        }

        imageViewCall.setVisibility(View.GONE);

    }

    private void callApi(boolean isFirst) {
        if (isCallApi) {
            if (isFirst) {
                pageRate = 1;
                reviews.clear();
            } else {
                pageRate = pageRate + 1;
            }
            presenter.getRating(pageRate, orderDetailsData.getUserId());
            isCallApi = false;
        }
    }

    @Override
    public void setDataRating(List<Review> data, int pg, int tootalCount) {
        if (pg == 1) {
            this.tootalCount = tootalCount;
        }

        if (data.size() != 0) {
            isCallApi = true;
        }

        reviews.addAll(data);

        if (linearLayoutNoData != null)
            linearLayoutNoData.setVisibility((reviews.size() == 0) ? View.VISIBLE : View.GONE);

        if (rateReviewAdapter != null) {
            rateReviewAdapter.notifyDataSetChanged();
        }

        setRatingData();
    }


    private void setRatingData() {

        customTextViewRatingCount.setText("(" + tootalCount + ")");

        if (textViewReadAll != null) {
            textViewReadAll.setVisibility(((tootalCount - reviews.size()) <= 0) ? View.GONE : View.VISIBLE);
            if (viewHide != null)
                viewHide.setVisibility(((tootalCount - reviews.size()) <= 0) ? View.VISIBLE : View.VISIBLE);

            textViewReadAll.setText(getString(R.string.read_all) + " " + (tootalCount - reviews.size()) + " " + getString(R.string.reviews));

        }

    }

    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        presenter.openMapScreen(orderDetailsData);
    }

    @OnClick({R.id.customTextViewDurationEdit, R.id.customTextViewMeetingAddressEdit, R.id.customTextViewCheckEquipmets, R.id.buttonGiveRate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonGiveRate:
                presenter.openRating(order);
                break;
            case R.id.customTextViewDurationEdit:
                if (orderDetailsData.getStatus() != 2) {
                    presenter.openDurationEdit(order);
                }
                break;
            case R.id.customTextViewMeetingAddressEdit:
                if (orderDetailsData.getStatus() != 2) {
                    presenter.openMeetingSpotEdit(order);
                }
                break;
            case R.id.customTextViewCheckEquipmets:
                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(orderDetailsData.getUserId());
                receiverData.setName(orderDetailsData.getFirstname() + " " + orderDetailsData.getLastname());
                receiverData.setImageUrl(orderDetailsData.getProfileImage());
                receiverData.setDeviceType(orderDetailsData.getDeviceType());
                receiverData.setDeviceToken(orderDetailsData.getDeviceToken());
                receiverData.setOrderId(order.getId());

                String chatStatus = "";

                if ((orderDetailsData.getStatus() == 2 && orderDetailsData.getPaymentStatus().equalsIgnoreCase(Common.OrderDetails.PAID)) || orderDetailsData.getStatus() == 5) {
                    chatStatus = "2";
                } else {
                    chatStatus = "1";
                }

                String orderStatus = (orderDetailsData.getStatus() == 2 || orderDetailsData.getStatus() == 5) ? "2" : "1";


                presenter.openCheckEquipments(order.getId(), receiverData, orderStatus, chatStatus);
                break;
        }
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
        unbinder.unbind();
    }

    public void onEvent(EventBusAction action) {
        if (action == EventBusAction.ORDERDETAILSLOCAL) {
            if (order != null) {
                presenter.getData(order.getId());
            }
        }
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

        Picasso.with(getActivity()).load(dataExperence.getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageViewExp);

        linearLayoutExpRoot.setOnClickListener(view -> {
            if (checkIsDeleteOrActive(dataExperence)) {
                presenter.openExpDetails(dataExperence);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.imageViewCall, R.id.imageViewChat})
    public void onClickChat(View view) {
        switch (view.getId()) {
            case R.id.imageViewCall:
                //openCallIntent();
                break;
            case R.id.imageViewChat:
                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(orderDetailsData.getUserId());
                receiverData.setName(orderDetailsData.getFirstname() + " " + orderDetailsData.getLastname());
                receiverData.setImageUrl(orderDetailsData.getProfileImage());
                receiverData.setDeviceType(orderDetailsData.getDeviceType());
                receiverData.setDeviceToken(orderDetailsData.getDeviceToken());
                receiverData.setOrderId(order.getId());

                presenter.openChat(receiverData);
                break;
        }
    }
}
