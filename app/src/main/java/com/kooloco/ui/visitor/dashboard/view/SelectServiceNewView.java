package com.kooloco.ui.visitor.dashboard.view;


import com.kooloco.model.DashboardDetails;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.RootView;

/**
 * Created by hlink on 8/2/18.
 */

public interface SelectServiceNewView extends RootView {

    void setVisitorBooking(VisitorBooking visitorBooking);

    void setDashBoardDetails(DashboardDetails dashboardDetails);

}