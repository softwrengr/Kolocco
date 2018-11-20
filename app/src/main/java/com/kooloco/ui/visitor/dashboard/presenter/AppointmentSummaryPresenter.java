package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 21/9/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.BookingFeeAndComision;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.model.VisitorBooking;
import com.kooloco.model.VisitorBookingNewFlow;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.visitor.dashboard.view.AppointmentSummaryView;
import com.kooloco.ui.visitor.dashboard.view.PaymentView;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class AppointmentSummaryPresenter extends BasePresenter<AppointmentSummaryView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public AppointmentSummaryPresenter() {

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

    public void getBookingFeesAndComision(String id, ExperienceBookingFlow visitorBooking) {
        view.showLoader();

        Map<String, String> param = new HashMap<>();
        param.put("experience_id", id);
        param.put("schedule_id", visitorBooking.getSchedulePrice().getId());
        param.put("participants", "" + visitorBooking.getAddParticipantsList().size());

        koolocoRepository.getBookingFeesNew(param).subscribe(new SubscribeWithView<Response<BookingFeeAndComision>>(view) {
            @Override
            public void onSuccess(Response<BookingFeeAndComision> bookingFeeAndComisionResponse) {
                view.hideLoader();
                view.setData(bookingFeeAndComisionResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                navigator.goBack();
            }
        });
    }

    public void openPayment(final ExperienceBookingFlow visitorBooking) {
        navigator.openPaymentView().hasData(new Passable<PaymentView>() {
            @Override
            public void passData(PaymentView paymentView) {
                paymentView.setBookingData(visitorBooking);
            }
        }).replace(true);
    }

    public void openMapScreen(ExperienceBookingFlow visitorBooking) {
        DashboardDetails dashboardDetails = new DashboardDetails();

        dashboardDetails.setFirstname(visitorBooking.getLocalName());
        dashboardDetails.setLastname(" ");

        dashboardDetails.setImageUrl(visitorBooking.getLocalProfile());

        dashboardDetails.setLocalLocation(visitorBooking.getExpereinceNew().getActivityAddress());

        dashboardDetails.setLatitude(visitorBooking.getExpereinceNew().getActivityLatitude());
        dashboardDetails.setLongitude(visitorBooking.getExpereinceNew().getActivityLongitude());

        navigator.openMettingLocalVisitorAndLocalMap().hasData(new Passable<MettingLocationView>() {
            @Override
            public void passData(MettingLocationView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);

    }
}



