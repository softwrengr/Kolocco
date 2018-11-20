package com.kooloco.ui.visitor.dashboard.view;
/**
 * Created by hlink44 on 22/9/17.
 */

import com.kooloco.model.BookingData;
import com.kooloco.model.Card;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface PaymentView extends RootView {
    void setData(List<Card> data);

    void setBookingData(ExperienceBookingFlow visitorBooking);

    void setRecentChat(BookingData data);

}
