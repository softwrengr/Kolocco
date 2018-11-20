package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink on 1/2/18.
 */

import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.View;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.PaymentRuleList;
import com.kooloco.model.PaymentRuleLocal;
import com.kooloco.model.PaymentRulesOption;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.organaization.adapter.OrgAddNewPaymentRulesLocalListAdapter;
import com.kooloco.uilocal.organaization.adapter.OrgAddPaymentRulesListAdapter;
import com.kooloco.uilocal.organaization.presenter.AddNewPaymentRulesPresenter;
import com.kooloco.uilocal.organaization.view.AddNewPaymentRulesView;
import com.kooloco.util.InputFilterMinMaxDouble;
import com.kooloco.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by hlink on 8/1/18.
 */

public class AddNewPaymentRulesFragment extends BaseFragment<AddNewPaymentRulesPresenter, AddNewPaymentRulesView> implements AddNewPaymentRulesView {

    @BindView(R.id.editTextRuleName)
    AppCompatEditText editTextRuleName;
    @BindView(R.id.recyclerViewLocal)
    RecyclerView recyclerViewLocal;
    @BindView(R.id.recyclerViewPriceRules)
    RecyclerView recyclerViewPriceRules;
    List<PaymentRulesOption> paymentRulesOptions;
    List<OrgLocal> orgLocals;
    List<OrgLocal> orgLocalsSelect;

    OrgAddPaymentRulesListAdapter orgAddPaymentRulesListAdapter;
    OrgAddNewPaymentRulesLocalListAdapter orgAddNewPaymentRulesLocalListAdapter;
    private OrganizationDetails organizationDetails;

    @Inject
    Validator validator;
    private PaymentRuleList paymentRuleList;
    private boolean isEdit;

    int selectIndex = 0;
    String selectValue = "";

    String paymentRuleId = "";

    boolean isSetBank = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_org_add_new_payment_rules;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AddNewPaymentRulesView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (orgLocals == null) {
            orgLocals = new ArrayList<>();
            orgLocalsSelect = new ArrayList<>();
        }

        if (isEdit) {
            orgAddNewPaymentRulesLocalListAdapter = new OrgAddNewPaymentRulesLocalListAdapter(getActivity(), orgLocalsSelect, paymentRuleList.getAssignedLocal());
        } else {
            orgAddNewPaymentRulesLocalListAdapter = new OrgAddNewPaymentRulesLocalListAdapter(getActivity(), orgLocalsSelect, new ArrayList<>());
        }


        recyclerViewLocal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewLocal.setAdapter(orgAddNewPaymentRulesLocalListAdapter);

        if (paymentRulesOptions == null) {
            paymentRulesOptions = new ArrayList<>();
            presenter.getData(organizationDetails, paymentRuleId);
        }


        orgAddPaymentRulesListAdapter = new OrgAddPaymentRulesListAdapter(getActivity(), paymentRulesOptions, new OrgAddPaymentRulesListAdapter.CallBack() {
            @Override
            public void showKey() {
                new Handler().postDelayed(() -> showKeyBoard(), 200);
            }

            @Override
            public void hideKey() {
                hideKeyBoard();
            }
        });
        recyclerViewPriceRules.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        orgAddPaymentRulesListAdapter.setSelectIndex(selectIndex);
        orgAddPaymentRulesListAdapter.setSetValue(selectValue);
        recyclerViewPriceRules.setAdapter(orgAddPaymentRulesListAdapter);

        if (isEdit) {
            if (paymentRuleList != null) {
                editTextRuleName.setText(paymentRuleList.getTitle());
            }
        }

    }

    @OnClick({R.id.imageViewBack, R.id.buttonDone, R.id.linearLayoutAssign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonDone:
                try {
                    validator.submit(editTextRuleName).checkEmpty().errorMessage(R.string.val_rul_name).check();
                    if (isCheckSelectLocal()) {
                        showMessage(getString(R.string.val_select_at_least_one_local));
                        return;
                    }
                    if (orgAddPaymentRulesListAdapter.getSelectData().getRuleType().equalsIgnoreCase("split")) {
                        if (orgAddPaymentRulesListAdapter.getSetValue().isEmpty()) {
                            showMessage(getString(R.string.val_per_local));
                            return;
                        }
                    }

                    boolean isCheckBank;

                    if (orgAddPaymentRulesListAdapter.getSelectData().getRuleType().equalsIgnoreCase("to_local")) {
                        isCheckBank = true;
                    } else {
                        isCheckBank = isSetBank;
                    }

                    if (isEdit) {
                        presenter.callEditPaymentRules(paymentRuleList.getId(), editTextRuleName.getText().toString(), organizationDetails, orgLocals, orgAddPaymentRulesListAdapter.getSelectData(), orgAddPaymentRulesListAdapter.getSetValue(), isCheckBank);
                    } else {
                        presenter.callAddPaymentRules(editTextRuleName.getText().toString(), organizationDetails, orgLocals, orgAddPaymentRulesListAdapter.getSelectData(), orgAddPaymentRulesListAdapter.getSetValue(), isCheckBank);
                    }

                } catch (ApplicationException e) {
                    showMessage(e.getMessage());
                }
                break;
            case R.id.linearLayoutAssign:
                selectIndex = orgAddPaymentRulesListAdapter.getSelectPosition();
                selectValue = orgAddPaymentRulesListAdapter.getSetValue();

                presenter.openLocalAssign(organizationDetails, orgLocals, new CallBack() {
                    @Override
                    public void onData(List<OrgLocal> orgLocalsData) {
                        setData(orgLocals);
                        orgAddPaymentRulesListAdapter.setSelectIndex(selectIndex);
                        orgAddPaymentRulesListAdapter.setSetValue(selectValue);
                        orgAddPaymentRulesListAdapter.notifyDataSetChanged();
                    }
                });
                break;
        }
    }

    @Override
    public void setDataPaymentOption(List<PaymentRulesOption> dataPaymentOptions) {
        paymentRulesOptions.addAll(dataPaymentOptions);
        if (orgAddPaymentRulesListAdapter != null) {
            orgAddPaymentRulesListAdapter.notifyDataSetChanged();
        }

        if (isEdit) {


            for (PaymentRulesOption paymentRulesOptionData : dataPaymentOptions) {
                if (paymentRulesOptionData.getId().equalsIgnoreCase(paymentRuleList.getPaymentOptionId())) {
                    selectValue = paymentRuleList.getPaymentValue();
                    break;
                }
                selectIndex = selectIndex + 1;
            }

            orgAddPaymentRulesListAdapter.setSelectIndex(selectIndex);
            orgAddPaymentRulesListAdapter.setSetValue(selectValue);
            if (orgAddPaymentRulesListAdapter != null) {
                orgAddPaymentRulesListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setDataUnAssignLocal(List<OrgLocal> orgLocalsData) {
        if (isEdit) {
            merageData(orgLocalsData, paymentRuleList.getAssignedLocal());
        } else {
            orgLocals.addAll(orgLocalsData);
            setData(orgLocals);
        }
    }

    private void merageData(List<OrgLocal> locals, List<PaymentRuleLocal> assignedLocal) {
        List<OrgLocal> orgLocalTemp = new ArrayList<>();

        for (OrgLocal orgLocal : locals) {

            boolean isSelect = false;

            for (PaymentRuleLocal assignedLocal1 : assignedLocal) {
                if (assignedLocal1.getLocalId().equalsIgnoreCase(orgLocal.getId())) {
                    isSelect = true;
                    break;
                }
            }
            orgLocal.setSelect(isSelect);

            orgLocalTemp.add(orgLocal);
        }

        orgLocals.clear();
        orgLocals.addAll(orgLocalTemp);

        setData(orgLocals);

    }

    private void setData(List<OrgLocal> data) {
        List<OrgLocal> orgLocalsTemp = new ArrayList<>();

        for (OrgLocal orgLocal : data) {
            if (orgLocal.isSelect()) {
                orgLocalsTemp.add(orgLocal);
            }
        }

        orgLocalsSelect.clear();
        orgLocalsSelect.addAll(orgLocalsTemp);
        if (orgAddNewPaymentRulesLocalListAdapter != null) {
            orgAddNewPaymentRulesLocalListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setDataOrganization(OrganizationDetails organizationDetails) {
        this.organizationDetails = organizationDetails;
    }

    @Override
    public void setPaymentRules(PaymentRuleList paymentRuleList) {
        this.paymentRuleList = paymentRuleList;
        paymentRuleId = paymentRuleList.getId();
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setIsSetBank(boolean isBank) {
        this.isSetBank = isBank;
    }

    private boolean isCheckSelectLocal() {
        boolean isSelect = true;

        for (OrgLocal orgLocal : orgLocals) {
            if (orgLocal.isSelect()) {
                return false;
            }
        }
        return isSelect;
    }

    public interface CallBack {
        void onData(List<OrgLocal> orgLocalsData);
    }
}
