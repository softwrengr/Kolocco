package com.kooloco.uilocal.organaization.presenter;
/**
 * Created by hlink on 28/3/18.
 */


import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.constant.EventBusAction;
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
import com.kooloco.uilocal.organaization.view.AddLocalView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@PerActivity
public class AddLocalPresenter extends BasePresenter<AddLocalView> {


    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public AddLocalPresenter() {
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


    public void getlocal(String idOrg) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);
        map.put("organisation_id", idOrg);

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

    public void openSetPriceRules(OrganizationDetails organizationDetails, List<OrgLocal> orgLocals, boolean isCreateOrg) {
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

                if (isCreateOrg) {
                    if (organizationDetails != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("orgDetails", new Gson().toJson(organizationDetails));
                        EventBus.getDefault().post(EventBusAction.UPDATEORG);
                        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ORGSETPAYMENTRULES).addBundle(bundle).byFinishingCurrent().start();
                        //navigator.openAddLocal().hasData(assignLocalView -> assignLocalView.setOrderDetails(organizationDetails)).replace(true);
                    }
                } else {
                    navigator.goBack();
                }
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

    public void deleteLocalOrg(int position, OrganizationDetails organizationDetails, OrgLocal orgLocal) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("organisation_id", organizationDetails.getId());
        map.put("local_id", orgLocal.getId());

        koolocoRepository.getdeleteLocalOrg(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();

                orgLocal.setIsAdded("0");
                view.setModifyData(position, orgLocal);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
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