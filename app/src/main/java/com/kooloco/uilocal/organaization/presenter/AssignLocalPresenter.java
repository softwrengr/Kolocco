package com.kooloco.uilocal.organaization.presenter;
/**
 * Created by hlink on 1/2/18.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.LocalImage;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.organaization.view.AssignLocalView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@PerActivity
public class AssignLocalPresenter extends BasePresenter<AssignLocalView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public AssignLocalPresenter() {

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

    public void getlocal() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);

        koolocoRepository.orgLocalList(map).subscribe(new SubscribeWithView<Response<List<OrgLocal>>>(view) {
            @Override
            public void onSuccess(Response<List<OrgLocal>> listResponse) {
                view.hideLoader();
                view.setData(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setData(new ArrayList<>());
            }
        });

    }

    public void openSetPriceRules(OrganizationDetails organizationDetails, List<OrgLocal> orgLocals) {
        view.showLoader();

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

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("organisation_id", organizationDetails.getId());
        map.put("local", localId);

        koolocoRepository.addToLocalOrg(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                navigator.openSetPaymentRules().hasData(setPaymentRulesView -> setPaymentRulesView.setOrderDetails(organizationDetails)).replace(true);
                view.showMessage(listResponse.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public Single<List<OrgLocal>> appplyFilter(List<OrgLocal> orgLocalsL, String text) {
        return Observable.fromIterable(orgLocalsL).subscribeOn(Schedulers.newThread()).filter(orgLocal -> orgLocal.getName().toUpperCase().contains(text.toUpperCase())).observeOn(AndroidSchedulers.mainThread()).toList();
    }

    public void openDashboardDetails(OrgLocal orgLocal, boolean isPreview) {

        DashboardDetails dashboardDetails = new DashboardDetails();
        dashboardDetails.setId(orgLocal.getId());

        Bundle bundle = new Bundle();
        bundle.putString("localDetails", new Gson().toJson(dashboardDetails));
        bundle.putBoolean("isPreviewScreen", isPreview);
        bundle.putBoolean("isOrgScreen", true);

        navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.Dashboard).start();
    }
}