package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 4/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.CancellationPolicy;
import com.kooloco.model.Language;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.addservice.view.CancellationPoliciesView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class CancellationPoliciesPresenter extends BasePresenter<CancellationPoliciesView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public CancellationPoliciesPresenter() {

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

    public void openSetLocation() {
        navigator.openSetLocationView().replace(true);
    }

    public void getCancelltion() {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.cancellation(map).subscribe(new SubscribeWithView<Response<List<CancellationPolicy>>>(view) {
            @Override
            public void onSuccess(Response<List<CancellationPolicy>> listResponse) {
                view.hideLoader();
                view.setData(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callWs(List<CancellationPolicy> cancellationPolicies, final boolean isEdit) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("cancellation_policy_id", getKey(cancellationPolicies));

        koolocoRepository.setcancellation(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                if (isEdit) {
                    navigator.goBack();
                } else {
                    openSetLocation();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    private String getKey(List<CancellationPolicy> cancellationPolicies) {
        String lngId = "";

        for (CancellationPolicy cancellationPolicy : cancellationPolicies) {
            if (cancellationPolicy.getIsSelected().equalsIgnoreCase("1")) {
                if (lngId.isEmpty()) {
                    lngId = lngId + cancellationPolicy.getId();
                } else {
                    lngId = lngId + "," + cancellationPolicy.getId();
                }
            }
        }
        return lngId;
    }
}
