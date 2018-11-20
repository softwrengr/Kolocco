package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 3/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Activities;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.uilocal.addservice.view.CreateYourExperienceView;
import com.kooloco.uilocal.addservice.view.SportPriceRulesTabView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class CreateYourExperiencePresenter extends BasePresenter<CreateYourExperienceView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public CreateYourExperiencePresenter() {

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
        map.put("user_id", session.getUser().getId());

        koolocoRepository.experience(map).subscribe(new SubscribeWithView<Response<List<Activities>>>(view) {
            @Override
            public void onSuccess(Response<List<Activities>> listResponse) {
                view.setData(listResponse.getData());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void openChooseSportEquipments() {
        navigator.openChooseSportEquipmentsView().replace(true);
    }

    public void openSportPriceRules(final boolean isEdit, boolean isIncompleteLocal) {
        navigator.openSportPriceRulesView().hasData(new Passable<SportPriceRulesTabView>() {
            @Override
            public void passData(SportPriceRulesTabView sportPriceRulesTabView) {
                sportPriceRulesTabView.setIsEdit(isEdit);
                sportPriceRulesTabView.setIsLocalInComplete(isIncompleteLocal);
            }
        }).replace(true);
    }

    public void openKoolocoHelp() {
        navigator.openHelpAndFaqView().replace(true);
    }

    public void callws(List<Activities> activites, final boolean isEdit, boolean isIncompleteLocal) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();

        String expId = "";

        for (Activities activity : activites) {
            if (activity.getIsSelected().equalsIgnoreCase("1")) {
                if (expId.isEmpty()) {
                    expId = expId + activity.getId();
                } else {
                    expId = expId + "," + activity.getId();
                }
            }
        }

        map.put("user_id", session.getUser().getId());
        map.put("activity_id", expId);

        koolocoRepository.setExperienceStepSix(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> listResponse) {
                view.hideLoader();
                openSportPriceRules(isEdit, isIncompleteLocal);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
