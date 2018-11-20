package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 22/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Common;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.dashboard.view.AppointmentBookedView;
import com.kooloco.util.SubscribeWithView;
import com.kooloco.util.TimeConvertUtils;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@PerActivity
public class AppointmentBookedPresenter extends BasePresenter<AppointmentBookedView> {

    @Inject
    KoolocoRepository koolocoRepository;


    @Inject
    public AppointmentBookedPresenter() {

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

    public void openRecipt() {
        navigator.openReceiptView().replace(true);
    }

    public void openExpBookedScreen(String expId) {


        view.showLoader();
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
}
