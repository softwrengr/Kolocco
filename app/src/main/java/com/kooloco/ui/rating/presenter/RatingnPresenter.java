package com.kooloco.ui.rating.presenter;/**
 * Created by hlink44 on 22/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Common;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.ExpDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.RatingOption;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.rating.view.RatingnView;
import com.kooloco.util.SubscribeWithView;
import com.kooloco.util.TimeConvertUtils;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class RatingnPresenter extends BasePresenter<RatingnView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public RatingnPresenter() {

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

    public void getData() {
        view.showLoader();
        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());
        param.put("role", "V");

        koolocoRepository.getRatOption(param).subscribe(new SubscribeWithView<Response<List<RatingOption>>>(view) {
            @Override
            public void onSuccess(Response<List<RatingOption>> listResponse) {
                view.setData(listResponse.getData());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void sendData(String orderId, String expId, String rate, String review, List<String> optionIds, boolean isOpenAnotherDay) {

        //{"order_id":"1","visitor_id":"1","local_id":"2","rate":"4","option_id":"1,2,3"}

        String optionId = "";

        for (String data : optionIds) {
            if (optionId.isEmpty()) {
                optionId = optionId + data;
            } else {

                optionId = optionId + "," + data;
            }
        }

        view.showLoader();
        Map<String, String> param = new HashMap<>();
        param.put("order_id", orderId);
        param.put("visitor_id", session.getUser().getId());
        param.put("rate", rate);
        param.put("review", review);
        param.put("option_id", optionId);

        koolocoRepository.setRateVisitor(param).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                if (isOpenAnotherDay) {
                    openExpBookedScreen(expId);
                } else {
                    view.backToScreen();
                    view.hideLoader();
                }
            }

            @Override
            public void onError(Throwable e) {

                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() == 14) {
                        if (isOpenAnotherDay) {
                            openExpBookedScreen(expId);
                        } else {
                            view.backToScreen();
                        }
                    } else {
                        super.onError(e);
                    }
                } else {
                    super.onError(e);
                }
                view.hideLoader();
            }
        });
    }

    public void openExpBookedScreen(String expId) {


        //view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);

        Calendar calendar = Calendar.getInstance();

        String selectDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());


        map.put("month", TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "MM"));
        map.put("year", TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "yyyy"));
        koolocoRepository.getExpDetails(map).subscribe(new SubscribeWithView<Response<ExpDetails>>(view) {
            @Override
            public void onSuccess(Response<ExpDetails> expDetailsResponse) {
                view.hideLoader();

                openSelectExpDate(expDetailsResponse.getData());

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                if (e instanceof ConnectException) {
                    new android.os.Handler().postDelayed(() -> navigator.goBack(), 700);
                }
            }
        });

    }

    public void openSelectExpDate(ExpDetails experienceDetails) {


        ExperienceBookingFlow experienceBookingFlow = new ExperienceBookingFlow();
        experienceBookingFlow.setLocalId(experienceDetails.getLocalId());
        experienceBookingFlow.setLocalName(experienceDetails.getLocalName());
        experienceBookingFlow.setLocalProfile(experienceDetails.getLocalImage());
        experienceBookingFlow.setLocalRating(experienceDetails.getLocalRate());

        experienceBookingFlow.setExpereinceNews(new ArrayList<>());


        ExpereinceNew expereinceNew = new ExpereinceNew();
        expereinceNew.setId(experienceDetails.getId());
        expereinceNew.setTitle(experienceDetails.getTitle());
        expereinceNew.setImage_url(experienceDetails.getImages().get(0).getLocalImageUrl());
        expereinceNew.setRate(experienceDetails.getRating());
        expereinceNew.setRateCount(experienceDetails.getRatingCount());
        expereinceNew.setExperience_url(experienceDetails.getActivities().getImageUrlTwo());
        expereinceNew.setLocation(experienceDetails.getCity() + ", " + experienceDetails.getCountry());
        expereinceNew.setLocalId(experienceDetails.getLocalId());
        expereinceNew.setCity(experienceDetails.getCity());
        expereinceNew.setCountry(experienceDetails.getCountry());
        expereinceNew.setActivityAddress(experienceDetails.getMettingAddress());
        expereinceNew.setActivityLatitude(experienceDetails.getMeetingLet());
        expereinceNew.setActivityLongitude(experienceDetails.getMeetingLng());

        expereinceNew.setPrice(experienceDetails.getPrice());

        experienceBookingFlow.setExpereinceNew(expereinceNew);


        Bundle bundle = new Bundle();

        bundle.putString(Common.EXPSELECTDATE, new Gson().toJson(experienceBookingFlow));

        navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.EXPSELECTDATE).byFinishingCurrent().start();

    }

    public void openNotificationScreen() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Notification).byFinishingCurrent().start();
    }

    public void getDataOrder(String orderId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("order_id", orderId);

        koolocoRepository.orderDetailsVisitor(map).subscribe(new SubscribeWithView<Response<OrderDetails>>(view) {
            @Override
            public void onSuccess(Response<OrderDetails> orderDetailsResponse) {
                view.hideLoader();
                view.setOrderData(orderDetailsResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }
}
