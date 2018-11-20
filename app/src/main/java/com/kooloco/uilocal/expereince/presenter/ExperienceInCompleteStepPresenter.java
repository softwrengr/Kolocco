package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 17/4/18.
 */

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceInCompleteStepView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceInCompleteStepPresenter extends BasePresenter<ExperienceInCompleteStepView> {

    @Inject
    KoolocoRepository koolocoRepository;

    private ExpereinceNew expData;

    @Inject
    public ExperienceInCompleteStepPresenter() {
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

    public void getDataExp(ExpereinceNew expereinceNew, boolean isShowLoder) {
        if (isShowLoder) {
            view.showLoader();
        }
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expereinceNew.getId());

        koolocoRepository.getExpEdit(map).subscribe(new SubscribeWithView<Response<ExpereinceNew>>(view) {
            @Override
            public void onSuccess(Response<ExpereinceNew> listResponse) {
                if (isShowLoder) {
                    view.hideLoader();
                }

                view.setExpData(listResponse.getData());
                view.setData(listResponse.getData().getProgress());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (isShowLoder) {
                    view.hideLoader();
                }
            }
        });
    }

    public void openSport() {
        navigator.openExperienceSportView().hasData(experienceSportView -> {
            experienceSportView.setExpId(expData.getId());
            experienceSportView.setIsEdit(true);
        }).replace(true,"ExpBack");
    }

    public void openCategory() {
        navigator.openExperienceSelectView().hasData(experienceSelectView -> {
            experienceSelectView.setIsEdit(true);
            experienceSelectView.setExpId(expData.getId());
        }).replace(true);
    }

    public void openAddDetails() {
        navigator.openExperienceAddDetailsView().hasData(addDetailsView -> {
            addDetailsView.setIsEdit(true);
            addDetailsView.setExpId(expData.getId());
        }).replace(true);
    }

    public void openOtherDetails() {
        navigator.openExperienceOtherDetailsView().hasData(otherDetailsView -> {
            otherDetailsView.setIsEdit(true);
            otherDetailsView.setExpId(expData.getId());
        }).replace(true);

    }

    public void openOtherAddAnotherFieldsDetails() {
        navigator.openExperienceOtherDetailsAnotherFieldsView().hasData(otherDetailsView -> {
            otherDetailsView.setIsEdit(true);
            otherDetailsView.setExpId(expData.getId());
        }).replace(true);

    }

    public void openSchedulePrice() {
        navigator.openExperienceSchedulePriceView().hasData(scheduleAndPriceView -> {
            scheduleAndPriceView.setIsEdit(true);
            scheduleAndPriceView.setExpId(expData.getId());
        }).replace(true);
    }

    public void openMeetingSpot() {
        navigator.openExperienceMeetingSpotView().hasData(experienceMeetingSpotView -> {
            experienceMeetingSpotView.setIsEdit(true);
            experienceMeetingSpotView.setExpId(expData.getId());
        }).replace(true);

    }

    public void openCancellationPolice() {
        navigator.openExperienceCancellationPolicesView().hasData(experienceMeetingSpotView -> {
            experienceMeetingSpotView.setIsEdit(true);
            experienceMeetingSpotView.setExpId(expData.getId());
        }).replace(true);
    }


    public void setExpData(ExpereinceNew expData) {
        this.expData = expData;
    }
}