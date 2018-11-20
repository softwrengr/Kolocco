package com.kooloco.uilocal.profile.presenter;
/**
 * Created by hlink on 20/3/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.addservice.view.AddImageView;
import com.kooloco.uilocal.addservice.view.CancellationPoliciesView;
import com.kooloco.uilocal.addservice.view.ChooseLanguagesView;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesNewView;
import com.kooloco.uilocal.addservice.view.ChooseSportEquipmentsView;
import com.kooloco.uilocal.addservice.view.SetLocationView;
import com.kooloco.uilocal.addservice.view.UploadAchievementsView;
import com.kooloco.uilocal.addservice.view.UploadCertificationsView;
import com.kooloco.uilocal.profile.view.LocalIncompleteStepView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class LocalIncompleteStepPresenter extends BasePresenter<LocalIncompleteStepView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public LocalIncompleteStepPresenter() {
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


    public void openAddImages() {
        navigator.openAddImageView().hasData(new Passable<AddImageView>() {
            @Override
            public void passData(AddImageView addImageView) {
                addImageView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openChooseLanguage() {
        navigator.openChooseLangaugeView().hasData(new Passable<ChooseLanguagesView>() {
            @Override
            public void passData(ChooseLanguagesView chooseLanguagesView) {
                chooseLanguagesView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openSport() {
        navigator.openChooseSportActivityNewView().hasData(new Passable<ChooseSportActivitiesNewView>() {
            @Override
            public void passData(ChooseSportActivitiesNewView chooseSportActivitiesNewView) {
                chooseSportActivitiesNewView.setIsEdit(true);
                chooseSportActivitiesNewView.setIsInComplete(true);
            }
        }).replace(true);
    }

    public void openUploadCertificates() {
        navigator.openUploadCertificateView().hasData(new Passable<UploadCertificationsView>() {
            @Override
            public void passData(UploadCertificationsView uploadCertificationsView) {
                uploadCertificationsView.setIsEdit(true);
            }
        }).replace(true);

    }

    public void openUploadAchivements() {
        navigator.openUploadAchievementsView().hasData(new Passable<UploadAchievementsView>() {
            @Override
            public void passData(UploadAchievementsView uploadAchievementsView) {
                uploadAchievementsView.setIsEdit(true);
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

    public void openAvailability() {
        navigator.openSetAvabilitiesProfileView().hasData(setAvabilityView -> setAvabilityView.setIsEdit(true)).replace(true);

    }

    public void openLocationView() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        koolocoRepository.getLocalProfile(map).subscribe(new SubscribeWithView<Response<DashboardDetails>>(view) {
            @Override
            public void onSuccess(Response<DashboardDetails> organizationDetailsResponse) {

                User user = session.getUser();

                user.setRate(organizationDetailsResponse.getData().getLocalRating());
                user.setFirstname(organizationDetailsResponse.getData().getFirstname());
                user.setLastname(organizationDetailsResponse.getData().getLastname());
                user.setIsWantToCompelte(organizationDetailsResponse.getData().getIsWantToCompelte());
                user.setProfileStatuses(organizationDetailsResponse.getData().getProfileStatuses());

                session.setUser(user);

                view.hideLoader();

                navigator.openSetLocationView().hasData(new Passable<SetLocationView>() {
                    @Override
                    public void passData(SetLocationView setLocationView) {
                        setLocationView.setDashboardDetails(organizationDetailsResponse.getData());
                        setLocationView.setIsEdit(true);
                    }
                }).replace(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    /*    navigator.openSetLocationView().hasData(new Passable<SetLocationView>() {
            @Override
            public void passData(SetLocationView setLocationView) {
                setLocationView.setDashboardDetails(dashboardDetails);
                setLocationView.setIsEdit(true);
            }
        }).replace(true);*/
    }

    public void openCancellationPolicy() {
        navigator.openCancellationPoliciesView().hasData(new Passable<CancellationPoliciesView>() {
            @Override
            public void passData(CancellationPoliciesView cancellationPoliciesView) {
                cancellationPoliciesView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void openAddBank() {
        navigator.openAddBankView().hasData(setAvabilityView -> setAvabilityView.setIsEdit(true)).replace(true);
    }

    public void openExp() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.LOCALEXP).start();
    }

    public void getDataUser() {

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getLocalProfile(map).subscribe(new SubscribeWithView<Response<DashboardDetails>>(view) {
            @Override
            public void onSuccess(Response<DashboardDetails> organizationDetailsResponse) {

                User user = session.getUser();

                user.setRate(organizationDetailsResponse.getData().getLocalRating());
                user.setFirstname(organizationDetailsResponse.getData().getFirstname());
                user.setLastname(organizationDetailsResponse.getData().getLastname());
                user.setIsWantToCompelte(organizationDetailsResponse.getData().getIsWantToCompelte());
                user.setProfileStatuses(organizationDetailsResponse.getData().getProfileStatuses());
                user.setIsAdminApprove(organizationDetailsResponse.getData().getIsAdminApprove());
                user.setIsAffilated(organizationDetailsResponse.getData().getIsAffilated());

                session.setUser(user);

                view.updateData();

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

}