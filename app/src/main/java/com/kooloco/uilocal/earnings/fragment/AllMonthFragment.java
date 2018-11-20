package com.kooloco.uilocal.earnings.fragment;
/**
 * Created by hlink44 on 9/10/17.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kooloco.R;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AllMonth;
import com.kooloco.model.MonthEarning;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.earnings.adapter.EarningAllMonthAdapter;
import com.kooloco.uilocal.earnings.presenter.AllMonthPresenter;
import com.kooloco.uilocal.earnings.view.AllMonthView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class AllMonthFragment extends BaseFragment<AllMonthPresenter, AllMonthView> implements AllMonthView {

    @BindView(R.id.recyclerMonth)
    RecyclerView recyclerMonth;

    List<MonthEarning> earningMonths;
    @BindView(R.id.customTextViewPriceTotal)
    AppCompatTextView customTextViewPriceTotal;
    private String monthEarningTotal = "";

    @Override
    protected int createLayout() {
        return R.layout.fragment_earning_all_month;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AllMonthView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        recyclerMonth.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        Observable.fromIterable(Temp.getAllMonths()).map(new Function<AllMonth, AllMonth>() {
            @Override
            public AllMonth apply(AllMonth allMonth) throws Exception {
                AllMonth allMonth1 = new AllMonth();
                allMonth1 = allMonth;
                if (earningMonths.get(allMonth.getPos()) != null) {
                    allMonth1.setPrice(earningMonths.get(allMonth.getPos()).getTotalAmount());
                } else {
                    allMonth1.setPrice("0.00");
                }
                return allMonth1;
            }
        }).toList().subscribe(allMonths -> {
            recyclerMonth.setAdapter(new EarningAllMonthAdapter(getActivity(), allMonths, new EarningAllMonthAdapter.CallBack() {
                @Override
                public void onClickItem(AllMonth allMonth, int pos) {
                    presenter.openMonthWise(allMonth);
                }
            }));

        });

        customTextViewPriceTotal.setText(BaseActivity.currency + " " + monthEarningTotal);


    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void setEarningData(List<MonthEarning> earningData) {
        this.earningMonths = earningData;
    }

    @Override
    public void setTotalAmount(String monthEarningTotal) {
        this.monthEarningTotal = monthEarningTotal;

    }
}
