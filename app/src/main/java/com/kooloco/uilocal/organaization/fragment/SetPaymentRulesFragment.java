package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink on 1/2/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.PaymentRuleList;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.organaization.adapter.OrgSetPaymentRulesListAdapter;
import com.kooloco.uilocal.organaization.presenter.SetPaymentRulesPresenter;
import com.kooloco.uilocal.organaization.view.SetPaymentRulesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink on 8/1/18.
 */

public class SetPaymentRulesFragment extends BaseFragment<SetPaymentRulesPresenter, SetPaymentRulesView> implements SetPaymentRulesView {
    @BindView(R.id.recyclerSetPaymentRules)
    RecyclerView recyclerSetPaymentRules;
    @BindView(R.id.linearLayoutAddButton)
    LinearLayout linearLayoutAddButton;
    @BindView(R.id.buttonDone)
    AppCompatButton buttonDone;
    private OrganizationDetails organizationDetails;
    List<PaymentRuleList> paymentRuleLists;
    OrgSetPaymentRulesListAdapter orgSetPaymentRulesListAdapter;
    private boolean setIsCreatOrganization = false;
    private boolean bank;

    @Override
    protected int createLayout() {
        return R.layout.fragment_org_set_payment_rules;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SetPaymentRulesView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        paymentRuleLists = new ArrayList<>();

        presenter.getBankIsAdd(organizationDetails);

        //presenter.getPaymentRules(organizationDetails);

        orgSetPaymentRulesListAdapter = new OrgSetPaymentRulesListAdapter(getActivity(), paymentRuleLists, new OrgSetPaymentRulesListAdapter.CallBack() {
            @Override
            public void onClickEdit(PaymentRuleList paymentRuleList) {
                presenter.openEditPaymentRules(organizationDetails, paymentRuleList, bank);
            }

            @Override
            public void onClickDelete(PaymentRuleList paymentRuleList) {
                presenter.callWsDelete(paymentRuleList, organizationDetails);
            }
        });
        recyclerSetPaymentRules.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerSetPaymentRules.setAdapter(orgSetPaymentRulesListAdapter);
    }

    @OnClick({R.id.imageViewBack, R.id.imageAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageAdd:
                presenter.openAddPaymentRules(organizationDetails, bank);
                break;
        }
    }

    @Override
    public void setOrderDetails(OrganizationDetails organizationDetails) {
        this.organizationDetails = organizationDetails;
    }

    @Override
    public void setData(List<PaymentRuleList> data) {
        paymentRuleLists.clear();
        paymentRuleLists.addAll(data);

        if (orgSetPaymentRulesListAdapter != null) {
            orgSetPaymentRulesListAdapter.notifyDataSetChanged();
        }

        if (linearLayoutAddButton != null) {
            linearLayoutAddButton.setVisibility((paymentRuleLists.size() == 0) ? View.VISIBLE : View.GONE);
        }

        if (buttonDone != null) {
            buttonDone.setVisibility((paymentRuleLists.size() == 0) ? View.GONE : View.VISIBLE);
        }

    }

    @Override
    public void setIsCreatOrganization(boolean isOrganization) {
        this.setIsCreatOrganization = isOrganization;
    }

    @Override
    public void setIsBank(boolean bank) {
        this.bank = bank;
    }

    @OnClick(R.id.buttonDone)
    public void onClick() {
        if (setIsCreatOrganization) {
            presenter.openPreiviewDetails();
        } else {
            goBack();
        }
    }

    @OnClick(R.id.buttonAdd)
    public void onClickAdd() {
        presenter.openAddPaymentRules(organizationDetails, bank);
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
        if (action == EventBusAction.SETADDPAYMENTRULES) {
            presenter.getPaymentRules(organizationDetails);
        }
    }

}
