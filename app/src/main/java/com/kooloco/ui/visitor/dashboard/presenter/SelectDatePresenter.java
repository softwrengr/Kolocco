package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 21/9/17.
 */

import android.view.View;

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Response;
import com.kooloco.model.Time;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.visitor.dashboard.view.MeetingLocationView;
import com.kooloco.ui.visitor.dashboard.view.SelectDateView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class SelectDatePresenter extends BasePresenter<SelectDateView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    public SelectDatePresenter() {

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

    public void getStartTime(VisitorBooking visitorBooking) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("date", visitorBooking.getDate());
        map.put("user_id", visitorBooking.getLocalId());
        map.put("activity_id", visitorBooking.getExperienceId());


        kooocoRepository.getStartTime(map).subscribe(new SubscribeWithView<Response<List<Time>>>(view) {
            @Override
            public void onSuccess(Response<List<Time>> listResponse) {
                view.hideLoader();
                view.hideKeyBoard();
                view.setDataStartTime(listResponse.getData());
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
                view.hideLoader();
                view.hideKeyBoard();
                view.setDataStartTime(new ArrayList<Time>());
            }
        });
    }

    public void getEndTime(VisitorBooking visitorBooking) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("date", visitorBooking.getDate());
        map.put("user_id", visitorBooking.getLocalId());
        map.put("activity_id", visitorBooking.getExperienceId());
        map.put("start_time", visitorBooking.getStartTime().getTime());

        kooocoRepository.getEndTime(map).subscribe(new SubscribeWithView<Response<List<Time>>>(view) {
            @Override
            public void onSuccess(Response<List<Time>> listResponse) {
                view.hideLoader();
                view.hideKeyBoard();
                view.setDataEndTime(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.hideKeyBoard();
                view.setDataEndTime(new ArrayList<Time>());
            }
        });
    }

    public void openAddParticipants() {
        navigator.openAddParticipantsView().replace(true);
    }

    public void openMettingLocation(final VisitorBooking visitorBooking) {
        navigator.openMeetingLocationView().hasData(new Passable<MeetingLocationView>() {
            @Override
            public void passData(MeetingLocationView meetingLocationView) {
                meetingLocationView.setBookingData(visitorBooking);
            }
        }).replace(true);

    }
}
