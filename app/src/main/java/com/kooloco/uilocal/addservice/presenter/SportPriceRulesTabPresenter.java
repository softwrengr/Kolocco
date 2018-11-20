package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 27/11/17.
 */

import com.google.gson.Gson;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.RequestSportRule;
import com.kooloco.model.Response;
import com.kooloco.model.SetSportPrice;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportPriceRules;
import com.kooloco.model.SportPriceRulesSport;
import com.kooloco.model.SportSubActivity;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.addservice.view.SportPriceRulesTabView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

@PerActivity
public class SportPriceRulesTabPresenter extends BasePresenter<SportPriceRulesTabView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    Gson gson = new Gson();


    @Inject
    public SportPriceRulesTabPresenter() {

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

    public void openChooseEquipment() {
        navigator.openChooseSportEquipmentsView().replace(true);
    }

    public void getData() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUserId());
        koolocoRepository.getSelectSport(map).subscribe(new SubscribeWithView<Response<List<SportPriceRules>>>(view) {
            @Override
            public void onSuccess(Response<List<SportPriceRules>> listResponse) {
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


    public void callWs(List<SportPriceRules> sportPriceRules, final boolean isEdit, final boolean isIncomplete) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUserId());
        map.put("sport_pricing", getSportRequest(sportPriceRules));

        koolocoRepository.setSportPriceRules(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> listResponse) {
                view.hideLoader();
                if (isIncomplete) {
                    EventBus.getDefault().post(EventBusAction.UPDATELISTLOCAL);
                    navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.LOCALINCOMPLETE).byFinishingCurrent().start();
                } else if (isEdit) {
                    navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ViewProfileLocal).byFinishingCurrent().start();
                } else {
                    openChooseEquipment();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    private String getSportRequest(List<SportPriceRules> sportPriceRules) {

        List<SetSportPrice> setSportPrices = new ArrayList<>();

        for (SportPriceRules sportPriceRule : sportPriceRules) {


            SetSportPrice setSportPrice = new SetSportPrice();

            String sportId = "";

            for (SportPriceRulesSport sportPriceRulesSport : sportPriceRule.getSports()) {

                if (sportPriceRulesSport.getIsPrice().equalsIgnoreCase("1")) {
                    if (sportId.isEmpty()) {
                        sportId = sportId + sportPriceRulesSport.getId();
                    } else {
                        sportId = sportId + "," + sportPriceRulesSport.getId();
                    }
                }
            }

            setSportPrice.setSportId(sportId);
            setSportPrice.setExperienceId(sportPriceRule.getId());
            setSportPrice.setPricePerHour(sportPriceRule.getPricePerHour());
            setSportPrice.setMinimumDuration(sportPriceRule.getMinDuration());
            setSportPrice.setNewParticipantPerc(sportPriceRule.getAddPersonPer());
            setSportPrice.setNumberOfParticipant(sportPriceRule.getAddPerson());
            setSportPrice.setIsMultipleBooking(sportPriceRule.getIsGroupBooking());
            setSportPrices.add(setSportPrice);

        }
        return gson.toJson(setSportPrices);
    }
}
