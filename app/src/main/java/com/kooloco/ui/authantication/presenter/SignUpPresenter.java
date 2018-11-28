package com.kooloco.ui.authantication.presenter;/**
 * Created by hlink44 on 24/8/17.
 */


import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.FbGoogleData;
import com.kooloco.model.Response;
import com.kooloco.model.Service;
import com.kooloco.model.User;
import com.kooloco.ui.authantication.view.SignUpView;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class SignUpPresenter extends BasePresenter<SignUpView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    AppPreferences appPreferences;

    @Inject
    public SignUpPresenter() {

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

    public void openHomeActivity(List<Service> services) {
        appPreferences.putBoolean("isLogin", true);
        Bundle bundle = new Bundle();
        bundle.putString("sports", new Gson().toJson(services));
        appPreferences.putBoolean("isOnBoard", false);

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ONBORDING).addBundle(bundle).byFinishingAll().start();

    }


    public void imagePick(ImagePicker.ImagePickerResult imagePickerResult) {
        navigator.pickImage(imagePickerResult);
    }

    public void signUp(final String firstName, final String lastName, final String emailAddress, final String password, final String referalCode, final String imageUrl, final boolean isSocial, final String signUpType, final String socialId) {
        view.showLoader();

        view.uploadImages(imageUrl, "signup", new BaseFragment.CallBackUploadImages() {
            @Override
            public void onCallBack(String imagePath) {

                view.showLoader();

                HashMap<String, String> map = new HashMap<>();
                map.put("firstname", firstName);
                map.put("lastname", lastName);
                map.put("email", emailAddress);

                if (!isSocial) {
                    map.put("password", password);
                }
                if (isSocial) {
                    map.put("social_id", socialId);
                }

                map.put("signup_type", signUpType);
                map.put("latitude", "" + BaseFragment.latitude);
                map.put("longitude", "" + BaseFragment.longitude);
                map.put("device_type", session.DEVICE_TYPE);
                map.put("device_id", session.getDeviceId());
                map.put("timezone", Calendar.getInstance().getTimeZone().getID());
                map.put("API-KEY", "kooloco");

                if (!referalCode.isEmpty()) {
                    map.put("referral_code", referalCode);
                }

                if (!imagePath.isEmpty()) {
                    map.put("profile_image", imagePath);
                }

                kooocoRepository.postSignup(map).subscribe(new SubscribeWithView<Response<User>>(view) {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        view.hideLoader();
                        openHomeActivity(userResponse.getData().getServices());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.hideLoader();
                    }
                });

            }
        });

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
                openHomeActivity(userResponse.getData().getServices());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                if (e instanceof ServerException) {
                    ServerException serverException = (ServerException) e;
                    if (serverException.getServerCode() == 0) {
                        view.setDataSignup(fbGoogleData);
                    }
                }
            }
        });
    }
}
