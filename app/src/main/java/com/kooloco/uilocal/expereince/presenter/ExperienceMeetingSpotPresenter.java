package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 17/4/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceMeetingSpotView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceMeetingSpotPresenter extends BasePresenter<ExperienceMeetingSpotView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ExperienceMeetingSpotPresenter() {
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

    public void openSetCancellation(String expId) {
        navigator.openExperienceCancellationPolicesView().hasData(experienceCancellationPoliciesView -> experienceCancellationPoliciesView.setExpId(expId)).replace(true);
    }

    public void callWs(String expId, String activityAddress, String latitude, String longitutde, String activityArea, String activityCity, String activityState, String activityCountry, final boolean isEdit) {
        //{"experience_id":"1","activity_address":"Address","activity_latitude":"23.12345","activity_longitude":"72.12345","city":"Ahmedabad","country":"india"}

        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("experience_id", expId);
        map.put("activity_address", activityAddress);
        map.put("activity_latitude", latitude);
        map.put("activity_longitude", longitutde);

        map.put("city", activityCity);
        map.put("state", activityState);
        map.put("country", activityCountry);

        koolocoRepository.setExpMeetingSpot(map).subscribe(new SubscribeWithView<Response<ExperienceResponse>>(view) {
            @Override
            public void onSuccess(Response<ExperienceResponse> experienceResponseResponse) {
                view.hideLoader();
                if (isEdit) {
                    navigator.goBack();
                } else {
                    openSetCancellation(expId);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}