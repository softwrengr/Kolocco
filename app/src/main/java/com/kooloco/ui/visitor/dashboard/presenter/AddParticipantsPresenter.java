package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 25/8/17.
 */

import javax.inject.Inject;

import com.google.gson.Gson;
import com.kooloco.di.PerActivity;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.visitor.dashboard.view.AddParticipantsView;
import com.kooloco.ui.visitor.dashboard.view.AppointmentSummaryView;

import java.util.ArrayList;
import java.util.List;

@PerActivity
public class AddParticipantsPresenter extends BasePresenter<AddParticipantsView> {
    @Inject
    public AddParticipantsPresenter() {

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

    public void openDuration() {
        navigator.openDurationView().replace(true);
    }

    public void openAppointmentSummery(final VisitorBooking visitorBooking, List<String> addParticipantsS, String addUserEmail) {

        List<AddParticipants> addPartcipantList = new ArrayList<>();

        if (!addUserEmail.isEmpty()) {
            AddParticipants addParticipants = new AddParticipants();
            addParticipants.setEmail(addUserEmail);
            addPartcipantList.add(addParticipants);
        }

        for (String email : addParticipantsS) {
            AddParticipants addParticipants = new AddParticipants();
            addParticipants.setEmail(email);
            addPartcipantList.add(addParticipants);

        }

        visitorBooking.setAddParticipants(new Gson().toJson(addPartcipantList));

        visitorBooking.setAddParticipantsList(addPartcipantList);

        navigator.openAppointmentSummeryView().hasData(new Passable<AppointmentSummaryView>() {
            @Override
            public void passData(AppointmentSummaryView appointmentSummaryView) {
              //  appointmentSummaryView.setBookingData(visitorBooking);
            }
        }).replace(true);
    }

}
