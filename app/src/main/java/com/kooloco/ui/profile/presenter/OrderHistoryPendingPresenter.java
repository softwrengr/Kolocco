package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 25/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Order;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.profile.view.OrderDetailsView;
import com.kooloco.ui.profile.view.OrderHistoryPendingView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class OrderHistoryPendingPresenter extends BasePresenter<OrderHistoryPendingView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    public OrderHistoryPendingPresenter() {

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

    public void getData(int page, boolean isLoadProgress) {

        if (isLoadProgress) {
            view.showLoader();
        }
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("page", "" + page);

        kooocoRepository.getPendingOrderVisitor(map).subscribe(new SubscribeWithView<Response<List<Order>>>(view) {
            @Override
            public void onSuccess(Response<List<Order>> listResponse) {
                view.setData(listResponse.getData(), page);

                if (isLoadProgress) {
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
                view.setData(new ArrayList<Order>(), page);
                if (isLoadProgress) {
                    view.hideLoader();
                }
            }
        });
    }

    public void openOrderDetails(Order order) {
        navigator.openOrderDetailsView().hasData(new Passable<OrderDetailsView>() {
            @Override
            public void passData(OrderDetailsView orderDetailsView) {
                orderDetailsView.setOrder(order);
            }
        }).replace(true,"VisitorOrder");
    }

    public void opnChat(ReceiverData receiverData) {
        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();
    }

}
