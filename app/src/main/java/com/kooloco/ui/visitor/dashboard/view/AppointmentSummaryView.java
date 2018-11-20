package com.kooloco.ui.visitor.dashboard.view;
/**
 * Created by hlink44 on 21/9/17.
 */

import com.kooloco.model.BookingFeeAndComision;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.ui.base.RootView;

public interface AppointmentSummaryView extends RootView {
    void setBookingData(ExperienceBookingFlow visitorBooking);

    void setData(BookingFeeAndComision data);
}
