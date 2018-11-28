package com.kooloco.ui.authantication.presenter;
/**
 * Created by hlink44 on 23/8/17.
 */

import android.os.Bundle;

import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.FbGoogleData;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.authantication.view.LoginView;
import com.kooloco.ui.authantication.view.SignUpView;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.util.SubscribeWithView;

import java.util.Calendar;
import java.util.HashMap;

import javax.inject.Inject;

@PerActivity
public class LoginPresenter extends BasePresenter<LoginView> {
    @Inject
    KoolocoRepository kooocoRepository;
    @Inject
    Session session;
    @Inject
    AppPreferences appPreferences;

    @Inject
    public LoginPresenter() {
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

    public void openSignUp() {
        navigator.openSignup().replace(true);
    }

    public void login(String emailAddress, String password) {
        view.showLoader();
        HashMap<String, String> map = new HashMap<>();
        map.put("email", emailAddress);
        map.put("password", password);
        map.put("API-KEY", "kooloco");
//        map.put("latitude", "" + BaseFragment.latitude);
//        map.put("longitude", "" + BaseFragment.longitude);
//        map.put("device_type", session.DEVICE_TYPE);
//        map.put("device_id", session.getDeviceId());
//        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        kooocoRepository.postLogin(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();

                if (!userResponse.getData().getAppLang().isEmpty()) {
                    view.changeAppLanguage(userResponse.getData().getAppLang());
                }

                if (userResponse.getData().getRole().equalsIgnoreCase("L") && userResponse.getData().getIsAdminApprove().equalsIgnoreCase("1")) {
                    openLocalHomeActivity();
                } else {
                    openHomeActivity();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openHomeActivity() {
        appPreferences.putBoolean("isLogin", true);
        appPreferences.putBoolean("isOnBoard", session.getUser().getIsOnBoarding().equalsIgnoreCase("1"));
        if (session.getUser().getIsOnBoarding().equalsIgnoreCase("1")) {
            navigator.startHomeActivity().byFinishingAll().start();
        } else {
            openOnBoarding();
        }
    }

    public void openLocalHomeActivity() {
        appPreferences.putBoolean("isLogin", true);
        appPreferences.putBoolean("isOnBoard", session.getUser().getIsOnBoarding().equalsIgnoreCase("1"));
        if (session.getUser().getIsOnBoarding().equalsIgnoreCase("1")) {
            navigator.startHomeLocalActivity().byFinishingAll().start();
        } else {
            openOnBoarding();
        }
    }

    private void openOnBoarding() {
        Bundle bundle = new Bundle();
        bundle.putString("sports", "[]");
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ONBORDING).addBundle(bundle).byFinishingAll().start();
    }

    public void checkSocialId(final FbGoogleData fbGoogleData) {
        view.showLoader();
        HashMap<String, String> map = new HashMap<>();
        map.put("social_id", fbGoogleData.getIsSocialId());
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);
        map.put("device_type", session.DEVICE_TYPE);
        map.put("device_id", session.getDeviceId());
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        kooocoRepository.postCheckSocial(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                if (userResponse.getData().getRole().equalsIgnoreCase("L") && userResponse.getData().getIsAdminApprove().equalsIgnoreCase("1")) {
                    openLocalHomeActivity();
                } else {
                    openHomeActivity();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                if (e instanceof ServerException) {
                    ServerException serverException = (ServerException) e;
                    if (serverException.getServerCode() == 0) {
                        navigator.openSignup().hasData(new Passable<SignUpView>() {
                            @Override
                            public void passData(SignUpView signUpView) {
                                signUpView.setDataSignupNew(fbGoogleData);
                            }
                        }).replace(true);
                    }
                }
            }
        });
    }

    public void openForgotPassword() {
        navigator.openForgotPasswordView().replace(true);
    }
}
