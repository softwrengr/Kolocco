package com.kooloco.uilocal.home.presenter;/**
 * Created by hlink44 on 16/10/17.
 */

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Order;
import com.kooloco.model.RateSelectOption;
import com.kooloco.model.RatingOption;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.home.view.RateAndReviewView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class RateAndReviewPresenter extends BasePresenter<RateAndReviewView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    Gson gson;

    @Inject
    public RateAndReviewPresenter() {

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
        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());
        param.put("role", "L");

        koolocoRepository.getRatOption(param).subscribe(new SubscribeWithView<Response<List<RatingOption>>>(view) {
            @Override
            public void onSuccess(Response<List<RatingOption>> listResponse) {
                view.setDataValue(listResponse.getData());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.goBackScreen();
            }
        });
    }

    public void sendData(String orderId,  String review, List<RateSelectOption> rateSelectOptions) {

        //{ "order_id": "4", "visitor_id": "1", "review": "This is test", "rate_list": [ { "option_id": "39", "rate": "1" }, { "option_id": "38", "rate": "5" }, { "option_id": "37", "rate": "1" }, { "option_id": "36", "rate": "1" } ] }

        view.showLoader();
        Map<String, String> param = new HashMap<>();
        param.put("order_id", orderId);
        param.put("local_id", session.getUser().getId());
        param.put("review", review);
        param.put("rate_list", gson.toJson(rateSelectOptions));

        koolocoRepository.setRateLocal(param).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.goBackScreen();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
