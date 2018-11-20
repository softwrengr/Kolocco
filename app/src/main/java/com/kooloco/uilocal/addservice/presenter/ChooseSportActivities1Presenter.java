package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 4/10/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.model.SportActivity;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.addservice.view.ChooseSportActivities1View;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class ChooseSportActivities1Presenter extends BasePresenter<ChooseSportActivities1View> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ChooseSportActivities1Presenter() {

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

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUserId());

        koolocoRepository.sportType(map).subscribe(new SubscribeWithView<Response<List<SportActivity>>>(view) {
            @Override
            public void onSuccess(Response<List<SportActivity>> listResponse) {
                view.hideLoader();
                view.setData(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openChooseSportView() {
        navigator.openChooseSportActivity2View().replace(true);
    }
}
