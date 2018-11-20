package com.kooloco.uilocal.organaization.presenter;
/**
 * Created by hlink on 1/2/18.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.CheckBank;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationVisitor;
import com.kooloco.model.PaymentRuleList;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.organaization.view.SetPaymentRulesView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class SetPaymentRulesPresenter extends BasePresenter<SetPaymentRulesView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public SetPaymentRulesPresenter() {
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

    public void openAddPaymentRules(OrganizationDetails organizationDetails, boolean bank) {
        navigator.openAddNewPaymentRules().hasData(addNewPaymentRulesView -> {
            addNewPaymentRulesView.setDataOrganization(organizationDetails);
            addNewPaymentRulesView.setIsSetBank(bank);
        }).replace(true, "OrgAddBank");
    }

    public void getPaymentRules(OrganizationDetails organizationDetails) {
        view.hideLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("organisation_id", organizationDetails.getId());

        koolocoRepository.getSetPaymentRules(map).subscribe(new SubscribeWithView<Response<List<PaymentRuleList>>>(view) {
            @Override
            public void onSuccess(Response<List<PaymentRuleList>> listResponse) {
                view.hideLoader();
                view.setData(listResponse.getData());
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
                view.setData(new ArrayList<>());
            }
        });
    }

    public void callWsDelete(PaymentRuleList paymentRuleList, OrganizationDetails organizationDetails) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("payment_id", paymentRuleList.getId());

        koolocoRepository.deletePaymentRule(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.showMessage(listResponse.getMessage());
                getPaymentRules(organizationDetails);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openEditPaymentRules(OrganizationDetails organizationDetails, PaymentRuleList paymentRuleList, boolean bank) {
        navigator.openAddNewPaymentRules().hasData(addNewPaymentRulesView -> {
            addNewPaymentRulesView.setDataOrganization(organizationDetails);
            addNewPaymentRulesView.setIsEdit(true);
            addNewPaymentRulesView.setPaymentRules(paymentRuleList);
            addNewPaymentRulesView.setIsSetBank(bank);
        }).replace(true, "OrgAddBank");
    }

    public void openPreiviewDetails() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ORGPREVIEW).byFinishingCurrent().start();
    }

    public void getBankIsAdd(OrganizationDetails organizationDetails) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("organisation_id", organizationDetails.getId());
        map.put("user_id", session.getUser().getId());

        koolocoRepository.checkOrgBank(map).subscribe(new SubscribeWithView<Response<CheckBank>>(view) {
            @Override
            public void onSuccess(Response<CheckBank> listResponse) {
                view.hideLoader();
                view.setIsBank(listResponse.getData().isBank());
                getPaymentRules(organizationDetails);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}