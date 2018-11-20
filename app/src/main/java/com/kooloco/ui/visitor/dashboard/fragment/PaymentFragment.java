package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 22/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.BookingData;
import com.kooloco.model.Card;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.PaymentAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.PaymentPresenter;
import com.kooloco.ui.visitor.dashboard.view.PaymentView;
import com.kooloco.util.TimeConvertUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentFragment extends BaseFragment<PaymentPresenter, PaymentView> implements PaymentView {

    @BindView(R.id.recyclerCard)
    RecyclerView recyclerCard;
    @BindView(R.id.customTextViewAddCard)
    AppCompatTextView customTextViewAddCard;
    @BindView(R.id.customTextViewNote)
    AppCompatTextView customTextViewNote;
    @BindView(R.id.buttonPay)
    AppCompatButton buttonPay;
    private ExperienceBookingFlow visitorBooking;
    @Inject
    Session session;

    Card selectCard;

    @Override
    protected int createLayout() {
        return R.layout.fragment_payment;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected PaymentView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        String text = "" + getActivity().getResources().getString(R.string.appointment_summery_note).replace("###", "<font color='" + getActivity().getResources().getColor(R.color.red) + "'>* </font>");
        customTextViewNote.setText(Html.fromHtml(text));

        String textn = "" + getActivity().getResources().getString(R.string.button_pay).replace("###", BaseActivity.currency + " " + (visitorBooking.getIsRefferal().equalsIgnoreCase("1") ? visitorBooking.getSetRefferalTotal() : visitorBooking.getTootalPrice()) + "");

        buttonPay.setText(Html.fromHtml(textn));

        presenter.getData();

    }


    @OnClick(R.id.buttonPay)
    public void onViewClicked() {

        if (selectCard == null) {
            showMessage(getString(R.string.val_select_card));
            return;
        }
        presenter.callWs(visitorBooking, selectCard.getCardId());
        //presenter.openAppointmentBooked();
    }

    @Override
    public void setData(List<Card> data) {
        if (data.size() != 0) {
            selectCard = data.get(0);
        }

        recyclerCard.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerCard.setAdapter(new PaymentAdapter(getActivity(), data, card -> selectCard = card));
    }

    @Override
    public void setBookingData(ExperienceBookingFlow visitorBooking) {
        this.visitorBooking = visitorBooking;
    }

    @Override
    public void setRecentChat(BookingData data) {
        String uniqId = "";

        uniqId = getDocumentIdForDatabse(session.getUser().getId(), visitorBooking.getLocalId());

        uniqId = uniqId + "#" + data.getId();

        Map<String, Object> chat = new HashMap<>();

        chat.put(Common.FireStore.FIELD_SENDER_ID, "");
        chat.put(Common.FireStore.FIELD_SENDER_NAME, "");
        chat.put(Common.FireStore.FIELD_SENDER_IMAGE_URL, "");
        chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TYPE, "");
        chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TOKEN, "");

        chat.put(Common.FireStore.FIELD_RECEIVER_ID, "");
        chat.put(Common.FireStore.FIELD_RECEIVER_NAME, "");
        chat.put(Common.FireStore.FIELD_RECEIVER_IMAGE_URL, "");
        chat.put(Common.FireStore.FIELD_RECEIVER_DEVICE_TYPE, "");
        chat.put(Common.FireStore.FIELD_RECEIVER_DEVICE_TOKEN, "");

        chat.put(Common.FireStore.FIELD_MESSAGE, "");
        chat.put(Common.FireStore.FIELD_MESSAGE_TYPE, "M");

        Date time = Calendar.getInstance().getTime();

        String convertDate = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a", Locale.US).format(time.getTime());

        chat.put(Common.FireStore.FIELD_CHAT_TIME, time);
        chat.put(Common.FireStore.FIELD_CHAT_TIME_UTC, TimeConvertUtils.datTimeConvertLocalToServerMain(convertDate, "MMM d, yyyy hh:mm:ss a", "MM d, yyyy hh:mm:ss a"));

        chat.put(Common.FireStore.FIELD_CHAT_READ, false);

        chat.put(Common.FireStore.FIELD_CHAT_COUNT, "0");

        //    chat.put(Common.FireStore.FIELD_UNIQ_ID, uniqId);

        chat.put(Common.FireStore.FIELD_CHAT_LOCAL_ID, visitorBooking.getLocalId());
        chat.put(Common.FireStore.FIELD_CHAT_VISITOR_ID, session.getUser().getId());
        chat.put(Common.FireStore.FIELD_CHAT_ORDER_ID, data.getId());

        getDatabase().collection(Common.FireStore.TAB_NAME_RECENT_CHAT).document(uniqId).set(chat).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                hideLoader();
                presenter.openAppointmentBooked(visitorBooking);
            } else {
                hideLoader();
                presenter.openAppointmentBooked(visitorBooking);
            }
        });

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

    @OnClick(R.id.customTextViewAddCard)
    public void onClick() {
        presenter.openAddCard();
    }
}
