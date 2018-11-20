package com.kooloco.ui.onboarding.presenter;
/**
 * Created by hlink on 18/5/18.
 */

import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.OnBoardingAnswer;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.onboarding.view.OnBoardingActivityView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class OnBoardingActivityPresenter extends BasePresenter<OnBoardingActivityView> {

    @Inject
    AppPreferences appPreferences;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public OnBoardingActivityPresenter() {
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

    public void openHomeActivity() {
        navigator.startHomeActivity().byFinishingAll().start();
    }

    public void callWs(String onBoardingData) {
        view.showLoader();

        Map<String, String> map = new HashMap();

        map.put("user_id", session.getUser().getId());

        map.put("boarding_detail", onBoardingData);

        koolocoRepository.setOnBordingData(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                appPreferences.putBoolean("isOnBoard", true);
                openHomeActivity();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });


    }
}