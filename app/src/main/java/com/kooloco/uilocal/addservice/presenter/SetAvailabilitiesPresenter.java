package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 5/10/17.
 */

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.SetAvailability;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.addservice.view.SetAvailabilitiesView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class SetAvailabilitiesPresenter extends BasePresenter<SetAvailabilitiesView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public SetAvailabilitiesPresenter() {

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

    public void openCancellation() {
        navigator.openCancellationPoliciesView().replace(true);
    }

    public void callWs(List<SetAvailability> setAvailability) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("slot", new Gson().toJson(setAvailability));

        koolocoRepository.setSchedule(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                openCancellation();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
