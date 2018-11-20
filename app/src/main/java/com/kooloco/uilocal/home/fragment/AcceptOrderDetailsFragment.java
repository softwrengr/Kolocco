package com.kooloco.uilocal.home.fragment;
/**
 * Created by hlink44 on 5/10/17.
 */


import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsWithPriceAdapter;
import com.kooloco.uilocal.home.presenter.AcceptOrderDetailsPresenter;
import com.kooloco.uilocal.home.view.AcceptOrderDetailsView;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AcceptOrderDetailsFragment extends BaseFragment<AcceptOrderDetailsPresenter, AcceptOrderDetailsView> implements AcceptOrderDetailsView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
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
    @BindView(R.id.buttonOk)
    AppCompatButton buttonOk;
    private Order order;

    double pricePartActivity;
    double priceActivity;
    int feesForBooking = 0;

    double tootalBooking = 0.0;

    double commission = 0;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_accept_order_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AcceptOrderDetailsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (order != null) {
            presenter.getData(order.getId());
        }
    }

    @OnClick(R.id.buttonOk)
    public void onViewClicked() {

        getActivity().finish();

    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void setData(OrderDetails orderDetails) {

        customTextViewLocalName.setText(orderDetails.getFirstname() + " " + orderDetails.getLastname());

        Picasso.with(getActivity()).load(orderDetails.getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        customTextViewRatingValue.setText(orderDetails.getVisitorRate());


        priceActivity = roundDouble(Double.parseDouble(orderDetails.getPrice()) * orderDetails.getParticipant().size());

        commission = Double.parseDouble(orderDetails.getCommisionValue());

        customTextViewRecTotalAct.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(priceActivity));

        tootalBooking = roundDouble(priceActivity - commission);

        customTextViewRecFeesFBookingPrice.setText("- " + BaseActivity.currency + " " + BaseFragment.convertFormat(commission));

        customTextViewRecTotalBooking.setText(BaseActivity.currency + " " + BaseFragment.convertFormat(tootalBooking));
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
    }
}
