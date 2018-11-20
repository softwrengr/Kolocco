package com.kooloco.ui.notification.fragment;
/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.constant.LocalOrderAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Notification;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.notification.adapter.NotificationAdapter;
import com.kooloco.ui.notification.presenter.NotificationPresenter;
import com.kooloco.ui.notification.view.NotificationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class NotificationFragment extends BaseFragment<NotificationPresenter, NotificationView> implements NotificationView {

    @BindView(R.id.recyclerNotification)
    RecyclerView recyclerNotification;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    //Pagination

    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    boolean isLoading = false;

    int page = 1;

    List<Notification> notifications;

    NotificationAdapter notificationAdapter;

    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;

    @Override
    protected int createLayout() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected NotificationView createView() {
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
                    presenter.openReciveObjection(notification.getId());
                } else if (notification.getStatus() == 2) {
                    presenter.openOrderHistory();
                } else if (notification.getStatus() == 3) {
                    presenter.openReciptScreen(notification);
                } else if (notification.getStatus() == 5) {
                    presenter.openMapScreen(notification);
                } else if (notification.getStatus() == 6) {
                    presenter.openRating(notification.getOrderId());
                } else if (notification.getStatus() == 7) {
                    callWsBecomeLocal(status -> {
                        if (status) {
                            presenter.openBecomeLocal();
                        }
                    });
                }

            }

            @Override
            public void onClickChat(Notification notification) {

                presenter.openChat(notification);
            }

            @Override
            public void onClickAcceptObjection(Notification notification) {
                visitorSendObjectionAction(notification.getId(), new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        page = 1;
                        presenter.getData(page, true);
                        deleteRecentChat(notification.getOrderId());
                        presenter.openRating(notification.getOrderId());
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }

            @Override
            public void onClickRequestAdmin(Notification notification) {
                visitorSendObjectionActionAdmin(notification.getId(), new CallBackOrderAction() {
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

            @Override
            public void onClickNotify(Notification notification) {
                visitorModifyAction(notification.getId(), LocalOrderAction.NOTIFY, new CallBackOrderAction() {
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

            @Override
            public void onClickAccept(Notification notification) {
                visitorModifyAction(notification.getId(), LocalOrderAction.ACCEPT, new CallBackOrderAction() {
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

            @Override
            public void onClickReject(Notification notification) {
                visitorModifyAction(notification.getId(), LocalOrderAction.REJECT, new CallBackOrderAction() {
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

    @Override
    public void setData(List<Notification> notifications, int page) {
        if (page == 1) {
            this.notifications.clear();

            EventBus.getDefault().post(EventBusAction.NOTIFICATIONCOUNTDISPLAYVISITOR);
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

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        presenter.onBack();
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
        if (action == EventBusAction.NOTIFICATIONVISITOR) {
            page = 1;
            presenter.getData(page, false);
        }
    }

    @Override
    public void onShow() {
        super.onShow();
        page = 1;
        presenter.getData(page, false);
    }
}
