package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 5/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.addservice.view.SetLocationView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class SetLocationPresenter extends BasePresenter<SetLocationView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public SetLocationPresenter() {

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

    public void callWs(String activityAddress, String latitude, String longitutde, String activityArea, String activityCity, String activityState, String activityCountry, final boolean isEdit) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("location_address", activityAddress);
        map.put("location_latitude", latitude);
        map.put("location_longitude", longitutde);

        map.put("location_city", activityCity);
        map.put("location_state", activityState);
        map.put("location_country", activityCountry);

        koolocoRepository.setLocation(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                if (isEdit) {
                    navigator.goBack();
                } else {
                    openExperience();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }


    public void openSuccessLocal() {
        navigator.openSuccessLocalView().replace(true);
    }

    public void openExperience() {
        navigator.openExperienceView().hasData(experienceView -> {
            experienceView.setIsLocalFlow(true);
        }).replace(true);
    }
}
