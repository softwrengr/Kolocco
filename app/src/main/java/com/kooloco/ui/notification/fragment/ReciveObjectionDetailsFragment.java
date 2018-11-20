package com.kooloco.ui.notification.fragment;
/**
 * Created by hlink44 on 26/9/17.
 */

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.notification.presenter.ReciveObjectionDetailsPresenter;
import com.kooloco.ui.notification.view.ReciveObjectionDetailsView;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsWithPriceAdapter;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ReciveObjectionDetailsFragment extends BaseFragment<ReciveObjectionDetailsPresenter, ReciveObjectionDetailsView> implements ReciveObjectionDetailsView {


    String notificationId = "";

    double priceActivity;
    double feesForBooking = 0;

    double tootalBooking = 0.0;
    double refValueSet = 0.0;
    double refValueTootal = 0.0;

    ObjectionDetails objectionDetails;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.imageViewChat)
    ImageView imageViewChat;
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
    @BindView(R.id.customTextViewLocationMetting)
    AppCompatTextView customTextViewLocationMetting;
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
    @BindView(R.id.customTextViewReason)
    AppCompatTextView customTextViewReason;
    @BindView(R.id.textView)
    AppCompatTextView textView;
    @BindView(R.id.recyclerAddParticipants)
    RecyclerView recyclerAddParticipants;
    @BindView(R.id.customTextViewRecTotalAct)
    AppCompatTextView customTextViewRecTotalAct;
    @BindView(R.id.customTextViewDiscountText)
    AppCompatTextView customTextViewDiscountText;
    @BindView(R.id.customTextViewDiscount)
    AppCompatTextView customTextViewDiscount;
    @BindView(R.id.customTextViewRefundValue)
    AppCompatTextView customTextViewRefundValue;
    @BindView(R.id.customTextViewRecFeesFBookingPrice)
    AppCompatTextView customTextViewRecFeesFBookingPrice;
    @BindView(R.id.customTextViewRecTotalBooking)
    AppCompatTextView customTextViewRecTotalBooking;
    @BindView(R.id.customTextObjAccept)
    AppCompatTextView customTextObjAccept;
    @BindView(R.id.customTextRequestToAdmin)
    AppCompatTextView customTextRequestToAdmin;
    @BindView(R.id.linearLayoutObjection)
    LinearLayout linearLayoutObjection;
    @BindView(R.id.textViewTime)
    AppCompatTextView textViewTime;
    @Inject
    Session session;

    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;


    @Override
    protected int createLayout() {
        return R.layout.fragment_recive_objection_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ReciveObjectionDetailsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData(notificationId);
    }

    @OnClick({R.id.imageViewBack, R.id.imageViewChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageViewChat:
                if (objectionDetails == null) {
                    return;
                }

                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(objectionDetails.getOrderDetails().getLocalId());
                receiverData.setName(objectionDetails.getOrderDetails().getFirstname() + " " + objectionDetails.getOrderDetails().getLastname());
                receiverData.setImageUrl(objectionDetails.getOrderDetails().getProfileImage());
                receiverData.setDeviceType(objectionDetails.getOrderDetails().getDeviceType());
                receiverData.setDeviceToken(objectionDetails.getOrderDetails().getDeviceToken());
                receiverData.setOrderId(objectionDetails.getOrderId());
                presenter.openChatFragment(receiverData);
                break;
        }
    }

    @Override
    public void setData(ObjectionDetails data) {
        objectionDetails = data;
        OrderDetails orderDetails = data.getOrderDetails();

        String currencyVisitorBooking = orderDetails.getVisitorCurrency();


        linearLayoutObjection.setVisibility(objectionDetails.getCrType().equalsIgnoreCase(Common.Visitor.MODIFY_OBJECTION) ? View.VISIBLE : View.GONE);

        customTextViewLocalName.setText(orderDetails.getFirstname() + " " + orderDetails.getLastname());

        Picasso.with(getActivity()).load(orderDetails.getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        customTextViewRatingValue.setText(orderDetails.getRate());
        customTextViewLocationMetting.setText(orderDetails.getMeetingAddress());

        setDataExperence(orderDetails.getExpereinceNew());

        String timeText = "";
        if (orderDetails.getInMultiDay().equalsIgnoreCase("1")) {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a");
        } else {
            timeText = TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getStartTime(), "HH:mm:ss", "hh:mm a") + " " + getString(R.string.sch_to) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal("" + orderDetails.getEndTime(), "HH:mm:ss", "hh:mm a");
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


        customTextViewParticipants.setText("" + orderDetails.getParticipant().size());


        priceActivity = roundDouble(Double.parseDouble(orderDetails.getPrice()) * orderDetails.getParticipant().size());


        customTextViewRecTotalAct.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(roundDouble(priceActivity)));


        String discountText = "" + customTextViewLocalName.getText().toString() + " ";

        if (!objectionDetails.getCrType().equalsIgnoreCase(Common.Visitor.MODIFY_OBJECTION)) {
            discountText = "" + session.getUser().getFirstname() + " " + session.getUser().getLastname() + " ";
        }

        Double tootleAmount = priceActivity;


        if (data.getObjectionType().equalsIgnoreCase("TR")) {

            discountText = discountText + getString(objectionDetails.getCrType().equalsIgnoreCase(Common.Local.RECEIPT_OBJECTION) ? R.string.give_total_refaund_new : R.string.give_total_refaund);

            refValueSet = tootleAmount;

        } else if (data.getObjectionType().equalsIgnoreCase("PR")) {
            discountText = discountText + getString(objectionDetails.getCrType().equalsIgnoreCase(Common.Local.RECEIPT_OBJECTION) ? R.string.rec_pr_flat_discount_new : R.string.rec_pr_flat_discount) + " (" + data.getObjectionPerc() + "%) :";

            Double discountPer = Double.parseDouble(data.getObjectionPerc());

            refValueSet = roundDouble(tootleAmount * (discountPer / 100));

        } else if (data.getObjectionType().equalsIgnoreCase("FA")) {
            discountText = discountText + getString(objectionDetails.getCrType().equalsIgnoreCase(Common.Local.RECEIPT_OBJECTION) ? R.string.rec_obj_flat_discount_new : R.string.rec_obj_flat_discount);

            Double discountFlat = Double.parseDouble(data.getObjectionAmount());

            refValueSet = discountFlat;
        }


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

        //Discount
        customTextViewDiscountText.setText(discountText);

        customTextViewDiscount.setText("- " + currencyVisitorBooking + " " + BaseFragment.convertFormat(refValueSet));

        refValueTootal = (tootleAmount - refValueSet);

        customTextViewRefundValue.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(refValueTootal));


        double bookingFeesPer = Double.parseDouble(orderDetails.getFeesForBookingPerc());


        double tempTotal = roundDouble(priceActivity - refValueSet);


        feesForBooking = roundDouble(((tempTotal * bookingFeesPer) / 100));

        tootalBooking = roundDouble(tempTotal + feesForBooking);


        tootalBooking = roundDouble(refValueTootal + feesForBooking);

        customTextViewRecFeesFBookingPrice.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(feesForBooking));

        customTextViewRecTotalBooking.setText(currencyVisitorBooking + " " + BaseFragment.convertFormat(tootalBooking));

        customTextViewReason.setText(orderDetails.getObjectionReason());


        customTextRequestToAdmin.setText(orderDetails.getRequestToAdmin().equalsIgnoreCase("1") ? getString(R.string.send_to_support) : getString(R.string.notification_request_admin));


    }

    @Override
    public void setId(String notificationId) {
        this.notificationId = notificationId;
    }

    @OnClick({R.id.customTextObjAccept, R.id.customTextRequestToAdmin})
    public void onClickAction(View view) {
        switch (view.getId()) {
            case R.id.customTextObjAccept:
                visitorSendObjectionAction(notificationId, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        if (objectionDetails.getOrderId() != null) {
                            deleteRecentChat(objectionDetails.getOrderId());
                        }
                        EventBus.getDefault().post(EventBusAction.NOTIFICATIONVISITOR);
                        if (objectionDetails != null) {
                            if (objectionDetails.getOrderId() != null) {
                                String orderId = objectionDetails.getOrderId();
                                presenter.openRatingScreen(orderId);
                            }
                        }
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
                break;
            case R.id.customTextRequestToAdmin:
                visitorSendObjectionActionAdmin(notificationId, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        customTextRequestToAdmin.setText(getString(R.string.send_to_support));

                        EventBus.getDefault().post(EventBusAction.NOTIFICATIONVISITOR);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
                break;
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
