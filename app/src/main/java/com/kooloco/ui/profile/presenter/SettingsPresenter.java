package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 27/9/17.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kooloco.constant.Common;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.profile.view.SettingsView;
import com.kooloco.uilocal.organaization.view.AddBankView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

@PerActivity
public class SettingsPresenter extends BasePresenter<SettingsView> {
    @Inject
    AppPreferences appPreferences;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public SettingsPresenter() {

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

    public void openChangePassword() {
        navigator.openChangePasswordView().replace(true);
    }

    public void openAuth(FirebaseFirestore database) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.logout(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                Realm realm = Realm.getDefaultInstance();

                realm.beginTransaction();

                realm.deleteAll();

                realm.commitTransaction();

                if (appPreferences.getBoolean("isLogin")) {

                    Map<String, Object> chat = new HashMap<>();

                    chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TYPE, session.getUser().getDeviceType());
                    chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TOKEN, session.getUser().getDeviceId());
                    chat.put(Common.FireStore.FIELD_IS_ONLINE, false);
                    chat.put(Common.FireStore.FIELD_SENDER_IMAGE_URL, session.getUser().getProfileImage());

                    database.collection(Common.FireStore.TAB_NAME_USER).document(session.getUser().getId()).set(chat).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            Log.d(":::::: ", "Log out");
                        }
                    });

                }

                appPreferences.putBoolean("isLogin", false);
                appPreferences.clearAll();

                view.changeAppLanguageSetDefault();

                view.logout();

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openAddBankScreen() {
        navigator.openAddBankView().hasData(new Passable<AddBankView>() {
            @Override
            public void passData(AddBankView addBankView) {
                addBankView.setIsEdit(true);
            }
        }).replace(true);
    }

    public void callWsNotification() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getNotificationAction(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                view.showMessage(userResponse.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callWsNotificationEmail() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getNotificationEmailAction(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openAuthActivity() {
        navigator.startAuthenticationActivity().byFinishingAll().start();
    }

    public void openCardDetails() {
        navigator.openCreditCardView().replace(true);
    }

    public void openWebOpenView(String title) {

        Bundle bundle=new Bundle();
        bundle.putString(Common.WEBTITLE,title);
        bundle.putString(Common.WEBURL,URLFactory.TERMS_CONDITIONS);

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.OPENWEBVIEW).addBundle(bundle).start();
    }
}
