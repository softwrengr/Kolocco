package com.kooloco.ui.expereince.presenter;
/**
 * Created by hlink on 21/4/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.DisableDateListResposne;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.Response;
import com.kooloco.model.SchedulePrice;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.expereince.view.ExperienceSelectDateTimeView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceSelectDateTimePresenter extends BasePresenter<ExperienceSelectDateTimeView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ExperienceSelectDateTimePresenter() {
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

    public void getData(ExpereinceNew expereinceNew, String selectDate) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expereinceNew.getId());
        map.put("date", selectDate);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getSelectDateSchedule(map).subscribe(new SubscribeWithView<Response<List<SchedulePrice>>>(view) {
            @Override
            public void onSuccess(Response<List<SchedulePrice>> disableDateListResposneResponse) {
                view.setData(disableDateListResposneResponse.getData());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.setData(new ArrayList<>());
                view.hideLoader();
            }
        });
    }

    public void openExpAddParticipants(ExperienceBookingFlow experienceBookingFlow) {
        navigator.openExperinceAddParticipantsView().hasData(experinceAddParticipantsView -> experinceAddParticipantsView.setExpBookingFlowData(experienceBookingFlow)).replace(true);
    }

    public void getDataYear(ExpereinceNew expereinceNew, String year, boolean isFirstTime) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expereinceNew.getId());
        map.put("year", year);
        map.put("user_id", session.getUser().getId());


        koolocoRepository.getDisableDateList(map).subscribe(new SubscribeWithView<Response<DisableDateListResposne>>(view) {
            @Override
            public void onSuccess(Response<DisableDateListResposne> disableDateListResposneResponse) {
                view.setCalData(disableDateListResposneResponse.getData().getCalendarDays(), isFirstTime);
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }
}