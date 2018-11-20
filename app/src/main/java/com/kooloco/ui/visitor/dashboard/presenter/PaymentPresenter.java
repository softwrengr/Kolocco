package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 22/9/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.BookingData;
import com.kooloco.model.Card;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.visitor.dashboard.view.PaymentView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class PaymentPresenter extends BasePresenter<PaymentView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    public PaymentPresenter() {

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
        map.put("user_id", session.getUser().getId());

        kooocoRepository.getCreditCardList(map).subscribe(new SubscribeWithView<Response<List<Card>>>(view) {
            @Override
            public void onSuccess(Response<List<Card>> homeNewResponseResponse) {

                view.setData(homeNewResponseResponse.getData());

                view.hideLoader();

            }

            @Override
            public void onError(Throwable e) {

                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    } else if (exception.getServerCode() == 0) {
                        view.setData(new ArrayList<>());
                    }
                } else {
                    super.onError(e);
                }

                view.hideLoader();
            }
        });


    }



    public void callWs(ExperienceBookingFlow visitorBooking, String cardId) {

        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("local_id", visitorBooking.getLocalId());
        map.put("experience_id", visitorBooking.getExpereinceNew().getId());

        map.put("booking_date", visitorBooking.getSelectDate());

        map.put("schedule_id", visitorBooking.getSchedulePrice().getId());
        map.put("is_multiple_day", visitorBooking.getSchedulePrice().getIsMultipleDay());
        map.put("duration", visitorBooking.getSchedulePrice().getDuration());
        map.put("start_time", visitorBooking.getSchedulePrice().getStartTime());
        map.put("end_time", visitorBooking.getSchedulePrice().getEndTime());


        map.put("meeting_address", visitorBooking.getExpereinceNew().getActivityAddress());
        map.put("meeting_latitude", visitorBooking.getExpereinceNew().getActivityLatitude());
        map.put("meeting_longitude", visitorBooking.getExpereinceNew().getActivityLongitude());
        map.put("more_about", visitorBooking.getMoreAbout());

        map.put("card_id", cardId);

        map.put("price", visitorBooking.getPrice());

        map.put("activity_total", visitorBooking.getTootalPriceActivity());
        map.put("total_price", visitorBooking.getTootalPrice());

        map.put("fees_for_booking", visitorBooking.getFeesForBooking());
        map.put("fees_for_booking_perc", visitorBooking.getFeesForBookingPer());

        map.put("commision_perc", visitorBooking.getCommisionPer());
        map.put("commision_value", visitorBooking.getCommisionValue());

        map.put("participant", visitorBooking.getAddParticipants());

        map.put("is_referral", visitorBooking.getIsRefferal());
        map.put("referral_amount", visitorBooking.getRefferalAmount());
        map.put("referral_percentage", visitorBooking.getReferralPer());

        kooocoRepository.boookAppointment(map).subscribe(new SubscribeWithView<Response<BookingData>>(view) {
            @Override
            public void onSuccess(Response<BookingData> response) {
                view.setRecentChat(response.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openAppointmentBooked(ExperienceBookingFlow visitorBooking) {
        navigator.openAppointmentBookedView().hasData(appointmentBookedView -> appointmentBookedView.setExpId(visitorBooking.getExpereinceNew().getId())).replace(true);
    }

    public void openAddCard() {
        navigator.openCreditCardView().hasData(cardView -> cardView.setIsBooking(true)).replace(true);
    }
}
