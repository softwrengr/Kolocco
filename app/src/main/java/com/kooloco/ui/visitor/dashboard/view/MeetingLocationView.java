package com.kooloco.ui.visitor.dashboard.view;
/**
 * Created by hlink44 on 21/9/17.
 */

import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.RootView;

public interface MeetingLocationView extends RootView {
    void setBookingData(VisitorBooking visitorBooking);

}
