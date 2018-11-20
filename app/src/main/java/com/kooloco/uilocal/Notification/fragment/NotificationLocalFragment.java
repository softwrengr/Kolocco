package com.kooloco.uilocal.Notification.fragment;
/**
 * Created by hlink44 on 9/10/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Notification;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.MainLocalActivity;
import com.kooloco.uilocal.Notification.adapter.NotificationAdapter;
import com.kooloco.uilocal.Notification.presenter.NotificationLocalPresenter;
import com.kooloco.uilocal.Notification.view.NotificationLocalView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class NotificationLocalFragment extends BaseFragment<NotificationLocalPresenter, NotificationLocalView> implements NotificationLocalView {
    @BindView(R.id.recyclerNotification)
    RecyclerView recyclerNotification;

    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    boolean isLoading = false;

    int page = 1;

    List<Notification> notifications;

    NotificationAdapter notificationAdapter;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_notification;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected NotificationLocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        notifications = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerNotification.setLayoutManager(linearLayoutManager);
        notificationAdapter = new NotificationAdapter(getActivity(), notifications, new NotificationAdapter.CallBack() {
            @Override
            public void onClickRow(Notification notification) {
                if (notification.getStatus() == 1) {
                    presenter.openReceivedObjection(notification);
                } else if (notification.getStatus() == 2) {
                    Intent authanticationIntent;
                    authanticationIntent = new Intent(getActivity(), MainLocalActivity.class);
                    getActivity().finishAffinity();
                    startActivity(authanticationIntent);
                }

            }

            @Override
            public void onClickModify(Notification notification) {
                presenter.openModifyObjection(notification);
            }

            @Override
            public void onClickAccept(Notification notification) {
                localSendObjectionAction(notification.getId(), new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        page = 1;
                        deleteRecentChat(notification.getOrderId());
                        presenter.getData(page, true);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });

            }

            @Override
            public void onClickOrgAccept(Notification notification) {
                presenter.callOrgAccept(notification.getId(), new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        page = 1;
                        presenter.getData(page, true);

                    }

                    @Override
                    public void onError(String message) {
                        presenter.getData(page, false);
                    }
                });
            }

            @Override
            public void onClickOrgDecline(Notification notification) {
                presenter.callOrgDecline(notification.getId(), new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        page = 1;
                        presenter.getData(page, true);

                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });

        recyclerNotification.setAdapter(notificationAdapter);

        recyclerNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + 3 + pastVisiblesItems) >= totalItemCount) {
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

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void setData(List<Notification> notifications, int page) {
        if (page == 1) {
            this.notifications.clear();

            EventBus.getDefault().post(EventBusAction.NOTIFICATIONCOUNTDISPLAYLOCAL);
        }

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        isLoading = !notifications.isEmpty();

        this.notifications.addAll(notifications);

        notificationAdapter.notifyDataSetChanged();

        if (textViewNoData != null) {
            textViewNoData.setVisibility((this.notifications.size() == 0) ? View.VISIBLE : View.GONE);
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
    }

    public void onEvent(EventBusAction action) {
        if (action == EventBusAction.NOTIFICATIONLOCAL) {
            page = 1;
            presenter.getData(page, false);
        }
    }
}
