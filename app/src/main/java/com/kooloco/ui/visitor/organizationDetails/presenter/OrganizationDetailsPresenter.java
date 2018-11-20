package com.kooloco.ui.visitor.organizationDetails.presenter;/**
 * Created by hlink44 on 19/9/17.
 */

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Activities;
import com.kooloco.model.CheckPaymentRules;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.LocalImage;
import com.kooloco.model.Notification;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationVisitor;
import com.kooloco.model.Response;
import com.kooloco.model.Service;
import com.kooloco.model.User;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.ui.visitor.organizationDetails.view.OrganizationDetailsView;
import com.kooloco.util.LocationManager;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

@PerActivity
public class OrganizationDetailsPresenter extends BasePresenter<OrganizationDetailsView> {
    @Inject
    KoolocoRepository kooocoRepository;
    List<OrgLocal> orgLocalsNew;

    @Inject
    Session session;

    @Inject
    public OrganizationDetailsPresenter() {

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

    public void back() {
        navigator.goBack();
    }

    public void getData(OrganizationVisitor organizationVisitor, boolean isPreview) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", organizationVisitor.getOwnerId());
        kooocoRepository.orgDetails(map).subscribe(new SubscribeWithView<Response<OrganizationDetails>>(view) {
            @Override
            public void onSuccess(Response<OrganizationDetails> organizationDetailsResponse) {
                view.hideLoader();
                if (isPreview) {
                    view.setData(organizationDetailsResponse.getData());
                } else {
                    applyDataFilter(organizationDetailsResponse.getData());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    private void applyDataFilter(OrganizationDetails data) {
        Observable.fromIterable(data.getOrgLocals()).filter(orgLocal -> orgLocal.getIsAccepted().equalsIgnoreCase("1") && orgLocal.getIsPaymentRulesAssign().equalsIgnoreCase("1")).toList().subscribe(orgLocals -> {
            data.setOrgLocals(orgLocals);
            view.setData(data);
        });
    }


    public String getSport(List<Service> services) {

        String sportId = "";

        for (Service service : services) {

            if (service.isSelect()) {
                if (sportId.isEmpty()) {
                    sportId = sportId + service.getName();
                } else {
                    sportId = sportId + "," + service.getName();
                }
            }

        }
        return sportId;
    }

    public String getActivity(List<Activities> activities) {
        String activityId = "";

        for (Activities activities1 : activities) {

            if (activityId.isEmpty()) {
                activityId = activityId + activities1.getName();
            } else {
                activityId = activityId + "," + activities1.getName();
            }

        }

        return activityId;
    }

    public void openOrganzationReport() {
        navigator.openOragizationReportView().replace(true);
    }

    public void callExitOrg(String organisationId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("affilated_id", organisationId);
        kooocoRepository.exitOrg(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response organizationDetailsResponse) {
                view.hideLoader();

                User user = session.getUser();
                user.setIsAffilated("0");
                session.setUser(user);

                view.showMessage(organizationDetailsResponse.getMessage());
                navigator.goBack();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void applyFilter(List<OrgLocal> orgLocalsTemp, List<Service> servicesSelect) {

        orgLocalsNew = new ArrayList<>();

        if (servicesSelect.isEmpty()) {
            orgLocalsNew.addAll(orgLocalsTemp);
            view.setNewSearch(orgLocalsNew);
        }

        for (OrgLocal orgLocal : orgLocalsTemp) {
            boolean isAdd = false;

            for (Service service : servicesSelect) {
                if (orgLocal.getSportCsvId().contains("," + service.getId() + ",")) {
                    isAdd = true;
                    break;
                }
            }

            if (isAdd) {
                orgLocalsNew.add(orgLocal);
            }

        }
        view.setNewSearch(orgLocalsNew);
    }

    public void openDashboardDetails(OrgLocal orgLocal, boolean isPreview) {
        DashboardDetails dashboardDetails = new DashboardDetails();
        dashboardDetails.setId(orgLocal.getLocalId());


        Bundle bundle = new Bundle();
        bundle.putString("localDetails", new Gson().toJson(dashboardDetails));
        bundle.putBoolean("isPreviewScreen", isPreview);
        bundle.putBoolean("isOrgScreen", true);

        navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.Dashboard).start();

    }

    public void openMapScreen(OrganizationDetails organizationDetails) {
        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setFirstname(organizationDetails.getOrgName());
        dashboardDetails.setLastname(" ");

        dashboardDetails.setImageUrl(organizationDetails.getImageUrl());

        dashboardDetails.setLocalLocation(organizationDetails.getOrgLocation());

        dashboardDetails.setLatitude(organizationDetails.getOrgLatitude());
        dashboardDetails.setLongitude(organizationDetails.getOrgLongitude());

        navigator.openLocalAndVistorMap().hasData(new Passable<LocalVisitorMapView>() {
            @Override
            public void passData(LocalVisitorMapView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);
    }

    public void deleteOrganization(String orgId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("organisation_id", orgId);

        kooocoRepository.deleteOrgnization(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.goBackData();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void isCheckPaymentRules() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        kooocoRepository.getIsCheckLocalAffilted(map).subscribe(new SubscribeWithView<Response<CheckPaymentRules>>(view) {
            @Override
            public void onSuccess(Response<CheckPaymentRules> checkPaymentRulesResponse) {
                view.hideLoader();
                if (checkPaymentRulesResponse.getData().getPaymentRuleText() == null) {
                    view.setTextPaymentRules("");
                } else {
                    view.setTextPaymentRules(checkPaymentRulesResponse.getData().getPaymentRuleText());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoader();
                super.onError(e);
                view.setTextPaymentRules("");
            }
        });
    }
}
