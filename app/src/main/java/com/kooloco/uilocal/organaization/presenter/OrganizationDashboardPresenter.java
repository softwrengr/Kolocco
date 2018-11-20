package com.kooloco.uilocal.organaization.presenter;/**
 * Created by hlink44 on 11/10/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.LocalImage;
import com.kooloco.model.OrderOrg;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDashboard;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.organaization.view.OrganizationDashboardView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class OrganizationDashboardPresenter extends BasePresenter<OrganizationDashboardView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public OrganizationDashboardPresenter() {

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

    public void getData(String orgId, String date) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("organisation_id", orgId);
        map.put("date", date);

        koolocoRepository.getOrgDashboard(map).subscribe(new SubscribeWithView<Response<OrganizationDashboard>>(view) {
            @Override
            public void onSuccess(Response<OrganizationDashboard> orderOrgResponse) {
                view.hideLoader();
                view.setData(orderOrgResponse.getData().getOrderOrgs());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setData(new ArrayList<>());
            }
        });
    }

    public void openDashboardDetails(String localId, boolean isPreview) {

        DashboardDetails dashboardDetails = new DashboardDetails();
        dashboardDetails.setId(localId);
        Bundle bundle = new Bundle();
        bundle.putString("localDetails", new Gson().toJson(dashboardDetails));
        bundle.putBoolean("isPreviewScreen", isPreview);
        bundle.putBoolean("isOrgScreen", true);

        navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.Dashboard).start();
    }
}
