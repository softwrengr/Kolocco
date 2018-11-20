package com.kooloco.ui.visitor.dashboard.presenter;
/**
 * Created by hlink on 14/2/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Activities;
import com.kooloco.model.Response;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.visitor.dashboard.view.SelectActivityNewView;
import com.kooloco.ui.visitor.dashboard.view.SelectDateView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class SelectActivityNewPresenter extends BasePresenter<SelectActivityNewView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public SelectActivityNewPresenter() {
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

    public void getData(VisitorBooking visitorBooking) {
        view.showLoader();

        Map<String, String> param = new HashMap<>();

        param.put("user_id", visitorBooking.getLocalId());
        param.put("sport_id", visitorBooking.getSportId());

        koolocoRepository.getVisitorActivities(param).subscribe(new SubscribeWithView<Response<List<Activities>>>(view) {
            @Override
            public void onSuccess(Response<List<Activities>> listResponse) {
                view.hideLoader();
                view.setData(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setData(new ArrayList<>());
            }
        });
    }


    public void openSelectDate(final VisitorBooking visitorBooking) {
        navigator.openSelectDateView().hasData(new Passable<SelectDateView>() {
            @Override
            public void passData(SelectDateView selectDateView) {
                selectDateView.setVisitorBooking(visitorBooking);
            }
        }).replace(true);
    }
}