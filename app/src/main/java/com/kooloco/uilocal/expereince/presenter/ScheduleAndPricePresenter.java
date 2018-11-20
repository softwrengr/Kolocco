package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 16/4/18.
 */

import com.google.gson.Gson;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Response;
import com.kooloco.model.ScheduleAndPriceResponse;
import com.kooloco.model.SchedulePrice;
import com.kooloco.model.SchedulePriceData;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.ScheduleAndPriceView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ScheduleAndPricePresenter extends BasePresenter<ScheduleAndPriceView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ScheduleAndPricePresenter() {
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

    public void openOtherDetails(String expId) {
        navigator.openExperienceOtherDetailsView().hasData(otherDetailsView -> otherDetailsView.setExpId(expId)).replace(true);
    }

    public void callWs(String expId, String dayName) {
//        { "experience_id": "1", "day": "Mon", "schedule_type": "N", "is_multiple_day": "0", "is_available": "0", "schedule_date": "2018-05-03", "slot": { "experience_id": "1", "day": "Mon", "start_time": "11:00:00", "end_time": "15:00:00", "is_multiple_day": "0", "price_per_participant": "10", "duration_in_days": "5" } }

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        // map.put("day", dayName);
        map.put("schedule_type", "N");
        map.put("schedule_date", "");

        koolocoRepository.getExpAvability(map).subscribe(new SubscribeWithView<Response<ScheduleAndPriceResponse>>(view) {
            @Override
            public void onSuccess(Response<ScheduleAndPriceResponse> scheduleAndPriceResponseResponse) {
                view.setData(scheduleAndPriceResponseResponse.getData().getSchedulePriceData());
                view.setDataParticipants(scheduleAndPriceResponseResponse.getData().getMaximumParticipant());
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
                // view.setData(new ArrayList<>());
            }
        });
    }

    public void callWsSetAvability(String slotId, String expId, String dayName, String isNumberOfDay, String isMultiPalDay, String startTime, String endTime, String price, CallBack callBack) {


//        { "experience_id": "1", "day": "Mon", "schedule_type": "S", "is_multiple_day": "0", "is_available": "1", "schedule_date": "2018-05-03", "slot": { "experience_id": "1", "day": "Mon", "start_time": "11:00:00", "end_time": "15:00:00", "is_multiple_day": "0", "price_per_participant": "10", "duration_in_days": "5" } }

        if (slotId.isEmpty()) {
            view.showLoader();
            Map<String, String> map = new HashMap<>();
            map.put("experience_id", expId);
            map.put("day", dayName);
            map.put("schedule_type", "N");
            map.put("is_available", "0");
            map.put("is_multiple_day", isMultiPalDay);
            map.put("schedule_date", "");

            SchedulePrice schedulePrice = new SchedulePrice();

            schedulePrice.setExperienceId(expId);
            schedulePrice.setDay(dayName);
            schedulePrice.setStartTime(startTime);
            schedulePrice.setEndTime(endTime);
            schedulePrice.setDuration(null);
            schedulePrice.setIsDisable(null);

            schedulePrice.setIsMultipleDay(isMultiPalDay);
            schedulePrice.setDurationInDays(isNumberOfDay);
            schedulePrice.setPrice(price);


            map.put("slot", new Gson().toJson(schedulePrice));

            koolocoRepository.setExpAvability(map).subscribe(new SubscribeWithView<Response<ScheduleAndPriceResponse>>(view) {
                @Override
                public void onSuccess(Response<ScheduleAndPriceResponse> scheduleAndPriceResponseResponse) {
                    callBack.success(true, scheduleAndPriceResponseResponse.getMessage());
                    view.setData(scheduleAndPriceResponseResponse.getData().getSchedulePriceData());
                    view.hideLoader();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    callBack.success(false, e.getMessage());
                    view.hideLoader();
                    //view.setData(new ArrayList<>());
                }
            });
        } else {
            view.showLoader();
            Map<String, String> map = new HashMap<>();
            map.put("experience_id", expId);
            map.put("day", dayName);
            map.put("schedule_type", "N");
            map.put("is_available", "0");
            map.put("is_multiple_day", isMultiPalDay);
            map.put("schedule_date", "");

            SchedulePrice schedulePrice = new SchedulePrice();

            schedulePrice.setExperienceId(expId);
            schedulePrice.setDay(dayName);
            schedulePrice.setStartTime(startTime);
            schedulePrice.setEndTime(endTime);
            schedulePrice.setDuration(null);
            schedulePrice.setIsDisable(null);

            schedulePrice.setIsMultipleDay(isMultiPalDay);
            schedulePrice.setDurationInDays(isNumberOfDay);
            schedulePrice.setPrice(price);


            map.put("slot", new Gson().toJson(schedulePrice));

            map.put("slot_id", slotId);

            koolocoRepository.editExpAvability(map).subscribe(new SubscribeWithView<Response<ScheduleAndPriceResponse>>(view) {
                @Override
                public void onSuccess(Response<ScheduleAndPriceResponse> scheduleAndPriceResponseResponse) {
                    callBack.success(true, scheduleAndPriceResponseResponse.getMessage());
                    view.setData(scheduleAndPriceResponseResponse.getData().getSchedulePriceData());
                    view.hideLoader();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    callBack.success(false, e.getMessage());
                    view.hideLoader();
                    //view.setData(new ArrayList<>());
                }
            });
        }

    }

    public void callWsDelete(SchedulePrice schedulePrice) {
        //{"slot_id":"9","experience_id":"1","day":"Mon","schedule_type":"S","schedule_date":"2018-05-03"}

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", schedulePrice.getExperienceId());
        map.put("day", schedulePrice.getDay());
        map.put("slot_id", schedulePrice.getId());
        map.put("schedule_type", "N");
        map.put("schedule_date", "");

        koolocoRepository.deleteExpAvability(map).subscribe(new SubscribeWithView<Response<ScheduleAndPriceResponse>>(view) {
            @Override
            public void onSuccess(Response<ScheduleAndPriceResponse> scheduleAndPriceResponseResponse) {

                view.setData(scheduleAndPriceResponseResponse.getData().getSchedulePriceData());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callWsMaxParticipants(String expId, String maxiMumPart, boolean isEdit) {
        //
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("maximum_participant", maxiMumPart);

        koolocoRepository.setExpMaxParticipants(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response scheduleAndPriceResponseResponse) {
                view.hideLoader();
                if (isEdit) {
                    navigator.goBack();
                } else {
                    openOtherDetails(expId);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public boolean checkSchedulePriceData(SchedulePriceData schedulePriceData) {

        boolean isEmpty=true;

        if (!schedulePriceData.getSun().isEmpty()) {
            isEmpty= false;
        }
        else if (!schedulePriceData.getMon().isEmpty()) {
            isEmpty= false;
        }
        else if (!schedulePriceData.getTue().isEmpty()) {
            isEmpty= false;
        }
        else if (!schedulePriceData.getWed().isEmpty()) {
            isEmpty= false;
        }
        else if (!schedulePriceData.getThu().isEmpty()) {
            isEmpty= false;
        }
        else if (!schedulePriceData.getFri().isEmpty()) {
            isEmpty=false;
        }
        else if (!schedulePriceData.getSat().isEmpty()) {
            isEmpty=false;
        }

        return isEmpty;

    }

    public interface CallBack {
        void success(boolean isScuess, String message);
    }

    public List<SchedulePrice> getSchedulrPriceList(String dayName, SchedulePriceData schedulePriceData) {
        switch (dayName) {
            case "Sun":
                return schedulePriceData.getSun();
            case "Mon":
                return schedulePriceData.getMon();
            case "Tue":
                return schedulePriceData.getTue();
            case "Wed":
                return schedulePriceData.getWed();
            case "Thu":
                return schedulePriceData.getThu();
            case "Fri":
                return schedulePriceData.getFri();
            case "Sat":
                return schedulePriceData.getSat();
            default:
                return new ArrayList<>();
        }
    }


    public List<String> getSelectDayNames(SchedulePriceData schedulePriceData) {
        List<String> selectDayName = new ArrayList<>();

        if (!schedulePriceData.getSun().isEmpty()) {
            selectDayName.add("Sun");
        }
        if (!schedulePriceData.getMon().isEmpty()) {
            selectDayName.add("Mon");
        }
        if (!schedulePriceData.getTue().isEmpty()) {
            selectDayName.add("Tue");
        }
        if (!schedulePriceData.getWed().isEmpty()) {
            selectDayName.add("Wed");
        }
        if (!schedulePriceData.getThu().isEmpty()) {
            selectDayName.add("Thu");
        }
        if (!schedulePriceData.getFri().isEmpty()) {
            selectDayName.add("Fri");
        }
        if (!schedulePriceData.getSat().isEmpty()) {
            selectDayName.add("Sat");
        }
        return selectDayName;

    }
}