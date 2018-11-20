package com.kooloco.uilocal.complateorder.presenter;/**
 * Created by hlink44 on 9/10/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Order;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.complateorder.view.CompleteOrderView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class CompleteOrderPresenter extends BasePresenter<CompleteOrderView> {

    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    public CompleteOrderPresenter() {

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

    public void getData(int page, boolean isShowLoader) {

        if (isShowLoader) {
            view.showLoader();
        }

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("page", "" + page);

        kooocoRepository.getComplateOrderLocal(map).subscribe(new SubscribeWithView<Response<List<Order>>>(view) {
            @Override
            public void onSuccess(Response<List<Order>> listResponse) {
                view.setData(listResponse.getData(), page, listResponse.getOrderCount());

                if (isShowLoader) {
                    view.hideLoader();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    }
                } else {
                    super.onError(e);
                }
                view.setData(new ArrayList<Order>(), page, "0");
                if (isShowLoader) {
                    view.hideLoader();
                }
            }
        });
    }

    public void openNotification() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.NotificationLocal).start();
    }

    public void openOrderDetails(Order order) {
        Bundle bundle = new Bundle();
        bundle.putString("orderStatus", new Gson().toJson(order));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.OrdeDetailsLocal).addBundle(bundle).start();
    }
}
