package com.kooloco.uilocal.organaization.presenter;/**
 * Created by hlink44 on 16/10/17.
 */

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Activities;
import com.kooloco.model.AddImages;
import com.kooloco.model.FilterGetData;
import com.kooloco.model.MultiFile;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.model.Service;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.organaization.view.EditOrganizationView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class EditOrganizationPresenter extends BasePresenter<EditOrganizationView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public EditOrganizationPresenter() {

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

    public void openPreviewDetails() {
        navigator.openLocalPreviewDetailsView().replace(true);
    }


    public void getFilterData() {

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        koolocoRepository.filterData(map).subscribe(new SubscribeWithView<Response<FilterGetData>>(view) {
            @Override
            public void onSuccess(Response<FilterGetData> filterGetDataResponse) {
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
     * @param organizationDetails
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
    public void callWsCrateOrg(OrganizationDetails organizationDetails, String name, String logo, String descripation, String location, String latitude, String longitude, String weburl, List<Service> services, List<Activities> activities, List<MultiFile> multiFiles) {

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
        map.put("organisation_id", organizationDetails.getId());
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

        koolocoRepository.editOrg(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response organizationDetailsResponse) {
                view.hideLoader();
                navigator.goBack();
                view.showMessage(organizationDetailsResponse.getMessage());
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

    public void getOrgData() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.orgDetails(map).subscribe(new SubscribeWithView<Response<OrganizationDetails>>(view) {
            @Override
            public void onSuccess(Response<OrganizationDetails> organizationDetailsResponse) {
                view.hideLoader();
                view.setDataOrg(organizationDetailsResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public List<Activities> mapActivity(List<Activities> activitiesAll, List<Activities> activitiesSelect) {
        List<Activities> activities = new ArrayList<>();

        for (Activities activitie : activitiesAll) {
            boolean isSelect = false;
            for (Activities activities1 : activitiesSelect) {
                if (activitie.getId().equalsIgnoreCase(activities1.getId())) {
                    isSelect = true;
                    break;
                }
            }
            activitie.setSelect(isSelect);
            activities.add(activitie);

        }

        return activities;
    }

    public List<Service> mapSport(List<Service> activitiesAll, List<Service> activitiesSelect) {
        List<Service> activities = new ArrayList<>();

        for (Service activitie : activitiesAll) {

            boolean isSelect = false;
            for (Service activities1 : activitiesSelect) {
                if (activitie.getId().equalsIgnoreCase(activities1.getId())) {
                    isSelect = true;
                    break;
                }
            }
            activitie.setSelect(isSelect);
            activities.add(activitie);
        }

        return activities;
    }

    public void callDeleteMediea(AddImages addImagesD) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("media_id", addImagesD.getSetId());

        koolocoRepository.deleteOrgMediea(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();
                view.deleteMedia(addImagesD);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void openAddLocal(OrganizationDetails organizationDetails) {
        if (organizationDetails != null) {
            navigator.openAssignLocal().hasData(assignLocalView -> assignLocalView.setOrderDetails(organizationDetails)).replace(true);
        }
    }

    public void openSetPriceRules(OrganizationDetails organizationDetails) {
        if (organizationDetails != null) {
            navigator.openSetPaymentRules().hasData(setPaymentRulesView -> setPaymentRulesView.setOrderDetails(organizationDetails)).replace(true);
        }
    }
}
