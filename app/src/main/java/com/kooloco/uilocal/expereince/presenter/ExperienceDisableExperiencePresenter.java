package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 17/4/18.
 */

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.DisableExperience;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceDisableExperienceView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceDisableExperiencePresenter extends BasePresenter<ExperienceDisableExperienceView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ExperienceDisableExperiencePresenter() {
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

    public void getData(ExpereinceNew expNew) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expNew.getId());

        koolocoRepository.getExpDisable(map).subscribe(new SubscribeWithView<Response<DisableExperience>>(view) {
            @Override
            public void onSuccess(Response<DisableExperience> disableExperienceResponse) {
                view.setData(disableExperienceResponse.getData());
                view.hideLoader();
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
            }
        });

    }

    public void callWs(String id, String startDate, String endDate) {

        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("experience_id", id);
        map.put("start_date", startDate);
        map.put("end_date", endDate);

        koolocoRepository.setExpDisable(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response disableExperienceResponse) {
                view.hideLoader();
                navigator.goBack();
                //  view.showMessage(disableExperienceResponse.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }
}