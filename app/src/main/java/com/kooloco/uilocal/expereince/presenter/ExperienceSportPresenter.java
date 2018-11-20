package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 13/4/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Activities;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.Response;
import com.kooloco.model.SelectActivites;
import com.kooloco.model.Service;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SubService;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceSportView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceSportPresenter extends BasePresenter<ExperienceSportView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ExperienceSportPresenter() {
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

    public void getData(String expId) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUserId());
        map.put("experience_id", expId);

        koolocoRepository.sportServiceType(map).subscribe(new SubscribeWithView<Response<List<Service>>>(view) {
            @Override
            public void onSuccess(Response<List<Service>> listResponse) {
                getSelectSport(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }


    private void getSelectSport(List<Service> services) {

        int selectMainService = 0;
        int selectSubService = -1;

        for (int i = 0; i < services.size(); i++) {

            Service service = services.get(i);
            if (service.getIsExpandable().equalsIgnoreCase("1") && selectMainService == i) {
                selectSubService = 0;
            }

            if (service.getIsSelected().equalsIgnoreCase("1")) {
                selectMainService = i;
                if (service.getIsExpandable().equalsIgnoreCase("1")) {
                    for (int j = 0; j < service.getSubServices().size(); j++) {
                        SubService subService = service.getSubServices().get(j);

                        if (subService.getIsSelected().equalsIgnoreCase("1")) {
                            selectSubService = j;
                            break;
                        }
                    }

                } else {
                    selectSubService = -1;
                }
                break;
            }
        }

        view.hideLoader();
        view.setData(services, selectMainService, selectSubService);
    }


    public void callWs(String expId, String sportId, boolean isEdit) {
        //{"experience_id":"1","activity_id":"1"}

        navigator.openExperienceSelectView().hasData(scheduleAndPriceView -> {
            scheduleAndPriceView.setExpId(expId);
            scheduleAndPriceView.setIsEdit(isEdit);
            scheduleAndPriceView.setSportId(sportId);

        }).replace(true, "ExpBack");


        /*view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("sport_id", sportId);

        koolocoRepository.setExpSport(map).subscribe(new SubscribeWithView<Response<ExperienceResponse>>(view) {
            @Override
            public void onSuccess(Response<ExperienceResponse> experienceResponseResponse) {
                view.hideLoader();
                if (isEdit) {
                    navigator.goBack();
                } else {
                    //As per client comment change step 06-06-2018
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });*/
    }
}