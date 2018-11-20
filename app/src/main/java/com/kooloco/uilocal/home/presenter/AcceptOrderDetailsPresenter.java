package com.kooloco.uilocal.home.presenter;/**
 * Created by hlink44 on 5/10/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.home.view.AcceptOrderDetailsView;
import com.kooloco.uilocal.modify.view.ModifyAddressView;
import com.kooloco.uilocal.modify.view.ModifyDurationView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class AcceptOrderDetailsPresenter extends BasePresenter<AcceptOrderDetailsView> {
    @Inject
    Session session;
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public AcceptOrderDetailsPresenter() {

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

    public void getData(String orderId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("order_id", orderId);

        koolocoRepository.orderDetailsLocal(map).subscribe(new SubscribeWithView<Response<OrderDetails>>(view) {
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

    public void openLocationDurationEdit(Order order) {
        Bundle bundle = new Bundle();
        bundle.putString("orderStatus", new Gson().toJson(order));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.MODIFYDURATIONLOCATION).addBundle(bundle).start();
    }

    public void openDurationEdit(Order order) {
        Bundle bundle = new Bundle();
        bundle.putString("orderStatus", new Gson().toJson(order));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.MODIFYDURATION).addBundle(bundle).start();
    }

    public void openMeetingSpotEdit(Order order) {
        Bundle bundle = new Bundle();
        bundle.putString("orderStatus", new Gson().toJson(order));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.MODIFYLOCATION).addBundle(bundle).start();
    }

}
