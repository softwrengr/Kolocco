package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 22/9/17.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.dashboard.view.ReceiptView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

@PerActivity
public class ReceiptPresenter extends BasePresenter<ReceiptView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ReceiptPresenter() {

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

    public void onBack() {
        navigator.goBack();
    }

    public void getData(String orderId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("order_id", orderId);

        koolocoRepository.orderDetailsVisitor(map).subscribe(new SubscribeWithView<Response<OrderDetails>>(view) {
            @Override
            public void onSuccess(Response<OrderDetails> orderDetailsResponse) {
                view.hideLoader();
                view.setData(orderDetailsResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callSendObjection(String orderId, String type, String objectionValue, String writeNow) {

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("objection_value", objectionValue);
        map.put("objection_type", type);
        map.put("reason", writeNow);
        map.put("order_id", orderId);

        koolocoRepository.sendObjection(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
                navigator.goBack();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callSendPayment(String orderId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("order_id", orderId);

        koolocoRepository.payNow(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
                view.clearAllNotification("Receipt");
                view.deleteRecentChat(orderId);
                EventBus.getDefault().post(EventBusAction.NOTIFICATIONVISITOR);
                openRating(orderId);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    private void openRating(String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderId);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.RATING).addBundle(bundle).byFinishingCurrent().start();
    }

    public void openExpDetails(ExpereinceNew expereinceNew) {

        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCAL).addBundle(bundle).start();
    }
}
