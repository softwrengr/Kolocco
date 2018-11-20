package com.kooloco.uilocal.earnings.fragment;
/**
 * Created by hlink44 on 7/10/17.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AllMonth;
import com.kooloco.model.Order;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.fragment.OrderHistoryPendingFragment;
import com.kooloco.uilocal.earnings.adapter.EarningCompleteOrderAdapter;
import com.kooloco.uilocal.earnings.presenter.MonthEarningPresenter;
import com.kooloco.uilocal.earnings.view.MonthEarningView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MonthEarningFragment extends BaseFragment<MonthEarningPresenter, MonthEarningView> implements MonthEarningView {

    @BindView(R.id.recyclerMonth)
    RecyclerView recyclerMonth;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.customTextViewPriceTotal)
    AppCompatTextView customTextViewPriceTotal;
    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;
    private AllMonth allMonth;
    List<Order> orders;
    private EarningCompleteOrderAdapter completeOrderAdapter;
    int page = 1;
    LinearLayoutManager linearLayoutManager;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_earnings_month;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected MonthEarningView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        String text = getActivity().getResources().getString(R.string.total_month).replace("###", allMonth.getMonthName());
        toolbarTitle.setText(text);
        customTextViewPriceTotal.setText(BaseActivity.currency + " " + allMonth.getPrice());
        orders = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        completeOrderAdapter = new EarningCompleteOrderAdapter(getActivity(), orders, new EarningCompleteOrderAdapter.CallBack() {
            @Override
            public void onClickRow(Order order) {
                presenter.openOrderDetails(order);
            }

            @Override
            public void onClickImage(String imageUrl) {
                imageOpenZoom(imageUrl);
            }
        });

        recyclerMonth.setAdapter(completeOrderAdapter);
        recyclerMonth.setLayoutManager(linearLayoutManager);

        recyclerMonth.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    if (isLoading) {
                        page = page + 1;
                        isLoading = false;
                        presenter.getData(allMonth, page);
                    }
                }

            }
        });


        presenter.getData(allMonth, page);

    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void setAllMonth(AllMonth allMonth) {
        this.allMonth = allMonth;
    }

    @Override
    public void setData(List<Order> data, int page) {
        if (page == 1) {
            orders.clear();
        }
        orders.addAll(data);

        isLoading = !data.isEmpty();

        if (textViewNoData != null) {
            textViewNoData.setVisibility((orders.size() == 0) ? View.VISIBLE : View.GONE);
        }

        if (completeOrderAdapter != null) {
            completeOrderAdapter.notifyDataSetChanged();
        }
    }

}
