package com.kooloco.ui.visitor.dashboard.view;


import com.kooloco.model.Activities;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 8/2/18.
 */

public interface SelectActivityNewView extends RootView {

    void setVisitorBooking(VisitorBooking visitorBooking);

    void setData(List<Activities> data);
}