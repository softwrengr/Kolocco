package com.kooloco.ui.chat.presenter;

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Chat;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.LocalImage;
import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.chat.view.ChatView;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by hlink44 on 14/3/17.
 */

@PerActivity
public class ChatPresenter extends BasePresenter<ChatView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    public ChatPresenter() {
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

    public void openDashboardDetails(String id) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", id);

        kooocoRepository.getLocalProfile(map).subscribe(new SubscribeWithView<Response<DashboardDetails>>(view) {
            @Override
            public void onSuccess(Response<DashboardDetails> organizationDetailsResponse) {
                view.hideLoader();
                List<LocalImage> localImages = new ArrayList<>();
                for (LocalImage localImage : organizationDetailsResponse.getData().getLocalImages()) {
                    localImage.setLocalImage(localImage.getLocalImageUrl());
                    localImages.add(localImage);
                }
                organizationDetailsResponse.getData().setLocalImages(localImages);
                Bundle bundle = new Bundle();
                bundle.putString("localDetails", new Gson().toJson(organizationDetailsResponse.getData()));
                bundle.putBoolean("isPreviewScreen", false);

                navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.Dashboard).start();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openOrderDetails(Chat recentChat) {
        if (!recentChat.getOrderId().isEmpty()) {
            if (recentChat.getLocalId().equalsIgnoreCase(session.getUser().getId())) {
                openLocalOrderDetails(recentChat.getOrderId(), session.getUser().getId());
            } else {
                openVisitorOrderDetails(recentChat.getOrderId(), session.getUser().getId());
            }
        }
    }

    private void openLocalOrderDetails(String orderId, String localId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", localId);
        map.put("order_id", orderId);

        kooocoRepository.orderDataLocal(map).subscribe(new SubscribeWithView<Response<Order>>(view) {
            @Override
            public void onSuccess(Response<Order> orderDetailsResponse) {
                view.hideLoader();

                Bundle bundle = new Bundle();
                bundle.putString("orderStatus", new Gson().toJson(orderDetailsResponse.getData()));
                navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.OrdeDetailsLocal).addBundle(bundle).start();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    private void openVisitorOrderDetails(String orderId, String visitorId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", visitorId);
        map.put("order_id", orderId);

        kooocoRepository.orderDataVisitor(map).subscribe(new SubscribeWithView<Response<Order>>(view) {
            @Override
            public void onSuccess(Response<Order> orderDetailsResponse) {
                view.hideLoader();

                Bundle bundle = new Bundle();
                bundle.putString("orderStatus", new Gson().toJson(orderDetailsResponse.getData()));
                navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.OrdeDetailsVisitor).addBundle(bundle).start();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

}
