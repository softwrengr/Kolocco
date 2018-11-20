package com.kooloco.uilocal.complateorder.fragment;
/**
 * Created by hlink44 on 9/10/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Order;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.MainLocalActivity;
import com.kooloco.uilocal.complateorder.adapter.CompleteOrderAdapter;
import com.kooloco.uilocal.complateorder.presenter.CompleteOrderPresenter;
import com.kooloco.uilocal.complateorder.view.CompleteOrderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class CompleteOrderFragment extends BaseFragment<CompleteOrderPresenter, CompleteOrderView> implements CompleteOrderView {

    @BindView(R.id.customTextViewOrderSize)
    AppCompatTextView customTextViewOrderSize;
    @BindView(R.id.recyclerCompleteOrder)
    RecyclerView recyclerCompleteOrder;

    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    boolean isLoading = false;

    int page = 1;


    List<Order> data;

    CompleteOrderAdapter completeOrderAdapter;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;
    @BindView(R.id.imageViewSearch)
    ImageView imageViewSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    SearchView searchView;


    @Override
    protected int createLayout() {
        return R.layout.fragment_local_complate_order;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected CompleteOrderView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        imageViewSearch.setVisibility(View.GONE);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setDot(imageViewCount);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerCompleteOrder.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();

        completeOrderAdapter = new CompleteOrderAdapter(getActivity(), data, new CompleteOrderAdapter.CallBack() {
            @Override
            public void onClickRow(Order order) {
                presenter.openOrderDetails(order);
            }

            @Override
            public void onClickImage(String imageUrl) {
                imageOpenZoom(imageUrl);
            }
        });

        recyclerCompleteOrder.setAdapter(completeOrderAdapter);


        recyclerCompleteOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void setData(List<Order> data, int page, String orderCount) {

        if (this.page == 1) {
            this.data.clear();
        }

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        isLoading = !data.isEmpty();

        this.data.addAll(data);

        completeOrderAdapter.notifyDataSetChanged();

        if (this.page == 1) {
            if (customTextViewOrderSize != null) {
                customTextViewOrderSize.setText(getActivity().getResources().getString(R.string.complete_orders) + " (" + orderCount + ")");
            }
        }

        if (linearLayoutNoData != null) {
            linearLayoutNoData.setVisibility((this.data.size() == 0) ? View.VISIBLE : View.INVISIBLE);
        }

    }


    @OnClick({R.id.imageViewNotification, R.id.imageViewSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewNotification:
                presenter.openNotification();
                break;
            case R.id.imageViewSearch:
                break;
        }
    }

    @OnClick(R.id.buttonOkDone)
    public void onClick() {
        if (getActivity() instanceof MainLocalActivity) {
            ((MainLocalActivity) getActivity()).selectHomeTab();
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

        if (action == EventBusAction.NOTIFICATIONCOUNTUILOCAL) {
            setDot(imageViewCount);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        super.onCreateOptionsMenu(menu, inflater);
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                completeOrderAdapter.getFilter().filter(newText, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {

                        recyclerCompleteOrder.getRecycledViewPool().clear();

                        if (completeOrderAdapter.countItems() == 0) {
                            if (!newText.isEmpty()) {
                                setVisibility(true);

                            }
                        } else {

                            setVisibility(false);
                        }
                    }
                });
                return true;
            }
        });
    }

    private void setVisibility(boolean isDisp) {
        if (linearLayoutNoData != null) {
            linearLayoutNoData.setVisibility((isDisp) ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
