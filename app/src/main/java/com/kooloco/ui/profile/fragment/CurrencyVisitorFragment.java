package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink on 29/6/18.
 */

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Currency;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.adapter.CurrencySelectAdapter;
import com.kooloco.ui.profile.presenter.CurrencyVisitorPresenter;
import com.kooloco.ui.profile.view.CurrencyVisitorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class CurrencyVisitorFragment extends BaseFragment<CurrencyVisitorPresenter, CurrencyVisitorView> implements CurrencyVisitorView {

    @BindView(R.id.recyclerCurrency)
    RecyclerView recyclerCurrency;
    @BindView(R.id.textViewSelectCurrency)
    AppCompatEditText textViewSelectCurrency;
    private boolean isVisitor;
    List<Currency> currency;
    private CurrencySelectAdapter currencyAdapter;
    Currency selectCurrency;

    @Inject
    Session session;

    @Override
    protected int createLayout() {
        return R.layout.currency_visitor;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected CurrencyVisitorView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (currency == null) {
            currency = new ArrayList<>();
            presenter.getData(isVisitor);
        }


        textViewSelectCurrency.setText(isVisitor ? session.getUser().getvCurrency() : session.getUser().getlCurrency());


        recyclerCurrency.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        currencyAdapter = new CurrencySelectAdapter(getActivity(), currency, true, new CurrencySelectAdapter.CallBack() {
            @Override
            public void onClickItem(Currency currency) {
                selectCurrency = currency;
                textViewSelectCurrency.setText(currency.getCurrency());
            }

        });
        recyclerCurrency.setAdapter(currencyAdapter);
    }

    @OnClick({R.id.imageViewBack, R.id.textViewSelectCurrency, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewSelectCurrency:
                break;
            case R.id.buttonNext:
                if (selectCurrency == null) {
                    goBack();
                } else {
                    presenter.callWs(selectCurrency, isVisitor);
                }
                break;
        }
    }

    @Override
    public void setIsVisitor(boolean isVisitor) {
        this.isVisitor = isVisitor;
    }

    @Override
    public void setData(List<Currency> data) {
        currency.addAll(data);
        if (currencyAdapter != null) {
            currencyAdapter.notifyDataSetChanged();
        }
    }
}
