package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 17/4/18.
 */

import com.google.gson.Gson;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Response;
import com.kooloco.model.ScheduleAndPriceResponse;
import com.kooloco.model.SchedulePrice;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.expereince.view.ExperienceSetSpecialAvailabilitesView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceSetSpecialAvailabilitesPresenter extends BasePresenter<ExperienceSetSpecialAvailabilitesView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ExperienceSetSpecialAvailabilitesPresenter() {
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

    public void callWs(String expId, String dayName, String date) {
//        { "experience_id": "1", "day": "Mon", "schedule_type": "N", "is_multiple_day": "0", "is_available": "0", "schedule_date": "2018-05-03", "slot": { "experience_id": "1", "day": "Mon", "start_time": "11:00:00", "end_time": "15:00:00", "is_multiple_day": "0", "price_per_participant": "10", "duration_in_days": "5" } }

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("day", dayName);
        map.put("schedule_type", "S");
        map.put("schedule_date", date);

        koolocoRepository.getExpAvability(map).subscribe(new SubscribeWithView<Response<ScheduleAndPriceResponse>>(view) {
            @Override
            public void onSuccess(Response<ScheduleAndPriceResponse> scheduleAndPriceResponseResponse) {
                view.setData(scheduleAndPriceResponseResponse.getData().getSlot());
                view.setIsNotAvability(scheduleAndPriceResponseResponse.getData().getIs_avability().equalsIgnoreCase("1"));
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
                view.setData(new ArrayList<>());
            }
        });
    }

    public void callWsSetAvability(String slotId, String expId, String dayName, String date, String isAvailable, String isNumberOfDay, String isMultiPalDay, String startTime, String endTime, String price, ScheduleAndPricePresenter.CallBack callBack) {


//        { "experience_id": "1", "day": "Mon", "schedule_type": "S", "is_multiple_day": "0", "is_available": "1", "schedule_date": "2018-05-03", "slot": { "experience_id": "1", "day": "Mon", "start_time": "11:00:00", "end_time": "15:00:00", "is_multiple_day": "0", "price_per_participant": "10", "duration_in_days": "5" } }

        if (slotId.isEmpty()) {
            view.showLoader();
            Map<String, String> map = new HashMap<>();
            map.put("experience_id", expId);
            map.put("day", dayName);
            map.put("schedule_type", "S");
            map.put("is_available", isAvailable);
            map.put("is_multiple_day", isMultiPalDay);
            map.put("schedule_date", date);

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
                    view.setData(scheduleAndPriceResponseResponse.getData().getSlot());
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
            map.put("schedule_type", "S");
            map.put("is_available", isAvailable);
            map.put("is_multiple_day", isMultiPalDay);
            map.put("schedule_date", date);

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
                    view.setData(scheduleAndPriceResponseResponse.getData().getSlot());
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

    public void callWsDelete(SchedulePrice schedulePrice, String date) {
        //{"slot_id":"9","experience_id":"1","day":"Mon","schedule_type":"S","schedule_date":"2018-05-03"}

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", schedulePrice.getExperienceId());
        map.put("day", schedulePrice.getDay());
        map.put("slot_id", schedulePrice.getId());
        map.put("schedule_type", "S");
        map.put("schedule_date", date);

        koolocoRepository.deleteExpAvability(map).subscribe(new SubscribeWithView<Response<ScheduleAndPriceResponse>>(view) {
            @Override
            public void onSuccess(Response<ScheduleAndPriceResponse> scheduleAndPriceResponseResponse) {

                view.setData(scheduleAndPriceResponseResponse.getData().getSlot());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setData(new ArrayList<>());
            }
        });
    }


    public void callWsIsNotAvailable(String expId, String dayName, String date, String isAvailable) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("day", dayName);
        map.put("schedule_type", "S");
        map.put("is_available", isAvailable);
        map.put("is_multiple_day", "0");
        map.put("schedule_date", date);


        koolocoRepository.setExpAvability(map).subscribe(new SubscribeWithView<Response<ScheduleAndPriceResponse>>(view) {
            @Override
            public void onSuccess(Response<ScheduleAndPriceResponse> scheduleAndPriceResponseResponse) {

                view.setData(scheduleAndPriceResponseResponse.getData().getSlot());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setData(new ArrayList<>());
            }
        });
    }


}