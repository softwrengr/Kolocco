package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 17/4/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.CancellationPolicy;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceCancellationPoliciesView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceCancellationPoliciesPresenter extends BasePresenter<ExperienceCancellationPoliciesView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ExperienceCancellationPoliciesPresenter() {
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

    public void getCancelltion(String expId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);

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



    public void callWs(String expId,List<CancellationPolicy> cancellationPolicies, final boolean isEdit) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("cancellation_policy", getKey(cancellationPolicies));

        koolocoRepository.setExpCancelationPolicy(map).subscribe(new SubscribeWithView<Response<ExperienceResponse>>(view) {
            @Override
            public void onSuccess(Response<ExperienceResponse> experienceResponseResponse) {
                view.hideLoader();
                if (isEdit) {
                    navigator.goBack();
                } else {
                    openConfirmationScreen();
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

    public void openConfirmationScreen() {
        navigator.openSuccessLocalView().replace(true);
    }
}