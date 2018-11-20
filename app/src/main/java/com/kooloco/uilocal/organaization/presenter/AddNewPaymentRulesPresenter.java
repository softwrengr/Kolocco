package com.kooloco.uilocal.organaization.presenter;
/**
 * Created by hlink on 1/2/18.
 */

import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.OrgBankSetPaymentRules;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.PaymentRulesOption;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.organaization.fragment.AddNewPaymentRulesFragment;
import com.kooloco.uilocal.organaization.view.AddNewPaymentRulesView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

@PerActivity
public class AddNewPaymentRulesPresenter extends BasePresenter<AddNewPaymentRulesView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public AddNewPaymentRulesPresenter() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void openLocalAssign(OrganizationDetails organizationDetails, List<OrgLocal> orgLocals, AddNewPaymentRulesFragment.CallBack callBack) {
        if (organizationDetails != null) {
            navigator.openAssignLocal().hasData(assignLocalView -> {
                assignLocalView.setOrderDetails(organizationDetails);
                assignLocalView.setOrgLocal(orgLocals);
                assignLocalView.setCallBack(callBack);
            }).replace(true);
        }
    }

    public void getData(OrganizationDetails organizationDetails, String paymentRuleId) {
        view.showLoader();
        Map<String, String> param = new HashMap<>();

        koolocoRepository.getPaymentRulesOption(param).subscribe(new SubscribeWithView<Response<List<PaymentRulesOption>>>(view) {
            @Override
            public void onSuccess(Response<List<PaymentRulesOption>> listResponse) {
                getUnAssignLocal(organizationDetails, listResponse.getData(), paymentRuleId);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();

                getUnAssignLocal(organizationDetails, new ArrayList<>(), paymentRuleId);

            }
        });
    }

    public void getUnAssignLocal(OrganizationDetails organizationDetails, List<PaymentRulesOption> paymentRulesOptions, String paymentRuleId) {
        Map<String, String> param = new HashMap<>();
        param.put("organisation_id", organizationDetails.getId());
        param.put("payment_id", paymentRuleId);

        koolocoRepository.getUnAssignLocal(param).subscribe(new SubscribeWithView<Response<List<OrgLocal>>>(view) {
            @Override
            public void onSuccess(Response<List<OrgLocal>> listResponse) {
                view.hideLoader();
                view.setDataPaymentOption(paymentRulesOptions);
                view.setDataUnAssignLocal(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    }
                } else {
                    super.onError(e);
                }

                view.hideLoader();
                view.setDataPaymentOption(paymentRulesOptions);
                view.setDataUnAssignLocal(new ArrayList<>());
            }
        });

    }

    public void callAddPaymentRules(String title, OrganizationDetails organizationDetails, List<OrgLocal> orgLocals, PaymentRulesOption paymentRulesOptions, String payment_value, boolean isSetBank) {

        //{"user_id":"1","organisation_id":"1","title":"Payment Rule 1","local":"18","payment_option_id":"1","rule_type":"split","payment_value":"10"}

        if (isSetBank) {
            view.showLoader();
        }

        String localId = "";

        for (OrgLocal orgLocal : orgLocals) {
            if (orgLocal.isSelect()) {
                if (localId.isEmpty()) {
                    localId = localId + orgLocal.getId();
                } else {
                    localId = localId + "," + orgLocal.getId();
                }
            }
        }

        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());
        param.put("organisation_id", organizationDetails.getId());
        param.put("title", title);

        param.put("payment_option_id", paymentRulesOptions.getId());
        param.put("rule_type", paymentRulesOptions.getRuleType());
        param.put("payment_value", payment_value);

        param.put("local", localId);

        if (!isSetBank) {
            OrgBankSetPaymentRules orgBankSetPaymentRules = new OrgBankSetPaymentRules();
            orgBankSetPaymentRules.setEdit(false);
            orgBankSetPaymentRules.setMap(param);
            openAddPaymentRules(orgBankSetPaymentRules, organizationDetails.getId());
            return;
        }

        koolocoRepository.setPaymentRule(param).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.showMessage(listResponse.getMessage());
                EventBus.getDefault().post(EventBusAction.UPDATEORG);
                navigator.goBack();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }


    private void openAddPaymentRules(OrgBankSetPaymentRules orgBankSetPaymentRules, String orgId) {
        navigator.openOrgAddBankView().hasData(orgAddBankView -> {
            orgAddBankView.setIsEdit(true);
            orgAddBankView.setAddBankPayment(orgBankSetPaymentRules);
            orgAddBankView.setOrgId(orgId);
        }).replace(true, "OrgAddBank");
    }

    public void callEditPaymentRules(String id, String title, OrganizationDetails organizationDetails, List<OrgLocal> orgLocals, PaymentRulesOption paymentRulesOptions, String payment_value, boolean isSetBank) {
        if (isSetBank) {
            view.showLoader();
        }

        String localId = "";

        for (OrgLocal orgLocal : orgLocals) {
            if (orgLocal.isSelect()) {
                if (localId.isEmpty()) {
                    localId = localId + orgLocal.getId();
                } else {
                    localId = localId + "," + orgLocal.getId();
                }
            }
        }

        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());
        param.put("organisation_id", organizationDetails.getId());
        param.put("title", title);
        param.put("payment_rule_id", id);

        param.put("payment_option_id", paymentRulesOptions.getId());
        param.put("rule_type", paymentRulesOptions.getRuleType());
        param.put("payment_value", payment_value);

        param.put("local", localId);

        if (!isSetBank) {
            OrgBankSetPaymentRules orgBankSetPaymentRules = new OrgBankSetPaymentRules();
            orgBankSetPaymentRules.setEdit(true);
            orgBankSetPaymentRules.setMap(param);
            openAddPaymentRules(orgBankSetPaymentRules, organizationDetails.getId());
            return;
        }

        koolocoRepository.editPaymentRule(param).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.showMessage(listResponse.getMessage());
                navigator.goBack();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

}