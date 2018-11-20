package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 22/9/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.profile.view.ProfileView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ProfilePresenter extends BasePresenter<ProfileView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ProfilePresenter() {

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

    public void openViewProfile() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ViewProfile).start();

    }

    public void openInviteFriends() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.InviteFriends).start();
        // navigator.openInviteView().replace(true);
    }

    public void tempOpen() {
        // navigator.openNotificationView().replace(true);
    }

    public void openFav() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Fav).start();
//        navigator.openFavoritesView().replace(true);
    }

    public void openSetting() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Setting).start();
        //navigator.openSettingView().replace(true);
    }

    public void openKoolocoHelp() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.HelpAndFaq).start();
        // navigator.openHelpAndFaqView().replace(true);
    }

    public void openOrderHistory() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.OrderHistory).start();
    }

    public void openLocalApp() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        koolocoRepository.setBecomeLocal(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                if (userResponse.getData().getSignupStep().equalsIgnoreCase("0")) {
//                    view.showCustomMessage(userResponse.getMessage());
                    openBecomeLocalWebView();
                } else {
                    openBecomeLocal();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

        //Remove comment after complete code
        //navigator.startHomeLocalActivity().start();
    }


    public void openEditProfile() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EditProfile).start();
    }

    public void openChooseLanguage() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ChooseLanguage).start();
    }


    public void openBecomeLocal() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.BecomeLocal).start();
    }

    public void openBecomeLocalWebView() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.BecomeLocalWebView).start();
    }


    public void getData() {
        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());

        koolocoRepository.getProfileVisitor(param).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                session.setUser(userResponse.getData());
                view.updateData();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void openNotification() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Notification).start();
    }

    public void openCurrency() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.VISITORCURRENCY).start();
    }

}
