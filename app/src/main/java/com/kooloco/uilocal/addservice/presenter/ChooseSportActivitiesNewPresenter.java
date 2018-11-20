package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 4/10/17.
 */

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.RequestSportRule;
import com.kooloco.model.Response;
import com.kooloco.model.SelectSport;
import com.kooloco.model.Sport;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.manager.Passable;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesNewView;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesView;
import com.kooloco.uilocal.addservice.view.CreateYourExperienceView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class ChooseSportActivitiesNewPresenter extends BasePresenter<ChooseSportActivitiesNewView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    Gson gson = new Gson();

    @Inject
    public ChooseSportActivitiesNewPresenter() {

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


    public void openChooseEquipmentsView() {
        navigator.openChooseSportEquipmentsView().replace(true);
    }

    public void openSetSchdule() {
        navigator.openSetAvilabilitesView().replace(true);
    }


    public void openMyExperience(final boolean isEdit, boolean isLocalIncomplete) {
        navigator.openCreatYourExpView().hasData(new Passable<CreateYourExperienceView>() {
            @Override
            public void passData(CreateYourExperienceView createYourExperienceView) {
                createYourExperienceView.setIsEdit(isEdit);
                createYourExperienceView.setIsLocalInComplete(isLocalIncomplete);

            }
        }).replace(true);
    }


    public void callWs(List<SportActivity> sportActivities, final boolean isEdit, boolean isLocalIncomplete) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("sports", getSportRequest(sportActivities));

        koolocoRepository.setSportLocal(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> listResponse) {
                view.hideLoader();
                openMyExperience(isEdit,isLocalIncomplete);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    private String getSportRequest(List<SportActivity> sportActivities) {
        List<SelectSport> selectSports = new ArrayList<>();

        for (SportActivity sportActivity : sportActivities) {

            if (sportActivity.isSelect()) {
                if (sportActivity.isExpand()) {

                    for (SportSubActivity sportSubActivity : sportActivity.getSportSubActivities()) {

                        if (sportSubActivity.isSelect()) {
                            SelectSport sport = new SelectSport();
                            sport.setSportId(sportSubActivity.getId());
                            sport.setUserId(session.getUser().getId());
                            selectSports.add(sport);
                        }
                    }
                } else {
                    SelectSport sport = new SelectSport();
                    sport.setSportId(sportActivity.getId());
                    sport.setUserId(session.getUser().getId());
                    selectSports.add(sport);
                }
            }
        }
        return gson.toJson(selectSports);
    }
}
