package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 4/10/17.
 */

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.RequestSportRule;
import com.kooloco.model.Response;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class ChooseSportActivitiesPresenter extends BasePresenter<ChooseSportActivitiesView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    Gson gson = new Gson();

    @Inject
    public ChooseSportActivitiesPresenter() {

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
        map.put("user_id", session.getUserId());

        koolocoRepository.sportType(map).subscribe(new SubscribeWithView<Response<List<SportActivity>>>(view) {
            @Override
            public void onSuccess(Response<List<SportActivity>> listResponse) {
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


    public void openChooseSportView() {
        navigator.openChooseSportActivity1View().replace(true);
    }

    public void callWs(List<SportActivity> sportActivities) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUserId());
        map.put("sport_activity", getSportRequest(sportActivities, "1"));

        koolocoRepository.setSportPriceRules(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> listResponse) {
                view.hideLoader();
                openChooseSportView();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    private String getSportRequest(List<SportActivity> sportActivities, String experinceId) {
        List<RequestSportRule> requestSportRules = new ArrayList<>();

        for (SportActivity sportActivity : sportActivities) {

            if (sportActivity.isSelect()) {
                if (sportActivity.isExpand()) {
                    for (SportSubActivity sportSubActivity : sportActivity.getSportSubActivities()) {

                        if (sportSubActivity.isSelect()) {
                            RequestSportRule requestSportRule = new RequestSportRule();
                            requestSportRule.setSportId(sportSubActivity.getId());
                            requestSportRule.setExperienceId(experinceId);
                            requestSportRule.setPricePerHour(sportSubActivity.getPrice());
                            requestSportRule.setNewParticipantPerc(sportSubActivity.getPricePerPerPerson());
                            requestSportRule.setNumberOfParticipant(sportSubActivity.getAddParticipants());
                            requestSportRule.setMinimumDuration(sportSubActivity.getMinimumDuration());
                            requestSportRule.setIsMultipleBooking(sportSubActivity.isGroup() ? "0" : "1");
                            requestSportRules.add(requestSportRule);
                        }
                    }
                } else {
                    RequestSportRule requestSportRule = new RequestSportRule();
                    requestSportRule.setSportId(sportActivity.getId());
                    requestSportRule.setExperienceId(experinceId);
                    requestSportRule.setPricePerHour(sportActivity.getPrice());
                    requestSportRule.setNewParticipantPerc(sportActivity.getPricePerPerPerson());
                    requestSportRule.setNumberOfParticipant(sportActivity.getAddParticipants());
                    requestSportRule.setMinimumDuration(sportActivity.getMinimumDuration());
                    requestSportRule.setIsMultipleBooking(sportActivity.isGroup() ? "0" : "1");
                    requestSportRules.add(requestSportRule);
                }
            }
        }
        return gson.toJson(requestSportRules);
    }
}
