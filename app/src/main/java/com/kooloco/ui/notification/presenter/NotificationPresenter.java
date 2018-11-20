package com.kooloco.ui.notification.presenter;/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.Notification;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.notification.view.NotificationView;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.notification.view.ReciveObjectionDetailsView;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class NotificationPresenter extends BasePresenter<NotificationView> {
    @Inject
    KoolocoRepository koolocoRepostry;

    @Inject
    Session session;


    @Inject
    public NotificationPresenter() {

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

    public void getData(int page, boolean isLoading) {

        if (isLoading) {
            view.showLoader();
        }
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("page", "" + page);
        map.put("role", "V");

        koolocoRepostry.getNotifications(map).subscribe(new SubscribeWithView<Response<List<Notification>>>(view) {
            @Override
            public void onSuccess(Response<List<Notification>> listResponse) {
                if (isLoading) {
                    view.hideLoader();
                }
                view.setData(listResponse.getData(), page);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (page == 1) {
                        //super.onError(e);
                    }
                } else {
                    super.onError(e);
                }

                if (isLoading) {
                    view.hideLoader();
                }
                view.setData(new ArrayList<>(), page);
            }
        });
    }

    public void openReciveObjection(String id) {
/*
        navigator.openReciveObjectionDetailsView().hasData(new Passable<ReciveObjectionDetailsView>() {
            @Override
            public void passData(ReciveObjectionDetailsView reciveObjectionDetailsView) {
                reciveObjectionDetailsView.setId(id);
            }
        }).replace(true);
        // navigator.openCheckSportEaquipmentsView().replace(true);
*/
        Bundle bundle = new Bundle();
        bundle.putString("notificationId", id);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.RECEVIOBJECTIONVISITOR).addBundle(bundle).start();
    }

    public void onBack() {
        navigator.goBack();
    }

    public void openChat(Notification notification) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("order_id", notification.getOrderId());

        koolocoRepostry.orderDetailsVisitor(map).subscribe(new SubscribeWithView<Response<OrderDetails>>(view) {
            @Override
            public void onSuccess(Response<OrderDetails> orderDetailsResponse) {
                view.hideLoader();

                OrderDetails orderDetailsData = orderDetailsResponse.getData();

                ReceiverData receiverData = new ReceiverData();
                receiverData.setUser_id(orderDetailsData.getLocalId());
                receiverData.setName(orderDetailsData.getFirstname() + " " + orderDetailsData.getLastname());
                receiverData.setImageUrl(orderDetailsData.getProfileImage());
                receiverData.setDeviceType(orderDetailsData.getDeviceType());
                receiverData.setDeviceToken(orderDetailsData.getDeviceToken());
                receiverData.setOrderId(notification.getOrderId());

                Bundle bundle = new Bundle();
                bundle.putString("receiverData", new Gson().toJson(receiverData));

                navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void openOrderHistory() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.OrderHistory).start();
    }

    public void openReciptScreen(Notification notification) {
        Bundle bundleData = new Bundle();
        bundleData.putString("orderId", notification.getOrderId());
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.ReceipScreen).addBundle(bundleData).start();
    }

    public void openMapScreen(Notification notification) {
        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setFirstname(notification.getFirstname());
        dashboardDetails.setLastname(notification.getLastname());

        dashboardDetails.setImageUrl(notification.getProfileImage());

        dashboardDetails.setLocalLocation(notification.getMeetingAddress());

        dashboardDetails.setLatitude(notification.getMettingLatitude());
        dashboardDetails.setLongitude(notification.getMeetingLongitude());

        //Old code for back button
    /*    navigator.openMettingLocalVisitorAndLocalMap().hasData(new Passable<MettingLocationView>() {
            @Override
            public void passData(MettingLocationView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);

*/
        Bundle bundle = new Bundle();
        bundle.putString("localDetails", new Gson().toJson(dashboardDetails));

        navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.METTINGLOCATIONDISP).start();

    }

    public void openRating(String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderId);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.RATING).addBundle(bundle).start();
    }

    public void openBecomeLocal() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.BecomeLocal).start();
    }
}
