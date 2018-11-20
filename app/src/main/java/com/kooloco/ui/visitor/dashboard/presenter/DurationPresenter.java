package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 21/9/17.
 */

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Duration;
import com.kooloco.model.Response;
import com.kooloco.model.SelectActivites;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.dashboard.view.DurationView;
import com.kooloco.util.SubscribeWithView;

import java.util.List;

@PerActivity
public class DurationPresenter extends BasePresenter<DurationView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    public DurationPresenter() {

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
        kooocoRepository.getDuration().subscribe(new SubscribeWithView<Response<List<Duration>>>(view) {

            @Override
            public void onSuccess(Response<List<Duration>> listResponse) {
                view.setData(listResponse.getData());
            }
        });
    }

    public void openMettingLocation() {
        navigator.openMeetingLocationView().replace(true);
    }

    public void openChat() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).start();
    }
}
