package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 25/8/17.
 */

import javax.inject.Inject;

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.SelectActivites;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.dashboard.view.SelectActivitiesView;
import com.kooloco.ui.visitor.dashboard.view.SelectDateView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class SelectActivitiesPresenter extends BasePresenter<SelectActivitiesView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    public SelectActivitiesPresenter() {

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

    public void openSelectDate(final VisitorBooking visitorBooking) {
        navigator.openSelectDateView().hasData(new Passable<SelectDateView>() {
            @Override
            public void passData(SelectDateView selectDateView) {
                selectDateView.setVisitorBooking(visitorBooking);
            }
        }).replace(true);
    }

    public void openKoolocoHelp() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.HelpAndFaq).start();
    }

    public void getData(String localId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("user_id", localId);

        kooocoRepository.activityAndSport(map).subscribe(new SubscribeWithView<Response<SelectActivites>>(view) {
            @Override
            public void onSuccess(Response<SelectActivites> selectActivitesResponse) {
                view.setData(selectActivitesResponse.getData());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //view.setData(new SelectActivites());
                view.hideLoader();
            }
        });
    }
}
