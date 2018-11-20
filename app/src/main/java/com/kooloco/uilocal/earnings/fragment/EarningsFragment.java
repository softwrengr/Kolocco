package com.kooloco.uilocal.earnings.fragment;
/**
 * Created by hlink44 on 7/10/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.EarningData;
import com.kooloco.model.Month;
import com.kooloco.model.MonthEarning;
import com.kooloco.model.Review;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.earnings.adapter.EarningReviewAdapter;
import com.kooloco.uilocal.earnings.adapter.MonthEarningAdapter;
import com.kooloco.uilocal.earnings.adapter.RateEarningAdapter;
import com.kooloco.uilocal.earnings.presenter.EarningsPresenter;
import com.kooloco.uilocal.earnings.view.EarningsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class EarningsFragment extends BaseFragment<EarningsPresenter, EarningsView> implements EarningsView {

    @BindView(R.id.customTextViewSelectMonth)
    AppCompatTextView customTextViewSelectMonth;
    @BindView(R.id.recyclerMonth)
    RecyclerView recyclerMonth;
    @BindView(R.id.customTextViewPriceMonth)
    AppCompatTextView customTextViewPriceMonth;
    @BindView(R.id.customTextViewMonthName)
    AppCompatTextView customTextViewMonthName;
    @BindView(R.id.customTextViewPriceTotal)
    AppCompatTextView customTextViewPriceTotal;
    @BindView(R.id.customTextViewRatingCount)
    AppCompatTextView customTextViewRatingCount;
    @BindView(R.id.recyclerRating)
    RecyclerView recyclerRating;

    List<Review> reviewsData;
    @BindView(R.id.recyclerReviews)
    RecyclerView recyclerReviews;
    @BindView(R.id.textViewReadAll)
    AppCompatTextView textViewReadAll;

    String monthName = "";

    int pageRate = 1;
    int tootalCount = 0;
    boolean isCallApi = true;

    EarningReviewAdapter rateReviewAdapter;

    List<Review> reviews;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    @BindView(R.id.ratingBarSportSkills)
    AppCompatRatingBar ratingBarSportSkills;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;

    List<MonthEarning> monthEarnings;

    String tootalAmount = "0.00";

    Month monthSelect;

    int monthSelectPos = 0;


    @Override
    protected int createLayout() {
        return R.layout.fragment_local_earnings;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected EarningsView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        setDot(imageViewCount);
        //It is used to set review data
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        rateReviewAdapter = new EarningReviewAdapter(getActivity(), reviews);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerReviews.setAdapter(rateReviewAdapter);


        swipeRefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(() -> {
            if (presenter != null) {
                presenter.getData();
            }
        });


        Month month = Temp.getMonths().get(0);
        customTextViewSelectMonth.setText(month.getFullName() + ", " + month.getYear());
        String text = getActivity().getResources().getString(R.string.total_month).replace("###", month.getName());
        customTextViewMonthName.setText(text);
        monthName = month.getName();


        recyclerMonth.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerMonth.setAdapter(new MonthEarningAdapter(getActivity(), Temp.getMonths(), new MonthEarningAdapter.CallBack() {
            @Override
            public void onSelect(Month month, int position) {
                customTextViewSelectMonth.setText(month.getFullName() + ", " + month.getYear());
                String text = getActivity().getResources().getString(R.string.total_month).replace("###", month.getName());
                customTextViewMonthName.setText(text);
                monthName = month.getName();
                monthSelect = month;
                monthSelectPos=position;
                setPrice(position);

            }
        }));

        monthSelect = Temp.getMonths().get(0);
        monthSelectPos=0;

        monthEarnings = new ArrayList<>();

        presenter.getData();
    }

    @OnClick({R.id.imageViewNext, R.id.customTextViewMonthWise, R.id.imageViewNotification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewNext:
                presenter.openMonthData(monthSelect,monthSelectPos,monthEarnings.get(monthSelectPos));
//                presenter.openMonthEarningView();
                break;
            case R.id.customTextViewMonthWise:
                presenter.openMonthEarningView(monthEarnings, tootalAmount);
                break;
            case R.id.imageViewNotification:
                presenter.openNotification();
                break;
        }
    }

    @OnClick({R.id.textViewReadAll})
    public void onViewClickedReview(View view) {
        switch (view.getId()) {
            case R.id.textViewReadAll:
                callApi(false);
                break;
        }
    }

    private void callApi(boolean isFirst) {
        if (isCallApi) {
            if (isFirst) {
                pageRate = 1;
                reviews.clear();
            } else {
                pageRate = pageRate + 1;
            }
            presenter.getRating(pageRate);
            isCallApi = false;
        }
    }

    @Override
    public void setDataRating(List<Review> data, int pg, int tootalCount) {
        if (pg == 1) {
            this.tootalCount = tootalCount;
        }

        if (data.size() != 0) {
            isCallApi = true;
        }

        reviews.addAll(data);

        if (linearLayoutNoData != null)
            linearLayoutNoData.setVisibility((reviews.size() == 0) ? View.VISIBLE : View.GONE);

        if (rateReviewAdapter != null) {
            rateReviewAdapter.notifyDataSetChanged();
        }

        setRatingData();
    }

    @Override
    public void setData(EarningData earningRates) {

        if (monthEarnings == null) {
            monthEarnings = new ArrayList<>();
        }

        monthEarnings.addAll(earningRates.getMonthEarnings());

        if (customTextViewPriceTotal != null) {
            tootalAmount = earningRates.getTotalAmount();
            customTextViewPriceTotal.setText(BaseActivity.currency + " " + earningRates.getTotalAmount());
        }

        setPrice(0);

        if (earningRates.getEarningRates() != null) {
            recyclerRating.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerRating.setAdapter(new RateEarningAdapter(getActivity(), earningRates.getEarningRates()));
        }

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        if (earningRates.getTootalReview() != null) {
            float rate = 0.0f;

            try {
                rate = Float.parseFloat(earningRates.getTootalReview());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                rate = 0.0f;
            }

            ratingBarSportSkills.setRating(rate);
        }
        isCallApi = true;
        callApi(true);
    }


    private void setRatingData() {

        customTextViewRatingCount.setText("(" + tootalCount + ")");

        if (textViewReadAll != null) {
            textViewReadAll.setVisibility(((tootalCount - reviews.size()) <= 0) ? View.GONE : View.VISIBLE);

            textViewReadAll.setText(getString(R.string.read_all)+" " + (tootalCount - reviews.size()) +" "+ getString(R.string.reviews));

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

    private void setPrice(int pos) {

        if (customTextViewPriceMonth != null) {
            if (monthEarnings.get(pos) != null) {
                customTextViewPriceMonth.setText(BaseActivity.currency + " " + monthEarnings.get(pos).getTotalAmount());
            }
        }

    }

}
