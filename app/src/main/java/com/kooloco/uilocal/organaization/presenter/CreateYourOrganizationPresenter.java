package com.kooloco.uilocal.organaization.presenter;/**
 * Created by hlink44 on 10/10/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Activities;
import com.kooloco.model.FilterGetData;
import com.kooloco.model.MultiFile;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.model.Service;
import com.kooloco.model.SportPriceRulesSport;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.organaization.view.CreateYourOrganizationView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


@PerActivity
public class CreateYourOrganizationPresenter extends BasePresenter<CreateYourOrganizationView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public CreateYourOrganizationPresenter() {

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

    public void imagePick(ImagePicker.ImagePickerResult imagePickerResult) {
        navigator.pickImage(imagePickerResult);
    }

    public void imagePickLogo(ImagePicker.ImagePickerResult imagePickerResult) {
        navigator.pickImageCustom(imagePickerResult);
    }

    public void openPreviewScreen() {
        navigator.openPreviewDetailsView().replace(true);
    }

    public void openOrganaizationDetails() {
        navigator.openOrgniztionDashboardView().replace(true);
    }

    public void openAddBankDetails() {
        navigator.openAddBankView().replace(true);
    }

    public void openAddBankPerDetails() {
        navigator.openAddBankPerView().replace(true);
    }

    public void openAssignLocal(OrganizationDetails organizationDetails) {
        if (organizationDetails != null) {
            Bundle bundle = new Bundle();

            bundle.putString("orgDetails", new Gson().toJson(organizationDetails));

            navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ORGADDLOCAL).addBundle(bundle).byFinishingCurrent().start();

            //navigator.openAddLocal().hasData(assignLocalView -> assignLocalView.setOrderDetails(organizationDetails)).replace(true);
        }
    }


    public void getFilterData() {

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        koolocoRepository.filterData(map).subscribe(new SubscribeWithView<Response<FilterGetData>>(view) {
            @Override
            public void onSuccess(Response<FilterGetData> filterGetDataResponse) {
                view.hideLoader();
                view.setData(filterGetDataResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    /**
     * It is used to Create organization
     *
     * @param name
     * @param logo
     * @param descripation
     * @param location
     * @param latitude
     * @param longitude
     * @param weburl
     * @param services
     * @param activities
     * @param multiFiles
     */
    public void callWsCrateOrg(String name, String logo, String descripation, String location, String latitude, String longitude, String weburl, List<Service> services, List<Activities> activities, List<MultiFile> multiFiles) {

        String activityId = "";

        for (Activities activities1 : activities) {

            if (activities1.isSelect()) {
                if (activityId.isEmpty()) {
                    activityId = activityId + activities1.getId();
                } else {
                    activityId = activityId + "," + activities1.getId();
                }
            }

        }

        String sportId = "";

        for (Service service : services) {

            if (service.isSelect()) {
                if (sportId.isEmpty()) {
                    sportId = sportId + service.getId();
                } else {
                    sportId = sportId + "," + service.getId();
                }
            }

        }


        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("name", name);
        map.put("logo", logo);
        map.put("description", descripation);
        map.put("location", location);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("site_url", weburl);
        map.put("media", new Gson().toJson(multiFiles));
        map.put("activity", activityId);
        map.put("sports", sportId);

        koolocoRepository.createOrg(map).subscribe(new SubscribeWithView<Response<OrganizationDetails>>(view) {
            @Override
            public void onSuccess(Response<OrganizationDetails> organizationDetailsResponse) {
                view.hideLoader();

                User user = session.getUser();
                user.setIsOrgaization("1");
                session.setUser(user);
                EventBus.getDefault().post(EventBusAction.UPDATELIST);
                EventBus.getDefault().post(EventBusAction.UPDATEORG);
                openAssignLocal(organizationDetailsResponse.getData());
                // view.showMessage(organizationDetailsResponse.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
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

            if (activities1.isSelect()) {
                if (activityId.isEmpty()) {
                    activityId = activityId + activities1.getName();
                } else {
                    activityId = activityId + "," + activities1.getName();
                }
            }

        }

        return activityId;
    }


}
