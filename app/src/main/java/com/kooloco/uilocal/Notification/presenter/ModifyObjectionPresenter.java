package com.kooloco.uilocal.Notification.presenter;/**
 * Created by hlink44 on 9/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.Notification.view.ModifyObjectionView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ModifyObjectionPresenter extends BasePresenter<ModifyObjectionView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ModifyObjectionPresenter() {

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

    public void getData(String notificationId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("notification_id", notificationId);

        koolocoRepository.objectionDetails(map).subscribe(new SubscribeWithView<Response<ObjectionDetails>>(view) {
            @Override
            public void onSuccess(Response<ObjectionDetails> objectionDetailsResponse) {
                view.hideLoader();
                view.setData(objectionDetailsResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callSendObjection(String orderId, String type, String objectionValue, String reason, String notificationId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("objection_value", objectionValue);
        map.put("objection_type", type);
        map.put("reason", reason);
        map.put("order_id", orderId);
        map.put("notification_id", notificationId);

        koolocoRepository.modifyObjection(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
                view.finishActivity();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
