package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 25/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.notification.view.ReciveObjectionDetailsView;
import com.kooloco.ui.profile.view.OrderDetailsView;
import com.kooloco.ui.visitor.dashboard.view.ReceiptView;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class OrderDetailsPresenter extends BasePresenter<OrderDetailsView> {
    @Inject
    Session session;
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public OrderDetailsPresenter() {

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

    public void openOrderCancel(String orderId) {
        navigator.openCancellationView().hasData(canecellationView -> {
            canecellationView.setOrderId(orderId);
        }).replace(true, "VisitorOrder");
    }

    public void opnChat(ReceiverData receiverData) {
        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();
    }

    public void getData(String orderId) {

        // view.setData(Temp.getorderDetailsNew());

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

    public void openReceipt(String orderId) {
        navigator.openReceiptView().hasData(new Passable<ReceiptView>() {
            @Override
            public void passData(ReceiptView receiptView) {
                receiptView.setOrderId(orderId);
            }
        }).replace(true);
    }

    public void openMapScreen(OrderDetails orderDetails) {
        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setFirstname(orderDetails.getFirstname());
        dashboardDetails.setLastname(orderDetails.getLastname());

        dashboardDetails.setImageUrl(orderDetails.getProfileImage());

        dashboardDetails.setLocalLocation(orderDetails.getMeetingAddress());

        dashboardDetails.setLatitude(orderDetails.getMeetingLatitude());
        dashboardDetails.setLongitude(orderDetails.getMeetingLongitude());

        navigator.openMettingLocalVisitorAndLocalMap().hasData(new Passable<MettingLocationView>() {
            @Override
            public void passData(MettingLocationView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);
    }

    public void openExpDetails(ExpereinceNew expereinceNew) {

        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCAL).addBundle(bundle).start();
    }

    public void openRating(String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderId);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.RATING).addBundle(bundle).byFinishingCurrent().start();
    }

    public void openReciveObjectionDetails(String notificationId) {
        navigator.openReciveObjectionDetailsView().hasData(new Passable<ReciveObjectionDetailsView>() {
            @Override
            public void passData(ReciveObjectionDetailsView reciveObjectionDetailsView) {
                reciveObjectionDetailsView.setId(notificationId);
            }
        }).replace(true);
    }
}
