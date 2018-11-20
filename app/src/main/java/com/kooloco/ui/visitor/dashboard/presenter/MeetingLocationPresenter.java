package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 21/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.dashboard.view.AddParticipantsView;
import com.kooloco.ui.visitor.dashboard.view.MeetingLocationView;

@PerActivity
public class MeetingLocationPresenter extends BasePresenter<MeetingLocationView> {
    @Inject
    public MeetingLocationPresenter() {

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

    public void openAppointmentSummery() {
        navigator.openAppointmentSummeryView().replace(true);
    }
    public void opnChat(ReceiverData receiverData) {
        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();
    }
    public void openAddParticipants(final VisitorBooking visitorBooking) {
        navigator.openAddParticipantsView().hasData(new Passable<AddParticipantsView>() {
            @Override
            public void passData(AddParticipantsView addParticipantsView) {
                addParticipantsView.setBookingData(visitorBooking);
            }
        }).replace(true);
    }
}
