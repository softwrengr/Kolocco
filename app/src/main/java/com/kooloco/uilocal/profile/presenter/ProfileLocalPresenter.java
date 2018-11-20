package com.kooloco.uilocal.profile.presenter;/**
 * Created by hlink44 on 6/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ProfileStatus;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.profile.view.ProfileLocalView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ProfileLocalPresenter extends BasePresenter<ProfileLocalView> {
    @Inject
    Session session;
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ProfileLocalPresenter() {

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
                user.setIsBank(organizationDetailsResponse.getData().getIsBank());

                user.setCountryCode(organizationDetailsResponse.getData().getCountryCode());

                session.setUser(user);

                view.updateData();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void openViewProfile() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ViewProfileLocal).start();
    }

    public void openCreateOrganization() {
        //   navigator.openCreateOrganization().replace(true);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.CreateYourOrganization).start();
    }

    public void openEditOrganization() {
        //   navigator.openCreateOrganization().replace(true);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EditOrganization).start();
    }

    public void openSetAvailabilities() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.SetAvabilities).start();

    }

    public void openEditProfile() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EditProfile).start();
    }

    public void openKoolocoHelp() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.HelpAndFaq).start();

    }

    public void openSettings() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Setting).start();

    }

    public void openChooseLanguage() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ChooseLanguage).start();
    }

    public void openBecomeVisitor() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        koolocoRepository.setVisitor(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                navigator.startHomeActivity().byFinishingAll().start();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void getStepData() {
        int stepPendingCount = 0;
        for (ProfileStatus profileStatus : session.getUser().getProfileStatuses()) {
            if (profileStatus.getIsComplete().equalsIgnoreCase("0")) {
                stepPendingCount = stepPendingCount + 1;
            }
        }

        view.setStepCountData(stepPendingCount, session.getUser().getProfileStatuses().size());

    }

    public void openLocalInCompleteView() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.LOCALINCOMPLETE).start();

    }

    public void callWsDontCompleteProfile() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        koolocoRepository.getdontWantComplete(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response userResponse) {
                getDataWithLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void getDataWithLoader() {
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
                user.setIsBank(organizationDetailsResponse.getData().getIsBank());

                session.setUser(user);

                view.updateData();

                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();

            }
        });
    }


    public void openOrgStatus() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ORGSTATUS).start();
    }

    public void openExp() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.LOCALEXP).start();
    }

    public void openNotification() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.NotificationLocal).start();
    }

    public void openAddBank() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ADDBANKLOCAL).start();
    }
}
