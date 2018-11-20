package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 25/9/17.
 */

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Order;
import com.kooloco.model.ReceiverData;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.adapter.OrderHistoryAdapter;
import com.kooloco.ui.profile.presenter.OrderHistoryPendingPresenter;
import com.kooloco.ui.profile.view.OrderHistoryPendingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderHistoryPendingFragment extends BaseFragment<OrderHistoryPendingPresenter, OrderHistoryPendingView> implements OrderHistoryPendingView {

    @BindView(R.id.recyclerOrder)
    RecyclerView recyclerOrder;

    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;

    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    boolean isLoading = false;

    int page = 1;


    List<Order> data;

    OrderHistoryAdapter orderHistoryAdapter;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;

    @Override
    protected int createLayout() {
        return R.layout.fragment_order_history_pending;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OrderHistoryPendingView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerOrder.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();

        orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), data, new OrderHistoryAdapter.CallBack() {
            @Override
            public void onClickChat(Order order) {

                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(order.getLocalId());
                receiverData.setName(order.getFirstname() + " " + order.getLastname());
                receiverData.setImageUrl(order.getProfileImage());
                receiverData.setDeviceType("A");
                receiverData.setDeviceToken("123");
                receiverData.setOrderId(order.getId());

                presenter.opnChat(receiverData);
            }

            @Override
            public void onClickCall(Order order) {
                //  openCallIntent("+91 8980255935");
            }

            @Override
            public void onClickRow(Order order) {
                presenter.openOrderDetails(order);
            }

            @Override
            public void onClickImage(String imageUrl) {
                imageOpenZoom(imageUrl);
            }
        });

        recyclerOrder.setAdapter(orderHistoryAdapter);


        recyclerOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = OrderHistoryPendingFragment.this.linearLayoutManager.getChildCount();
                totalItemCount = OrderHistoryPendingFragment.this.linearLayoutManager.getItemCount();
                pastVisiblesItems = OrderHistoryPendingFragment.this.linearLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    if (isLoading) {
                        page = page + 1;
                        isLoading = false;
                        presenter.getData(page, false);
                    }
                }

            }
        });

        page = 1;
        presenter.getData(1, true);

        swipeRefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(() -> {
            if (presenter != null) {
                page = 1;
                presenter.getData(page, false);
            }
        });

    }

    @Override
    public void setData(List<Order> data, int page) {
        if (page == 1) {
            this.data.clear();
        }

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }


        this.data.addAll(data);

        isLoading = !data.isEmpty();

        orderHistoryAdapter.notifyDataSetChanged();

        if (linearLayoutNoData != null) {
            linearLayoutNoData.setVisibility((this.data.size() == 0) ? View.VISIBLE : View.INVISIBLE);
        }

    }

    @OnClick(R.id.buttonOkDone)
    public void onClickDone() {
        Intent authanticationIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(authanticationIntent);
        getActivity().finishAffinity();
    }
}
