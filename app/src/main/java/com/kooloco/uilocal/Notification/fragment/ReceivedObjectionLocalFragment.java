package com.kooloco.uilocal.Notification.fragment;
/**
 * Created by hlink44 on 9/10/17.
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
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsWithPriceAdapter;
import com.kooloco.uilocal.Notification.presenter.ReceivedObjectionLocalPresenter;
import com.kooloco.uilocal.Notification.view.ReceivedObjectionLocalView;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ReceivedObjectionLocalFragment extends BaseFragment<ReceivedObjectionLocalPresenter, ReceivedObjectionLocalView> implements ReceivedObjectionLocalView {

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
    AppCompatTextView customTextViewDiscountImage;
    @BindView(R.id.customTextViewRefundValue)
    AppCompatTextView customTextViewRefundValue;
    @BindView(R.id.customTextViewRecFeesFBookingPrice)
    AppCompatTextView customTextViewRecFeesFBookingPrice;
    @BindView(R.id.customTextViewRecTotalBooking)
    AppCompatTextView customTextViewRecTotalBooking;
    @BindView(R.id.customTextAccept)
    AppCompatTextView customTextAccept;
    @BindView(R.id.customTextModify)
    AppCompatTextView customTextModify;
    @BindView(R.id.linearLayoutPendingButton)
    LinearLayout linearLayoutPendingButton;
    @BindView(R.id.textViewTime)
    AppCompatTextView textViewTime;
    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;

    @Inject
    Session session;


    private String notificationId = "";

    double priceActivity;
    int feesForBooking = 0;

    double tootalBooking = 0.0;
    double commission = 0.0, commissionPer = 0.0;
    double refValueSet = 0.0;
    double refValueTootal = 0.0;
    OrderDetails orderDetails;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_recive_objection_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ReceivedObjectionLocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        presenter.getData(notificationId);
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }


    @OnClick({R.id.customTextAccept, R.id.customTextModify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customTextAccept:

                localSendObjectionAction(notificationId, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        if (orderDetails != null) {
                            deleteRecentChat(orderDetails.getId());
                        }
                        goBack();
                        EventBus.getDefault().post(EventBusAction.NOTIFICATIONLOCAL);
                        EventBus.getDefault().post(EventBusAction.ACCEPTREFRESE);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });

                break;
            case R.id.customTextModify:
                presenter.openModifyObjection(notificationId);
                break;
        }
    }

    @Override
    public void setNotificationId(String id) {
        this.notificationId = id;
    }

    @Override
    public void setData(ObjectionDetails data) {

        linearLayoutPendingButton.setVisibility(data.getCrType().equalsIgnoreCase(Common.Local.RECEIPT_OBJECTION) ? View.VISIBLE : View.GONE);

        orderDetails = data.getOrderDetails();
        customTextViewLocalName.setText(orderDetails.getFirstname() + " " + orderDetails.getLastname());

        Picasso.with(getActivity()).load(orderDetails.getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        customTextViewRatingValue.setText(data.getVisitorRate());


        setDataExperence(orderDetails.getExpereinceNew());

        customTextViewLocationMetting.setText(orderDetails.getMeetingAddress());

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


        customTextViewParticipants.setText(orderDetails.getParticipant().size() + " " + getActivity().getResources().getString(R.string.person));


        priceActivity = roundDouble(Double.parseDouble(orderDetails.getPrice()) * orderDetails.getParticipant().size());

        commission = Double.parseDouble(orderDetails.getCommisionValue());
        commissionPer = Double.parseDouble(orderDetails.getCommisionPerc());

        customTextViewRecTotalAct.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(roundDouble(priceActivity)));


        String discountText = "" + customTextViewLocalName.getText().toString() + " ";

        if (!data.getCrType().equalsIgnoreCase(Common.Local.RECEIPT_OBJECTION)) {
            discountText = session.getUser().getFirstname() + " " +session.getUser().getLastname()+" ";
        }


        Double tootleAmount = Double.parseDouble(orderDetails.getActivityTootal());


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

        recyclerAddParticipants.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerAddParticipants.setAdapter(new AddParticipantsWithPriceAdapter(getActivity(), addParticipantsList, roundDouble(priceActivity)));


        if (data.getObjectionType().equalsIgnoreCase("TR")) {
            discountText = discountText + getString(R.string.wants_tooal_refaund);

            refValueSet = tootleAmount;

        } else if (data.getObjectionType().equalsIgnoreCase("PR")) {
            discountText = discountText + getString(R.string.persentage_discount) + " (" + data.getObjectionPerc() + "%)";

            Double discountPer = Double.parseDouble(data.getObjectionPerc());

            refValueSet = roundDouble(tootleAmount * (discountPer / 100));

        } else if (data.getObjectionType().equalsIgnoreCase("FA")) {
            discountText = discountText + getString(R.string.flat_amount_discount);

            Double discountFlat = Double.parseDouble(data.getObjectionAmount());
            refValueSet = discountFlat;
        }


        //Discount
        customTextViewDiscountText.setText(discountText);

        customTextViewDiscountImage.setText("- " + BaseActivity.currency + " " + BaseFragment.convertFormat(refValueSet));

        refValueTootal = (tootleAmount - refValueSet);

        customTextViewRefundValue.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(refValueTootal));

        commission = refValueTootal * commissionPer / 100;

        tootalBooking = roundDouble(refValueTootal - commission);

        customTextViewRecFeesFBookingPrice.setText("- " + BaseActivity.currency + " " + BaseFragment.convertFormat(commission));

        customTextViewRecTotalBooking.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(tootalBooking));

        customTextViewReason.setText(orderDetails.getObjectionReason());

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
