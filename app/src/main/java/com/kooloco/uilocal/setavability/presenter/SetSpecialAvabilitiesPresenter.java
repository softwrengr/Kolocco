package com.kooloco.uilocal.setavability.presenter;/**
 * Created by hlink44 on 11/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.SetSpecialAvability;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.setavability.view.SetSpecialAvabilitiesView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class SetSpecialAvabilitiesPresenter extends BasePresenter<SetSpecialAvabilitiesView> {
    @Inject
    KoolocoRepository koolocoRepository;
    @Inject
    Session session;

    @Inject
    public SetSpecialAvabilitiesPresenter() {

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

    public void callWs(String date, String stratTime, String endTime, String isDayoff) {
        view.showLoader();
        Map<String, String> param = new HashMap<>();

        param.put("user_id",session.getUser().getId());
        param.put("start_time",stratTime);
        param.put("end_time",endTime);
        param.put("date",date);
        param.put("is_available",isDayoff);

        koolocoRepository.setSpecialAvavility(param).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void getAvability(String selectDate) {
        view.showLoader();
        Map<String, String> param = new HashMap<>();

        param.put("user_id",session.getUser().getId());
        param.put("date",selectDate);


        koolocoRepository.getSpecialAvavility(param).subscribe(new SubscribeWithView<Response<SetSpecialAvability>>(view) {
            @Override
            public void onSuccess(Response<SetSpecialAvability> response) {
                view.hideLoader();
                view.setData(response.getData());
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
