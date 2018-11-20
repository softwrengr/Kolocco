package com.kooloco.ui.onboarding.presenter;
/**
 * Created by hlink on 18/5/18.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.OnBoardingAnswer;
import com.kooloco.model.Response;
import com.kooloco.model.Service;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.onboarding.view.OnBoardingView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class OnBoardingPresenter extends BasePresenter<OnBoardingView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Gson gson;

    @Inject
    AppPreferences appPreferences;

    @Inject
    public OnBoardingPresenter() {
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

        koolocoRepository.sportServiceType(map).subscribe(new SubscribeWithView<Response<List<Service>>>(view) {
            @Override
            public void onSuccess(Response<List<Service>> listResponse) {

                List<Service> services = mapStaticData(listResponse.getData());
                view.data(services);
                view.setData(services);
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openActivityDashBoard(String onBoardingData) {
        Bundle bundle = new Bundle();
        bundle.putString("onBordData", onBoardingData);
        navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.ONBORDINGACTIVITY).byFinishingCurrent().start();
    }


    public void openHomeActivity() {
        navigator.startHomeActivity().byFinishingAll().start();
    }

    public void callWs(Map<String, String> selectAnswer) {
        List<OnBoardingAnswer> onBoardingAnswers = new ArrayList<>();
        for (Map.Entry<String, String> param : selectAnswer.entrySet()) {
            OnBoardingAnswer onBoardingAnswer = new OnBoardingAnswer();
            onBoardingAnswer.setIsYes(param.getValue());
            onBoardingAnswer.setUserId(session.getUser().getId());
            onBoardingAnswer.setSportId(param.getKey());
            onBoardingAnswers.add(onBoardingAnswer);
        }


        openActivityDashBoard(gson.toJson(onBoardingAnswers));


    }

    public void introSkip(Map<String, String> selectAnswer){

        view.showLoader();

        List<OnBoardingAnswer> onBoardingAnswers = new ArrayList<>();
        for (Map.Entry<String, String> param : selectAnswer.entrySet()) {
            OnBoardingAnswer onBoardingAnswer = new OnBoardingAnswer();
            onBoardingAnswer.setIsYes(param.getValue());
            onBoardingAnswer.setUserId(session.getUser().getId());
            onBoardingAnswer.setSportId(param.getKey());
            onBoardingAnswers.add(onBoardingAnswer);
        }


        Map<String, String> map = new HashMap();

        map.put("user_id", session.getUser().getId());

        map.put("boarding_detail", gson.toJson(onBoardingAnswers));

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


    public List<Service> mapStaticData(List<Service> services) {
        List<Service> servicesTemp = new ArrayList<>();

        for (Service service : services) {
            String onBoard = service.getOnBoardingImage();

            switch (service.getName()) {
                case "Surfing":
                    onBoard = "android.resource://com.kooloco/drawable/on_bord_surf";
                    break;
                case "Skiing":
                    onBoard = "android.resource://com.kooloco/drawable/on_bord_skiing";
                    break;
                case "Snowboarding":
                    onBoard = "android.resource://com.kooloco/drawable/on_bord_snow";
                    break;
                case "Biking":
                    onBoard = "android.resource://com.kooloco/drawable/on_bord_biking";
                    break;
                case "Skateboarding":
                    onBoard = "android.resource://com.kooloco/drawable/on_bord_skate";
                    break;
                case "Mountaineering":
                    onBoard = "android.resource://com.kooloco/drawable/on_bord_mount";
                    break;
                case "Hiking/trekking":
                    onBoard = "android.resource://com.kooloco/drawable/on_bord_hiking";
                    break;
                case "Climbing":
                    onBoard = "android.resource://com.kooloco/drawable/on_bord_climbing";
                    break;
                default:
                    onBoard = service.getOnBoardingImage();
                    break;
            }
            service.setOnBoardingImage(onBoard);

            servicesTemp.add(service);
        }

        return servicesTemp;
    }

}