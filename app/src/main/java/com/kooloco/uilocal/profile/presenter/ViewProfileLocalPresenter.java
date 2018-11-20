package com.kooloco.uilocal.profile.presenter;/**
 * Created by hlink44 on 9/10/17.
 */

import android.os.Bundle;

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.OrganizationVisitor;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.addservice.view.AddImageView;
import com.kooloco.uilocal.addservice.view.CancellationPoliciesView;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesNewView;
import com.kooloco.uilocal.addservice.view.ChooseSportEquipmentsView;
import com.kooloco.uilocal.addservice.view.SetLocationView;
import com.kooloco.uilocal.addservice.view.UploadAchievementsView;
import com.kooloco.uilocal.addservice.view.UploadCertificationsView;
import com.kooloco.uilocal.profile.view.ViewProfileLocalView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ViewProfileLocalPresenter extends BasePresenter<ViewProfileLocalView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    public ViewProfileLocalPresenter() {

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

    public void getData() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        kooocoRepository.getLocalProfile(map).subscribe(new SubscribeWithView<Response<DashboardDetails>>(view) {
            @Override
            public void onSuccess(Response<DashboardDetails> organizationDetailsResponse) {
                view.hideLoader();
                view.setData(organizationDetailsResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openEditProfile() {
//        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EditProfile).start();
        navigator.openEditProfileView().hasData(editProfileView -> editProfileView.setIsLocal(true)).replace(true);
    }

    public void openEditOrganization() {
        navigator.openEditOrganizationView().replace(true);
    }

    public void openEditAchievements() {
        navigator.openUploadAchievementsView().hasData(new Passable<UploadAchievementsView>() {
            @Override
            public void passData(UploadAchievementsView uploadAchievementsView) {
                uploadAchievementsView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openCertification() {
        navigator.openUploadCertificateView().hasData(new Passable<UploadCertificationsView>() {
            @Override
            public void passData(UploadCertificationsView uploadCertificationsView) {
                uploadCertificationsView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openCancellationPolicy() {
        navigator.openCancellationPoliciesView().hasData(new Passable<CancellationPoliciesView>() {
            @Override
            public void passData(CancellationPoliciesView cancellationPoliciesView) {
                cancellationPoliciesView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openAddImages() {
        navigator.openAddImageView().hasData(new Passable<AddImageView>() {
            @Override
            public void passData(AddImageView addImageView) {
                addImageView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openChooseSportEquipments() {
        navigator.openChooseSportEquipmentsView().hasData(new Passable<ChooseSportEquipmentsView>() {
            @Override
            public void passData(ChooseSportEquipmentsView chooseSportEquipmentsView) {
                chooseSportEquipmentsView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openChooseSport() {
        navigator.openChooseSportActivityNewView().hasData(new Passable<ChooseSportActivitiesNewView>() {
            @Override
            public void passData(ChooseSportActivitiesNewView chooseSportActivitiesNewView) {
                chooseSportActivitiesNewView.setIsEdit(true);
            }
        }).replace(true);

    }

    public void openSetLocation(final DashboardDetails dashboardDetails) {
        navigator.openSetLocationView().hasData(new Passable<SetLocationView>() {
            @Override
            public void passData(SetLocationView setLocationView) {
                setLocationView.setDashboardDetails(dashboardDetails);
                setLocationView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openOrganizationDetails(OrganizationVisitor organizationVisitor) {
        navigator.openOrganisationDetails().hasData(organizationDetailsView -> {organizationDetailsView.setDataOrgVisitor(organizationVisitor);
        organizationDetailsView.setIsLocal(true);}).replace(true);

    }

    public void openEditExperience(ExpereinceNew expereinceNew) {
        navigator.openExperienceEditView().hasData(editExperienceView -> {
            editExperienceView.setExpId(expereinceNew.getId());
            editExperienceView.setExpNew(expereinceNew);
        }).replace(true);
    }

    public void openExpAddDetails() {
        // navigator.openExperienceAddDetailsView().replace(true);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPADDDETAILS).start();
    }

    public void openExpDetails(ExpereinceNew expereinceNew) {
        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCALSIDE).addBundle(bundle).start();

        //       navigator.openExperienceDetailsView().hasData(experienceDetailsView -> experienceDetailsView.setIsLocalApp(true)).replace(true);
    }

    public void callWsDeleteExp(int pos, ExpereinceNew expereinceNew) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expereinceNew.getId());
        kooocoRepository.deleteExp(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.deleteExp(pos, expereinceNew);
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();

            }
        });
    }

    public void openSpeakLanguage() {
        navigator.openSpeekLanguageView().replace(true);
    }
}
