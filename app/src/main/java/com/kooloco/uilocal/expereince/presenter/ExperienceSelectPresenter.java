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
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceSelectView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceSelectPresenter extends BasePresenter<ExperienceSelectView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ExperienceSelectPresenter() {
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


    public void getData(String expId, String sportId) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("experience_id", expId);
        map.put("sport_id", sportId);

        koolocoRepository.experience(map).subscribe(new SubscribeWithView<Response<List<Activities>>>(view) {
            @Override
            public void onSuccess(Response<List<Activities>> listResponse) {
                getSelectIndex(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }


    /**
     * It is used to get select index
     */
    private void getSelectIndex(List<Activities> activities) {

        int selectIndex = 0;

        int temp = 0;
        for (Activities activitie : activities) {
            if (activitie.getIsSelected().equalsIgnoreCase("1")) {
                selectIndex = temp;
                break;
            }
            temp++;
        }

        view.setData(activities, selectIndex);
        view.hideLoader();
    }

    public void callWs(String expId, Activities activitiesSelect, String sportId, boolean isEdit) {

        //{"experience_id":"1","activity_id":"1"}

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("activity_id", activitiesSelect.getId());
        map.put("sport_id", sportId);

        koolocoRepository.setExpActivity(map).subscribe(new SubscribeWithView<Response<ExperienceResponse>>(view) {
            @Override
            public void onSuccess(Response<ExperienceResponse> experienceResponseResponse) {
                view.hideLoader();
                if (isEdit) {
                   navigator.openChooseLangaugeView().clearHistory("ExpBack");
                } else {

                    //As per client comment change step 06-06-2018
                    navigator.openExperienceSchedulePriceView().hasData(scheduleAndPriceView -> scheduleAndPriceView.setExpId(expId)).replace(true);

//                    navigator.openExperienceSportView().hasData(experienceSportView -> experienceSportView.setExpId(expId)).replace(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }
}