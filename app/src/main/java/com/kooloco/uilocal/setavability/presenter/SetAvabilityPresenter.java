package com.kooloco.uilocal.setavability.presenter;/**
 * Created by hlink44 on 11/10/17.
 */

import com.google.gson.Gson;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.SchduleDashboard;
import com.kooloco.model.SetAvailability;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.setavability.view.SetAvabilityView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

@PerActivity
public class SetAvabilityPresenter extends BasePresenter<SetAvabilityView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public SetAvabilityPresenter() {

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

    public void openSetSpecialAvaibility() {
        navigator.openSetSpecialAvabilitiesView().replace(true);
    }

    public void openDisableSport() {
        navigator.openDisableSportView().replace(true);
    }

    public void callWs(List<SetAvailability> setAvailability, boolean isEdit) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("slot", new Gson().toJson(setAvailability));

        koolocoRepository.setSchedule(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                view.showMessage(userResponse.getMessage());
                EventBus.getDefault().post(EventBusAction.UPDATELISTLOCAL);
                if (isEdit) {
                    navigator.goBack();
                } else {
                    navigator.goBack();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void getData() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getSchdule(map).subscribe(new SubscribeWithView<Response<List<SchduleDashboard>>>(view) {
            @Override
            public void onSuccess(Response<List<SchduleDashboard>> listResponse) {
                view.hideLoader();
                view.setData(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setData(new ArrayList<SchduleDashboard>());
            }
        });
    }
}
