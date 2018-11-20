package com.kooloco.uilocal.home.fragment;
/**
 * Created by hlink44 on 5/10/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.constant.LocalOrderAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Order;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.home.adapter.HomeAdapter;
import com.kooloco.uilocal.home.presenter.HomePendingPresenter;
import com.kooloco.uilocal.home.view.HomePendingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class HomePendingFragment extends BaseFragment<HomePendingPresenter, HomePendingView> implements HomePendingView {
    @BindView(R.id.recyclerOrder)
    RecyclerView recyclerOrder;
    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;

    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    boolean isLoading = false;

    int page = 1;

    List<Order> data;

    HomeAdapter homeAdapter;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    private SearchView searchView;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_home_pending;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected HomePendingView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerOrder.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), data, new HomeAdapter.CallBack() {
            @Override
            public void onClickAccept(Order order) {
                localOrderAction(order, LocalOrderAction.ACCEPT, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        data.remove(order);
                        homeAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post(EventBusAction.ACCEPTREFRESE);
                        presenter.getData(page, false);
                        presenter.openAcceptOrderDetails(order);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }

            @Override
            public void onClickDecline(Order order) {
                localOrderAction(order, LocalOrderAction.REJECT, new CallBackOrderAction() {
                    @Override
                    public void onSuccess(Response response) {
                        deleteRecentChat(order.getId());
                        data.remove(order);
                        presenter.getData(page, false);
                        homeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }

            @Override
            public void onClickModify(Order order) {
                showModifyDialog(order);
            }

            @Override
            public void onClickChat(Order order) {

            }

            @Override
            public void onClickComplate(Order order) {

            }

            @Override
            public void onClickCancel(Order order) {

            }

            @Override
            public void onClickSendRequest(Order order) {

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


        recyclerOrder.setAdapter(homeAdapter);


        recyclerOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (searchView != null) {
                    if (!searchView.getQuery().toString().isEmpty()) {
                        return;
                    }
                }
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
    public void setData(List<Order> data, int page) {
        if (page == 1) {
            this.data.clear();
        }

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        isLoading = !data.isEmpty();

        this.data.addAll(data);

        homeAdapter.notifyDataSetChanged();

        if (linearLayoutNoData != null) {
            linearLayoutNoData.setVisibility((this.data.size() == 0) ? View.VISIBLE : View.INVISIBLE);
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
        if (action == EventBusAction.PENDINGREFRESE) {
            page = 1;
            presenter.getData(page, false);
        }
    }

    @OnClick(R.id.buttonOkDone)
    public void onClickDone() {
        presenter.openViewProfile();
    }


    public void setSearchText(String searchText) {
        Log.e("KPPending", "---------------" + searchText);
        if (homeAdapter != null) {
            homeAdapter.getFilter().filter(searchText, new Filter.FilterListener() {
                @Override
                public void onFilterComplete(int count) {
                    if (homeAdapter.countItems() == 0) {
                        if (!searchText.isEmpty()) {
                            setVisibility(true);
                        }
                    } else {
                        setVisibility(false);
                    }
                }

            });
        }

    }

    private void setVisibility(boolean b) {
        if (linearLayoutNoData != null) {
            linearLayoutNoData.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setSearchView(SearchView searchView) {
        this.searchView=searchView;
    }
}
