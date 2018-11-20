package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 13/4/18.
 */

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ProfileStatus;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.EditExperienceView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class EditExperiencePresenter extends BasePresenter<EditExperienceView> {

    private ExpereinceNew expereinceNew;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public EditExperiencePresenter() {
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

    public void openSport() {

        navigator.openExperienceSportView().hasData(experienceSportView -> {
            experienceSportView.setIsEdit(true);
            experienceSportView.setExpId(expereinceNew.getId());
        }).replace(true,"ExpBack");
    }

    public void openCategory() {

        navigator.openExperienceSelectView().hasData(experienceSelectView -> {
            experienceSelectView.setIsEdit(true);
            experienceSelectView.setExpId(expereinceNew.getId());
        }).replace(true);
    }

    public void openAddDetails() {

        navigator.openExperienceAddDetailsView().hasData(addDetailsView -> {
            addDetailsView.setIsEdit(true);
            addDetailsView.setExpId(expereinceNew.getId());
        }).replace(true);
    }

    public void openOtherDetails() {
        navigator.openExperienceOtherDetailsView().hasData(otherDetailsView -> {
            otherDetailsView.setIsEdit(true);
            otherDetailsView.setExpId(expereinceNew.getId());
        }).replace(true);

    }

    public void openOtherAddAnotherFieldsDetails() {
        navigator.openExperienceOtherDetailsAnotherFieldsView().hasData(otherDetailsView -> {
            otherDetailsView.setIsEdit(true);
            otherDetailsView.setExpId(expereinceNew.getId());
        }).replace(true);

    }

    public void openSchedulePrice() {
        navigator.openExperienceSchedulePriceView().hasData(scheduleAndPriceView -> {
            scheduleAndPriceView.setIsEdit(true);
            scheduleAndPriceView.setExpId(expereinceNew.getId());
        }).replace(true);
    }

    public void openMeetingSpot() {

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expereinceNew.getId());

        koolocoRepository.getExpEdit(map).subscribe(new SubscribeWithView<Response<ExpereinceNew>>(view) {
            @Override
            public void onSuccess(Response<ExpereinceNew> listResponse) {
                view.hideLoader();
                navigator.openExperienceMeetingSpotView().hasData(experienceMeetingSpotView -> {
                    experienceMeetingSpotView.setIsEdit(true);
                    experienceMeetingSpotView.setExpId(expereinceNew.getId());
                    experienceMeetingSpotView.setExpResponse(listResponse.getData());
                }).replace(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openCancellationPolice() {
        navigator.openExperienceCancellationPolicesView().hasData(experienceMeetingSpotView -> {
            experienceMeetingSpotView.setIsEdit(true);
            experienceMeetingSpotView.setExpId(expereinceNew.getId());
        }).replace(true);
    }

    public void openExpSetSpecialAvilabilities() {
        navigator.openExperienceSetSpecialAvailabilitesView().hasData(experienceSetSpecialAvailabilitesView -> experienceSetSpecialAvailabilitesView.setExpNew(expereinceNew)).replace(true);
    }

    public void openExpDisableSpot() {
        navigator.openExperienceDisableExperienceView().hasData(experienceDisableExperienceView -> {
            experienceDisableExperienceView.setExpNew(expereinceNew);
        }).replace(true);
    }

    public void openExpSportEquipments() {
        navigator.openExperienceSportEquipmentsView().hasData(experienceSportEquipmentsView -> experienceSportEquipmentsView.setExpId(expereinceNew.getId())).replace(true);
    }

    public void openInCompleteProfile(ExpereinceNew dataExp) {
        navigator.openExperienceInCompleteStepView().hasData(experienceInCompleteStepView -> {
            experienceInCompleteStepView.setExpData(dataExp);
        }).replace(true);
    }

    public void setExpNew(ExpereinceNew expereinceNew) {
        this.expereinceNew = expereinceNew;
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
                view.setData(listResponse.getData());
                getStepCount(listResponse.getData());
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

    public void getStepCount(ExpereinceNew expereinceNew) {
        int stepPendingCount = 0;
        for (ProfileStatus profileStatus : expereinceNew.getProgress()) {
            if (profileStatus.getIsComplete().equalsIgnoreCase("0")) {
                stepPendingCount = stepPendingCount + 1;
            }
        }

        view.setStepCountData(stepPendingCount, expereinceNew.getProgress());
    }
}