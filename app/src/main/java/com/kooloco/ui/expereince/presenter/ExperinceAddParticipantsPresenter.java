package com.kooloco.ui.expereince.presenter;
/**
 * Created by hlink on 23/4/18.
 */

import com.google.gson.Gson;
import com.kooloco.di.PerActivity;
import com.kooloco.model.AddParticipants;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.VisitorBooking;
import com.kooloco.model.VisitorBookingNewFlow;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.expereince.view.ExperinceAddParticipantsView;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.visitor.dashboard.view.AppointmentSummaryView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@PerActivity
public class ExperinceAddParticipantsPresenter extends BasePresenter<ExperinceAddParticipantsView> {

    @Inject
    public ExperinceAddParticipantsPresenter() {
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

    public void openAppointmentSummery(final ExperienceBookingFlow experienceBookingFlow, List<String> addParticipantsS, String addUserEmail) {

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

        experienceBookingFlow.setAddParticipants(new Gson().toJson(addPartcipantList));

        experienceBookingFlow.setAddParticipantsList(addPartcipantList);

        navigator.openAppointmentSummeryView().hasData(new Passable<AppointmentSummaryView>() {
            @Override
            public void passData(AppointmentSummaryView appointmentSummaryView) {
                appointmentSummaryView.setBookingData(experienceBookingFlow);
            }
        }).replace(true);
    }

}