package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.CancellationData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.profile.view.CanecellationView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class CanecellationPresenter extends BasePresenter<CanecellationView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public CanecellationPresenter() {

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

    public void getCancellationData(String orderId) {
        view.showLoader();
        Map<String, String> param = new HashMap<>();
        param.put("booking_id", orderId);
        param.put("user_id", session.getUser().getId());

        koolocoRepository.getCancellationAmount(param).subscribe(new SubscribeWithView<Response<CancellationData>>(view) {
            @Override
            public void onSuccess(Response<CancellationData> response) {
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

    public void callWsCancel(String orderId, String reason, CallBack callBack) {
        view.showLoader();
        Map<String, String> param = new HashMap<>();
        param.put("order_id", orderId);
        param.put("user_id", session.getUser().getId());
        param.put("reason", reason);

        koolocoRepository.visitorCancel(param).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                callBack.onSuccess(true, response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                callBack.onSuccess(false, e.getMessage());
            }
        });

    }

    public void goBack() {
        navigator.openChooseLangaugeView().clearHistory("VisitorOrder");
    }

    public interface CallBack {
        void onSuccess(boolean isSucess, String message);
    }
}
