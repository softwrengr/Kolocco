package com.kooloco.uilocal.home.presenter;/**
 * Created by hlink44 on 5/10/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.model.ReviewData;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.uilocal.Notification.view.ReceivedObjectionLocalView;
import com.kooloco.uilocal.home.view.OrderDetailsLocalView;
import com.kooloco.uilocal.home.view.RateAndReviewView;
import com.kooloco.uilocal.modify.view.ModifyAddressView;
import com.kooloco.uilocal.modify.view.ModifyDurationView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

@PerActivity
public class OrderDetailsLocalPresenter extends BasePresenter<OrderDetailsLocalView> {
    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public OrderDetailsLocalPresenter() {

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

    public void openChat(ReceiverData receiverData) {

        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();

    }

    public void openAcceptOrderDetails(Order order) {
        navigator.openAcceptOrderDetailsLocalView().hasData(acceptOrderDetailsView -> acceptOrderDetailsView.setOrder(order)).replace(true);
    }

    public void openRating(Order order) {
        navigator.openLocalRateAndReviewView().hasData(new Passable<RateAndReviewView>() {
            @Override
            public void passData(RateAndReviewView rateAndReviewView) {
                rateAndReviewView.setData(order);
            }
        }).replace(true);
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

    public void openDurationEdit(Order order) {
        navigator.openModifyDuration().hasData(new Passable<ModifyDurationView>() {
            @Override
            public void passData(ModifyDurationView modifyDurationView) {
                modifyDurationView.setIsLocation(false);
                modifyDurationView.setOrder(order);
            }
        }).replace(true);
    }

    public void openMeetingSpotEdit(Order order) {
        navigator.openModifyLocation().hasData(new Passable<ModifyAddressView>() {
            @Override
            public void passData(ModifyAddressView modifyAddressView) {
                modifyAddressView.setIsLocation(false);
                modifyAddressView.setOrder(order);
            }
        }).replace(true);
    }

    public void getRating(int pageRate, String userId) {

        Map<String, String> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("page", "" + pageRate);

        koolocoRepository.getRateVisitor(param).subscribe(new SubscribeWithView<Response<ReviewData>>(view) {
            @Override
            public void onSuccess(Response<ReviewData> userResponse) {
                int count = 0;
                if (userResponse.getData().getReviewCount() != null) {
                    try {
                        count = Integer.parseInt(userResponse.getData().getReviewCount());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        count = 0;
                    }
                }

                view.setDataRating(userResponse.getData().getList(), pageRate, count);
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

                view.setDataRating(new ArrayList<>(), pageRate, 0);

            }
        });

    }

    public void openExpDetails(ExpereinceNew expereinceNew) {
        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCALSIDE).addBundle(bundle).start();

        //       navigator.openExperienceDetailsView().hasData(experienceDetailsView -> experienceDetailsView.setIsLocalApp(true)).replace(true);
    }

    public void openCheckEquipments(String orderId, ReceiverData receiverData, String orderStaus, String chatStatus) {


        navigator.openLocalCheckSportEaquipmentsView().hasData(checkSportEaquipmentsView -> {
            checkSportEaquipmentsView.setOrderId(orderId);
            checkSportEaquipmentsView.setRecevierData(receiverData);
            checkSportEaquipmentsView.setOrderStatus(orderStaus);
            checkSportEaquipmentsView.setChatStatus(chatStatus);

        }).replace(true);
    }

    public void openReceiveObjectionDetails(String notificationId) {
        navigator.openReceivedObjectionLocalView().hasData(new Passable<ReceivedObjectionLocalView>() {
            @Override
            public void passData(ReceivedObjectionLocalView receivedObjectionLocalView) {
                receivedObjectionLocalView.setNotificationId(notificationId);
            }
        }).replace(true);
    }
}
